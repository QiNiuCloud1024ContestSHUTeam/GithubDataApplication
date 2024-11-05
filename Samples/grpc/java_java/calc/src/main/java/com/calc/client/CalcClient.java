package com.calc.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import com.calc.proto.CalcServiceGrpc;
import com.calc.proto.CalcReq;
import com.calc.proto.CalcRsp;

public class CalcClient {
    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext()
                .build();
        CalcServiceGrpc.CalcServiceBlockingStub stub = CalcServiceGrpc.newBlockingStub(channel);
        CalcReq req = CalcReq.newBuilder().setA(10).setB(2).build();
        CalcRsp rsp = stub.calc(req);
        System.out.printf("result: %d", rsp.getResult());
        channel.shutdown();
    }
}