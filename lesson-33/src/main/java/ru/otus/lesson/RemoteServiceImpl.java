package ru.otus.lesson;

import io.grpc.stub.StreamObserver;
import ru.otus.protobuf.generated.ClientRequest;
import ru.otus.protobuf.generated.RemoteServiceGrpc;
import ru.otus.protobuf.generated.ServerResponse;

public class RemoteServiceImpl extends RemoteServiceGrpc.RemoteServiceImplBase {

    @Override
    public void getSequence(ClientRequest request, StreamObserver<ServerResponse> responseObserver) {
        for (long i = request.getFirstValue(); i <= request.getLastValue(); i++) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            responseObserver.onNext(response(i));
        }
        responseObserver.onCompleted();
    }

    private ServerResponse response(Long currentValue) {
        return ServerResponse.newBuilder()
            .setCurrentValue(currentValue)
            .build();
    }
}
