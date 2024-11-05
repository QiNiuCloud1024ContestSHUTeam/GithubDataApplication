package com.calc.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import java.io.IOException;

public class CalcServer {
    public static void main(String[] args) throws IOException, InterruptedException {
        Server server = ServerBuilder.forPort(50051)
                .addService(new CalcServiceImpl())
                .build();
        server.start();
        System.out.println("server started, listening on port 50051");
        server.awaitTermination();
    }
}
