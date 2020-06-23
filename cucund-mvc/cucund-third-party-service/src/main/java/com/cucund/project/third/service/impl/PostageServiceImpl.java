package com.cucund.project.third.service.impl;

import com.cucund.project.third.dao.PostageDatMapper;
import com.cucund.project.third.entity.PostageDat;
import com.cucund.project.third.service.PostageService;
import com.cucund.project.third.strategy.postage.*;
import com.cucund.project.tool.utils.strategy.StrategyUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class PostageServiceImpl implements PostageService {


    @Autowired
    PostageDatMapper postageDatMapper;

    StrategyUtil<PostageDat,Boolean> utils = new StrategyUtil<>();

    @PostConstruct
    public void init(){
        utils.add(new UnlimitedStrategy())
                .add(new CountCostStrategy())
                .add(new CostStrategy())
                .add(new DefaultCostStrategy());
    }

    @Override
    public boolean isArrears(String id) {
        if(StringUtils.isBlank(id))
            return true;
        PostageDat postageDat = postageDatMapper.selectById(id);
        if(postageDat==null)
            return true;
        return utils.loop(postageDat);
    }
}
