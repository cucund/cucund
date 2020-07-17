package com.cucund.project.test.grpc;

import com.cucund.project.test.grpc.basic.GrpcGrpc;
import com.cucund.project.test.grpc.basic.GrpcRequest;
import com.cucund.project.test.grpc.basic.GrpcResponse;
import io.grpc.stub.StreamObserver;

public class Test extends GrpcGrpc.GrpcImplBase {

    @Override
    public void calculation(GrpcRequest request, StreamObserver<GrpcResponse> responseObserver) {// 获取数据信息
        int num1 = Integer.parseInt(request.getNum1());
        int num2 = Integer.parseInt(request.getNum2());
        // 计算数据
        GrpcResponse response =
                GrpcResponse.newBuilder()
                        .setSum(String.valueOf(num1 + num2))
                        .setSub(String.valueOf(num1 - num2))
                        .setProduct(String.valueOf(num1 * num2))
                        .build();
        // 返回数据，完成此次请求
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
