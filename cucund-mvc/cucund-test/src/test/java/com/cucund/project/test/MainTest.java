package com.cucund.project.test;

import com.cucund.project.grpc.client.GRPCClient;
import com.cucund.project.test.grpc.basic.GrpcGrpc;
import com.cucund.project.test.grpc.basic.GrpcRequest;
import com.cucund.project.test.grpc.basic.GrpcResponse;
import io.grpc.Channel;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class MainTest {

    @Test
    public void doStart() throws InterruptedException {
        GRPCClient client = new GRPCClient("localhost", 8910);
        GrpcGrpc.GrpcBlockingStub blockingStub = GrpcGrpc.newBlockingStub(client.getManagedChannel());

        GrpcRequest request = GrpcRequest.newBuilder().setNum1("1").setNum2("2").build();
        GrpcResponse response = blockingStub.calculation(request);
        System.out.println(response.getProduct());


        client.shutdown();
    }


}
