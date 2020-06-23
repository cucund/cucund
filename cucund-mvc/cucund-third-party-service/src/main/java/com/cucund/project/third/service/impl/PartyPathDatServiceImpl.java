package com.cucund.project.third.service.impl;

import com.cucund.project.spring.config.EnvHolder;
import com.cucund.project.third.dao.PartyPathDatMapper;
import com.cucund.project.third.dao.PartyRouterDatMapper;
import com.cucund.project.third.entity.PartyPathDat;
import com.cucund.project.third.entity.PartyRouterDat;
import com.cucund.project.third.service.PartyPathDatService;
import com.cucund.project.tool.utils.Assert;
import com.cucund.project.tool.utils.exception.SystemError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PartyPathDatServiceImpl implements PartyPathDatService {

    @Autowired
    PartyPathDatMapper pathDatMapper;

    @Autowired
    PartyRouterDatMapper routerDatMapper;

    @Override
    public PartyPathDat selectById(String pathId) {
        PartyPathDat pathDat = pathDatMapper.selectById(pathId);
        Assert.isNull(pathDat!=null,"未找到对应数据");
        PartyRouterDat routerDat = routerDatMapper.selectById(pathDat.getRouterId());
        Assert.isNull(routerDat!=null,"未找到对应数据");
        String env = EnvHolder.getInstance().getEnv();
        String domain = null;
        if("fat".equals(env)){
            domain = routerDat.getSandboxDomain();
        }else if("prod".equals(env)){
            domain = routerDat.getProdDomain();
        }else {
            SystemError.throwException("未找到对应域名");
        }
        String path = pathDat.getPath();
        pathDat.setPath(domain + path);
        return pathDat;
    }
}
