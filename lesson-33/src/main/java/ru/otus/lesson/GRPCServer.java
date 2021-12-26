package ru.otus.lesson;

import io.grpc.ServerBuilder;
import java.io.IOException;
import ru.otus.protobuf.generated.RemoteServiceGrpc;

public class GRPCServer {

    public static final int SERVER_PORT = 8190;

    public static void main(String[] args) throws IOException, InterruptedException {
        RemoteServiceGrpc.RemoteServiceImplBase remoteService = new RemoteServiceImpl();
        var server = ServerBuilder
            .forPort(SERVER_PORT)
            .addService(remoteService)
            .build();

        server.start();
        System.out.println(Thread.currentThread().getName() + ": Server waiting for client connections...");
        server.awaitTermination();
    }
}
