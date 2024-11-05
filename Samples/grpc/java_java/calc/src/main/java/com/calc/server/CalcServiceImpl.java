package com.calc.server;

import io.grpc.stub.StreamObserver;
import com.calc.proto.CalcServiceGrpc;
import com.calc.proto.CalcReq;
import com.calc.proto.CalcRsp;

public class CalcServiceImpl extends CalcServiceGrpc.CalcServiceImplBase {
    @Override
    public void calc(CalcReq request, StreamObserver<CalcRsp> responseObserver) {
        int a = request.getA();
        int b = request.getB();
        int result = a + b;
        System.out.println("calc: " + a + "+" + b + "=" + result);
        CalcRsp resp = CalcRsp.newBuilder().setResult(result).build();
        responseObserver.onNext(resp);
        responseObserver.onCompleted();
    }
}
