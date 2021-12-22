package ru.otus.lesson;

import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import java.util.concurrent.CountDownLatch;
import ru.otus.protobuf.generated.ClientRequest;
import ru.otus.protobuf.generated.RemoteServiceGrpc;
import ru.otus.protobuf.generated.ServerResponse;

public class GRPCClient {

    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 8190;

    private static final Object syncObj = new Object();
    private static final int LIMIT = 50;
    private static final int FIRST_VALUE = 1;
    private static final int LAST_VALUE = 10;
    private static long lastValueFromServer = 0;
    private static long currentValue = 0;

    public static void main(String[] args) throws InterruptedException {
        var channel = ManagedChannelBuilder.forAddress(SERVER_HOST, SERVER_PORT)
            .usePlaintext()
            .build();

        var latch = new CountDownLatch(1);

        RemoteServiceGrpc.newStub(channel).getSequence(ClientRequest.newBuilder()
            .setFirstValue(FIRST_VALUE)
            .setLastValue(LAST_VALUE)
            .build(), new StreamObserver<>() {

            @Override
            public void onNext(ServerResponse message) {
                synchronized (syncObj) {
                    lastValueFromServer = message.getCurrentValue();
                    System.out.println(Thread.currentThread().getName() + ": Value from server: " + message.getCurrentValue());
                }
            }

            @Override
            public void onError(Throwable t) {
                System.err.println(t.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println(Thread.currentThread().getName() + ": Completed");
                latch.countDown();
            }
        });

        while (currentValue + lastValueFromServer + FIRST_VALUE <= LIMIT) {
            synchronized (syncObj) {
                currentValue = currentValue + lastValueFromServer + FIRST_VALUE;
            }

            System.out.println(Thread.currentThread().getName() + ": Current value: " + currentValue);
            Thread.sleep(1000);
        }

        latch.await();
        channel.shutdown();
    }
}
