import com.google.protobuf.ByteString;
import com.google.rpc.context.AttributeContext;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.example.model.*;

import java.io.*;
import java.io.File;
import java.util.Iterator;
import java.util.Objects;
import java.util.Scanner;

public class GrpcClient {
    public static void main(String[] args) throws IOException, InterruptedException {
        String address = "localhost"; //here we use service on the same host
        int port = 50001;
        ServiceNameGrpc.ServiceNameBlockingStub bStub;
        ServiceNameGrpc.ServiceNameStub nonbStub;
        System.out.println("Running gRPC client...");
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress(address, port) .usePlaintext().build();
        bStub = ServiceNameGrpc.newBlockingStub(channel);
        nonbStub = ServiceNameGrpc.newStub(channel);
        ByteRequest request = ByteRequest.newBuilder().setFile("src/files/README.txt").build();
//        TheRequest request = TheRequest.newBuilder().setName("Michal")
//                .setAge(33).build();
//        System.out.println("...calling unaryProcedure");
//        TheResponse response = bStub.unaryProcedure(request);
//        System.out.println("...after calling unaryProcedure");
//        System.out.println("--> Response: " + response);
//        Iterator<TheResponse> respIterator;
//        System.out.println("...calling streamProcedure");
//        respIterator = bStub.streamProcedure(request);
//        System.out.println("...after calling streamProcedure");
//        TheResponse strResponse;
//        while(respIterator.hasNext()) {
//            strResponse = respIterator.next();
//            System.out.println("-->" + strResponse.getMessage());
//        }
//
//        System.out.println("...async calling unaryProcedure");
//        nonbStub.unaryProcedure(request, new UnaryObs());
//        System.out.println("...after async calling unaryProcedure");
//
//        System.out.println("...async calling streamProcedure");
//        nonbStub.streamProcedure(request, new UnaryObs());
//        System.out.println("...after async calling streamProcedure");
//
//        System.out.println("...async calling fib");
//        nonbStub.procFibProcedure(request, new UnaryObs());
//        System.out.println("...after async fib");
        String input = "";
        while (!Objects.equals(input, "0")) {
            System.out.println("MENU:");
            System.out.println("1 - Download file");
            System.out.println("2 - Upload file");
            System.out.println("3 - Add car");
            System.out.println("4 - Get all cars");
            System.out.println("5 - Edit car");
            System.out.println("6 - Delete car");

            Scanner scanner = new Scanner(System.in);
            input = scanner.nextLine();

            switch (input) {
                case "1" -> nonbStub.streamFileProcedure(request, new Obs());
                case "2" -> uploadFile(channel);
                case "3" -> addCar(scanner, nonbStub);
                case "4" -> getCars(nonbStub);
                case "5" -> uploadFile(channel);
                case "6" -> addCar(scanner, nonbStub);
            }
        }

        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        channel.shutdown();
    }

    public static void getCars(ServiceNameGrpc.ServiceNameStub nonbStub) {
        CarRequest carRequest = CarRequest.newBuilder()
                .build();
        nonbStub.getCars(carRequest, new CarObs());
    }

    public static void addCar(Scanner scanner, ServiceNameGrpc.ServiceNameStub nonbStub) {
        System.out.printf("Podaj nazwę: ");
        String name = scanner.nextLine();

        System.out.printf("Podaj wiek: ");
        int age = scanner.nextInt();
        scanner.nextLine();

        System.out.printf("Podaj nazwę pliku: ");
        String image = scanner.nextLine();

        System.out.printf("Czy auto jest nowe?" +
                "1 - Tak" +
                "2 - Nie" +
                ": ");
        boolean isNew = false;
        String isNewAnswer = scanner.nextLine();
        if (Objects.equals(isNewAnswer, "1")) {
            isNew = true;
        }
        CarRequest carRequest = CarRequest.newBuilder()
                        .setName(name).setAge(age).setImage(image).setIsNew(isNew)
                        .build();
        nonbStub.addCar(carRequest, new CarObs());
    }

    public static void uploadFile(ManagedChannel channel) throws IOException {
        ServiceNameGrpc.ServiceNameStub fileServiceStub;
        fileServiceStub = ServiceNameGrpc.newStub(channel);
        var streamObserver = fileServiceStub.uploadFile(new fileUploadObs());
        InputStream instr = new FileInputStream("/Users/hitit/sem6/RSI/1-Kapala-Michal/GrpcApp/Images/ClientImages/clientImage.jpg");
        byte[] bytes = new byte[4096];
        int size;

        FileUploadRequest metadata =
                FileUploadRequest.newBuilder()
                        .setMetadata(MetaData.newBuilder().setName("clientOutput").setType("jpg").build())
                        .build();
        streamObserver.onNext(metadata);

        while ((size = instr.read(bytes)) > 0) {
            FileUploadRequest uploadRequest =
                    FileUploadRequest.newBuilder()
                            .setFile(org.example.model.File.newBuilder().setContent(ByteString.copyFrom(bytes, 0, size)).build())
                            .build();
            streamObserver.onNext(uploadRequest);
        }

        instr.close();
        streamObserver.onCompleted();
    }

    private static class Obs implements StreamObserver<ByteResponse>{

        File file = new File("/Users/hitit/sem6/RSI/1-Kapala-Michal/GrpcApp/Images/ClientImages/serverOutput.jpg");
        FileOutputStream fos;

        {
            try {
                fos = new FileOutputStream(file);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        public void onNext(ByteResponse response){
            try {
                if(file.createNewFile())
                    System.out.println("Created file");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                fos.write(response.getChunk().toByteArray());
                System.out.println("Received chunk of " + response.getNumOfBytes() + "bytes");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }


        public void onError(Throwable throwable) {
            System.out.println("-->async onError");
        }

        public void onCompleted(){
            try {
                fos.flush();
                fos.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static class UnaryObs implements StreamObserver<TheResponse> {
        public void onNext(TheResponse theResponse) {
            System.out.println("-->async unary onNext: " +
                    theResponse.getMessage());
        }
        public void onError(Throwable throwable) {
            System.out.println("-->async unary onError");
        }
        public void onCompleted() {
            System.out.println("-->async unary onCompleted");
        }
    }

    private static class CarObs implements StreamObserver<CarResponse> {
        public void onNext(CarResponse carResponse) {
            System.out.println("-->async unary onNext: " +
                    carResponse.getMessage());
        }
        public void onError(Throwable throwable) {
            System.out.println("-->async unary onError");
        }
        public void onCompleted() {
            System.out.println("-->async unary onCompleted");
        }
    }

    private static class fileUploadObs implements StreamObserver<FileUploadResponse> {
        @Override
        public void onNext(FileUploadResponse fileUploadResponse) {
            System.out.println("Uploading...");
        }
        @Override
        public void onError(Throwable throwable) {
            System.out.println(throwable.getMessage());
        }
        @Override
        public void onCompleted() {
            System.out.println("-->async unary onCompleted");
        }
    }
}