import os
import sys
os.chdir(os.path.dirname(__file__))
sys.path.append("./pb2")

from pb2 import calc_pb2, calc_pb2_grpc
import grpc

def call_calc_func(num1, num2):
    with grpc.insecure_channel('localhost:50051') as channel:
        stub = calc_pb2_grpc.CalcServiceStub(channel)
        request = calc_pb2.CalcReq(num1=num1, num2=num2)
        response = stub.Multiply(request)
        return response.result
    
if __name__=="__main__":
    result = call_calc_func(2,5)
    print(result)