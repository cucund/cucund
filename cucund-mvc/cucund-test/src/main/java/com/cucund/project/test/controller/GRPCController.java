package com.cucund.project.test.controller;


import com.cucund.project.grpc.client.GRPCClient;
import com.cucund.project.grpc.server.GRPCService;
import com.cucund.project.test.grpc.Test;
import com.cucund.project.test.grpc.basic.GrpcGrpc;
import com.cucund.project.test.grpc.basic.GrpcRequest;
import com.cucund.project.test.grpc.basic.GrpcResponse;
import io.grpc.BindableService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("gRPC")
@Slf4j
public class GRPCController {

    GRPCService grpcService;

    GRPCClient client;

    GrpcGrpc.GrpcBlockingStub blockingStub;

    @PostConstruct
    public void init() throws IOException {
        String start = start();

        log.info(start);

        client = new GRPCClient("localhost", 8910);

        blockingStub = GrpcGrpc.newBlockingStub(client.getManagedChannel());
    }



    @GetMapping("start")
    public String start() throws IOException {
        if(grpcService != null)
            return "已启动";
        List<BindableService> list = new ArrayList<>();
        list.add(new Test());
        grpcService = new GRPCService();
        grpcService.start(8910,list);
        return "启动成功";
    }

    @GetMapping("end")
    public String end(){
        if(grpcService == null){
            return "未启动";
        }
        grpcService.stop();
        grpcService = null;
        return "已停止";
    }


    @GetMapping("client")
    public String client(){
        GrpcRequest request = GrpcRequest.newBuilder().setNum1("1").setNum2("2").build();
        GrpcResponse response = blockingStub.calculation(request);
        return response.toString();
    }

    @PreDestroy
    public void destroyMethod(){
        try {
            client.shutdown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
