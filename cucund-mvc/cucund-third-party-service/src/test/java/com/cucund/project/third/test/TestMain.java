package com.cucund.project.third.test;

import com.cucund.project.third.entity.PartyResultDat;
import com.cucund.project.tool.utils.js.ScriptUtil;
import com.cucund.project.tool.utils.json.JSONUtils;

import java.util.Map;

public class TestMain {

    @org.junit.Test
    public void test() throws Exception {
        PartyResultDat dat = new PartyResultDat();
        dat.setExample("123");
        dat.setKey("23");
        dat.setName("123123");
        String str = null;
        String s = JSONUtils.obj2json(dat);
        Map<String, Object> stringObjectMap = JSONUtils.json2map(s);
        try {
            str = "return JSON.stringify{data: key}";
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(str);
        Object evel = ScriptUtil.evel(str,stringObjectMap);
        System.out.println(evel);
    }

}
