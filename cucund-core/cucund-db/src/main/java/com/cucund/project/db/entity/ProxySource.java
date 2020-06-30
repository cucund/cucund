package com.cucund.project.db.entity;


import lombok.Data;
import org.aspectj.lang.JoinPoint;

@Data
public class ProxySource {

    private ChooseSource chooseSource;

    private JoinPoint joinPoint;

}
