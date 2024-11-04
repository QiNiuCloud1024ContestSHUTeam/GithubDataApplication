import os
import sys
os.chdir(os.path.dirname(__file__))
sys.path.append("./pb2")

from pb2 import calc_pb2, calc_pb2_grpc
from concurrent import futures
import grpc

class CalcServicer(calc_pb2_grpc.CalcServiceServicer):
    def Multiply(self, request, context):
        print("num1:{}, num2:{}".format(request.num1, request.num2))
        result = request.num1 * request.num2
        return calc_pb2.CalcRsp(result=result)
    
def serve():
    server = grpc.server(futures.ThreadPoolExecutor(max_workers=10))
    calc_pb2_grpc.add_CalcServiceServicer_to_server(CalcServicer(), server)
    server.add_insecure_port('[::]:50051')
    server.start()
    server.wait_for_termination()

if __name__ == '__main__':
    print("server start")
    serve()
    print("server stop")