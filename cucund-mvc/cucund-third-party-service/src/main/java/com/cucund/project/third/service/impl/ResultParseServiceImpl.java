package com.cucund.project.third.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cucund.project.third.dao.PartyResultDatMapper;
import com.cucund.project.third.dto.ResolveDto;
import com.cucund.project.third.entity.PartyResultDat;
import com.cucund.project.third.service.ResultParseService;
import com.cucund.project.third.strategy.resolve.JSONResolveStrategy;
import com.cucund.project.third.strategy.resolve.XmlResolveStrategy;
import com.cucund.project.tool.utils.exception.SystemError;
import com.cucund.project.tool.utils.js.ScriptUtil;
import com.cucund.project.tool.utils.json.JSONUtils;
import com.cucund.project.tool.utils.strategy.StrategyUtil;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class ResultParseServiceImpl implements ResultParseService {

    @Autowired
    PartyResultDatMapper partyResultMapper;



    private StrategyUtil<ResolveDto,String> util = new StrategyUtil();
    @PostConstruct
    public void init(){
        util.add(new XmlResolveStrategy());
        util.add(new JSONResolveStrategy());
    }

    /**
     * 解析第三方调用 返回值
     * @param body
     * @param pathId
     * @return
     */
    @Override
    public Map<String, Object> resolve(String body, String pathId) {
        PartyResultDat dat = this.selectByParentId(pathId);
        ResolveDto dto = new ResolveDto();
        String data = util.loop(dto);
        Map<String,Object> map = new HashMap<>();
        map.put("body",data);
        Object eval = ScriptUtil.eval(dat.getExample(), map);
        Map<String, Object> objectMap = null;
        try {
            objectMap = JSONUtils.json2map(eval.toString());
        } catch (Exception e) {
            log.error("js返回:"+data);
            log.error("js驱动方法"+dat.getExample());
            SystemError.throwException("无法通过jS解析");
        }
        return objectMap != null?objectMap:new HashMap<>();
    }

    private PartyResultDat selectByParentId(String pathId) {
        QueryWrapper ew = new QueryWrapper(PartyResultDat.class);
        ew.eq("parentId",pathId);
        ew.eq("del",false);
        PartyResultDat dat = partyResultMapper.selectOne(ew);
        return dat;
    }


}
