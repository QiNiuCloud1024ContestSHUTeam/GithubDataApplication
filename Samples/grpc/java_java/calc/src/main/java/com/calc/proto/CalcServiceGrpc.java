package com.calc.proto;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.68.1)",
    comments = "Source: calc.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class CalcServiceGrpc {

  private CalcServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "calculator.CalcService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.calc.proto.CalcReq,
      com.calc.proto.CalcRsp> getCalcMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "calc",
      requestType = com.calc.proto.CalcReq.class,
      responseType = com.calc.proto.CalcRsp.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.calc.proto.CalcReq,
      com.calc.proto.CalcRsp> getCalcMethod() {
    io.grpc.MethodDescriptor<com.calc.proto.CalcReq, com.calc.proto.CalcRsp> getCalcMethod;
    if ((getCalcMethod = CalcServiceGrpc.getCalcMethod) == null) {
      synchronized (CalcServiceGrpc.class) {
        if ((getCalcMethod = CalcServiceGrpc.getCalcMethod) == null) {
          CalcServiceGrpc.getCalcMethod = getCalcMethod =
              io.grpc.MethodDescriptor.<com.calc.proto.CalcReq, com.calc.proto.CalcRsp>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "calc"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.calc.proto.CalcReq.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.calc.proto.CalcRsp.getDefaultInstance()))
              .setSchemaDescriptor(new CalcServiceMethodDescriptorSupplier("calc"))
              .build();
        }
      }
    }
    return getCalcMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static CalcServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<CalcServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<CalcServiceStub>() {
        @java.lang.Override
        public CalcServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new CalcServiceStub(channel, callOptions);
        }
      };
    return CalcServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static CalcServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<CalcServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<CalcServiceBlockingStub>() {
        @java.lang.Override
        public CalcServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new CalcServiceBlockingStub(channel, callOptions);
        }
      };
    return CalcServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static CalcServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<CalcServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<CalcServiceFutureStub>() {
        @java.lang.Override
        public CalcServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new CalcServiceFutureStub(channel, callOptions);
        }
      };
    return CalcServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void calc(com.calc.proto.CalcReq request,
        io.grpc.stub.StreamObserver<com.calc.proto.CalcRsp> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCalcMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service CalcService.
   */
  public static abstract class CalcServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return CalcServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service CalcService.
   */
  public static final class CalcServiceStub
      extends io.grpc.stub.AbstractAsyncStub<CalcServiceStub> {
    private CalcServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CalcServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new CalcServiceStub(channel, callOptions);
    }

    /**
     */
    public void calc(com.calc.proto.CalcReq request,
        io.grpc.stub.StreamObserver<com.calc.proto.CalcRsp> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCalcMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service CalcService.
   */
  public static final class CalcServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<CalcServiceBlockingStub> {
    private CalcServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CalcServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new CalcServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.calc.proto.CalcRsp calc(com.calc.proto.CalcReq request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCalcMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service CalcService.
   */
  public static final class CalcServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<CalcServiceFutureStub> {
    private CalcServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CalcServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new CalcServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.calc.proto.CalcRsp> calc(
        com.calc.proto.CalcReq request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCalcMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_CALC = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AsyncService serviceImpl;
    private final int methodId;

    MethodHandlers(AsyncService serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_CALC:
          serviceImpl.calc((com.calc.proto.CalcReq) request,
              (io.grpc.stub.StreamObserver<com.calc.proto.CalcRsp>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getCalcMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.calc.proto.CalcReq,
              com.calc.proto.CalcRsp>(
                service, METHODID_CALC)))
        .build();
  }

  private static abstract class CalcServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    CalcServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.calc.proto.CalcProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("CalcService");
    }
  }

  private static final class CalcServiceFileDescriptorSupplier
      extends CalcServiceBaseDescriptorSupplier {
    CalcServiceFileDescriptorSupplier() {}
  }

  private static final class CalcServiceMethodDescriptorSupplier
      extends CalcServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    CalcServiceMethodDescriptorSupplier(java.lang.String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (CalcServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new CalcServiceFileDescriptorSupplier())
              .addMethod(getCalcMethod())
              .build();
        }
      }
    }
    return result;
  }
}
