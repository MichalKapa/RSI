import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.example.model.TheRequest;
import org.example.model.TheResponse;
import org.example.model.ServiceNameGrpc;

import java.util.Iterator;

public class GrpcClient {
    public static void main(String[] args) {
        String address = "localhost"; //here we use service on the same host
        int port = 50001;
        ServiceNameGrpc.ServiceNameBlockingStub bStub;
        ServiceNameGrpc.ServiceNameStub nonbStub;
        System.out.println("Running gRPC client...");
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress(address, port) .usePlaintext().build();
        bStub = ServiceNameGrpc.newBlockingStub(channel);
        nonbStub = ServiceNameGrpc.newStub(channel);
        TheRequest request = TheRequest.newBuilder().setName("Michal")
                .setAge(33).build();
        System.out.println("...calling unaryProcedure");
        TheResponse response = bStub.unaryProcedure(request);
        System.out.println("...after calling unaryProcedure");
        System.out.println("--> Response: " + response);
//
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

        System.out.println("...async calling fib");
        nonbStub.streamFileProcedure(request, new UnaryObs());
        System.out.println("...after async fib");

        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        channel.shutdown();
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
}