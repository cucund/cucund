package com.cucund.project.test.controller;

import com.cucund.project.test.proto.UserProto;
import com.google.protobuf.InvalidProtocolBufferException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class ProtoController {


    @GetMapping("proto")
    public byte[] getProto(){
        UserProto.User.Builder builder = UserProto.User.newBuilder();
        UserProto.User person = builder.setName("123").setAge(10).build();
        byte[] bytes = person.toByteArray();
        return bytes;
    }

    @GetMapping("reproto")
    public String getProto(byte[] data) throws InvalidProtocolBufferException {
        UserProto.User user = UserProto.User.parseFrom(data);
        return user.toString();
    }

}
