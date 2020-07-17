package com.cucund.project.grpc.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.concurrent.TimeUnit;

public class GRPCClient {


    public ManagedChannel managedChannel;

    public GRPCClient(String ip,int port){
        this(ManagedChannelBuilder.forAddress("localhost", 8910).usePlaintext());
    }

    public GRPCClient(ManagedChannelBuilder<?> builder) {
        this.managedChannel = builder.build();
    }

    public ManagedChannel getManagedChannel() {
        return managedChannel;
    }

    public void shutdown() throws InterruptedException {
        managedChannel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }
}
