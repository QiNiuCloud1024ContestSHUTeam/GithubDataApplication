// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: calc.proto

// Protobuf Java Version: 3.25.5
package com.calc.proto;

public final class CalcProto {
  private CalcProto() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_calculator_CalcReq_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_calculator_CalcReq_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_calculator_CalcRsp_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_calculator_CalcRsp_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\ncalc.proto\022\ncalculator\"\037\n\007CalcReq\022\t\n\001a" +
      "\030\001 \001(\005\022\t\n\001b\030\002 \001(\005\"\031\n\007CalcRsp\022\016\n\006result\030\001" +
      " \001(\0052?\n\013CalcService\0220\n\004calc\022\023.calculator" +
      ".CalcReq\032\023.calculator.CalcRspB\035\n\016com.cal" +
      "c.protoB\tCalcProtoP\001b\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_calculator_CalcReq_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_calculator_CalcReq_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_calculator_CalcReq_descriptor,
        new java.lang.String[] { "A", "B", });
    internal_static_calculator_CalcRsp_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_calculator_CalcRsp_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_calculator_CalcRsp_descriptor,
        new java.lang.String[] { "Result", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
