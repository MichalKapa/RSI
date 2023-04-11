import com.google.protobuf.ByteString;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

import org.example.model.*;

public class GrpcServer {
    static ArrayList<Car> cars = new ArrayList<>();

    public static void main(String[] args) {
        int port = 50001;
        System.out.println("Starting gRPC server...");
        Server server = ServerBuilder.forPort(port)
                .addService(new ServiceNameImpl()).build();
        try {
            server.start();
            System.out.println("...Server started");
            server.awaitTermination();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        cars.add(new Car("Romek", 12, "car1.jpg", true));
        cars.add(new Car("Tomek", 20, "car2.jpg", true));
        cars.add(new Car("Bob", 40, "car3.jpg", false));

    }

    static class ServiceNameImpl extends ServiceNameGrpc.ServiceNameImplBase {

        public void getCars(CarRequest req,
                            StreamObserver<CarResponse> responseObserver) {
            //find by name
            StringBuilder message = new StringBuilder();
            for (Car car: cars
                 ) {
                message.append(car).append("\n");
            }

            CarResponse response = CarResponse.newBuilder()
                    .setMessage(message.toString())
                    .build();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }

        public void addCar(CarRequest req,
                                   StreamObserver<CarResponse> responseObserver) {
            Car car = new Car(req.getName(), req.getAge(), req.getImage(), req.getIsNew());
            CarResponse response = CarResponse.newBuilder()
                    .setMessage("Created car: " + car)
                    .build();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }

        public void unaryProcedure(TheRequest req,
                                   StreamObserver<TheResponse> responseObserver) {
            String msg;
            System.out.println("...called UnaryProcedure - start");
            if (req.getAge() > 18)
                msg = "Mr/Ms "+ req.getName();
            else
                msg = "Boy/Girl";
            TheResponse response = TheResponse.newBuilder()
                    .setMessage("Hello " + msg)
                    .build();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            responseObserver.onNext(response);
            responseObserver.onCompleted();
            System.out.println("...called UnaryProcedure - end");
        }
        public void streamProcedure(TheRequest req,
                                    StreamObserver<TheResponse> responseObserver) {
            for (int i = 0; i < 6; i++) {
                TheResponse response = TheResponse.newBuilder()
                        .setMessage("Stream chunk " + (i + 1)).build();
                // [enter here Thread.sleep to easier trace the operation]
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                responseObserver.onNext(response);
            }
            responseObserver.onCompleted();
        }

        public void procFibProcedure(TheRequest req,
                                    StreamObserver<TheResponse> responseObserver) {

            int count=10;
            boolean isSquare = true;

            int n1=1,n2=1,n3;
            TheResponse response = TheResponse.newBuilder()
                    .setMessage(n1 + " : " + (isSquare ? 1 : "")).build();
            // [enter here Thread.sleep to easier trace the operation]
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            responseObserver.onNext(response);

            response = TheResponse.newBuilder()
                    .setMessage(n2  + " : " + (isSquare ? 1 : "")).build();
            // [enter here Thread.sleep to easier trace the operation]
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            responseObserver.onNext(response);

            for (int i=2;i<count;++i) {
                n3=n1+n2;
                response = TheResponse.newBuilder()
                        .setMessage(n3 + " : " + (isSquare ? (int)Math.pow(n3, 2) : "")).build();
                // [enter here Thread.sleep to easier trace the operation]
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                responseObserver.onNext(response);
                n1=n2;
                n2=n3;
            }
            responseObserver.onCompleted();
        }

        public void streamFileProcedure(ByteRequest req,
                                        StreamObserver<ByteResponse> responseObserver) {

            String absPath = "/Users/hitit/sem6/RSI/1-Kapala-Michal/GrpcApp/Images/";
            int BUF_SIZE = 4096;
            byte[] bytes;

            try (FileInputStream fis = new FileInputStream(absPath + "ServerImages/serverImage.jpg")){
                while(fis.available() > 0){
                    bytes = fis.readNBytes(BUF_SIZE);
                    ByteResponse response = ByteResponse.newBuilder()
                            .setChunk(ByteString.copyFrom(bytes))
                            .setNumOfBytes(BUF_SIZE)
                            .build();
                    responseObserver.onNext(response);
                    Thread.sleep(400);
                }
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
            responseObserver.onCompleted();
        }
        private static final Path SERVER_BASE_PATH = Paths.get("/Users/hitit/sem6/RSI/1-Kapala-Michal/GrpcApp/Images/ServerImages");
        @Override
        public StreamObserver<FileUploadRequest> uploadFile(StreamObserver<FileUploadResponse> responseObserver) {
            return new StreamObserver<>() {


                // upload context variables
                OutputStream writer;
                Status status = Status.IN_PROGRESS;

                @Override
                public void onNext(FileUploadRequest fileUploadRequest) {
                    try{
                        if(fileUploadRequest.hasMetadata()){
                            writer = getFilePath(fileUploadRequest);
                        }else{
                            writeFile(writer, fileUploadRequest.getFile().getContent());
                        }
                    }catch (IOException e){
                        this.onError(e);
                    }
                }

                @Override
                public void onError(Throwable throwable) {
                    status = Status.FAILED;
                    this.onCompleted();
                }

                @Override
                public void onCompleted() {
                    closeFile(writer);
                    status = Status.IN_PROGRESS.equals(status) ? Status.SUCCESS : status;
                    FileUploadResponse response = FileUploadResponse.newBuilder()
                            .setStatus(status)
                            .build();
                    responseObserver.onNext(response);
                    responseObserver.onCompleted();
                }
            };
        }

        private OutputStream getFilePath(FileUploadRequest request) throws IOException {
            var fileName = request.getMetadata().getName() + "." + request.getMetadata().getType();
            return Files.newOutputStream(SERVER_BASE_PATH.resolve(fileName), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        }

        private void writeFile(OutputStream writer, ByteString content) throws IOException {
            writer.write(content.toByteArray());
            writer.flush();
        }

        private void closeFile(OutputStream writer){
            try {
                writer.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
