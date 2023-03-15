import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.example.model.TheRequest;
import org.example.model.TheResponse;
import org.example.model.ServiceNameGrpc;

public class GrpcClient {
    public static void main(String[] args) {
        String address = "localhost"; //here we use service on the same host
        int port = 50001;
        ServiceNameGrpc.ServiceNameBlockingStub bStub;
        System.out.println("Running gRPC client...");
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress(address, port) .usePlaintext().build();
        bStub = ServiceNameGrpc.newBlockingStub(channel);
        TheRequest request = TheRequest.newBuilder().setName("Mariusz")
                .setAge(33).build();
        System.out.println("...calling unaryProcedure");
        TheResponse response = bStub.unaryProcedure(request);
        System.out.println("...after calling unaryProcedure");
        System.out.println("--> Response: " + response);
        channel.shutdown();
    }
}