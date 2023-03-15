import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import java.io.IOException;
import org.example.model.TheRequest;
import org.example.model.TheResponse;
import org.example.model.ServiceNameGrpc;

public class GrpcServer {
    public static void main(String[] args) {
        int port = 50001;
        System.out.println("Starting gRPC server...");
        Server server = ServerBuilder.forPort(port)
                .addService(new ServiceNameImpl()).build();
        try {
            server.start();
            System.out.println("...Server started");
            server.awaitTermination();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static class ServiceNameImpl extends ServiceNameGrpc.ServiceNameImplBase {
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
    }
}
