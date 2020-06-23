package com.cucund.project.third.strategy.resolve;

import com.cucund.project.third.dto.ResolveDto;
import com.cucund.project.tool.utils.exception.SystemError;
import com.cucund.project.tool.utils.json.JSONUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class XmlResolveStrategy implements ResolveStrategy {
    @Override
    public boolean condition(ResolveDto resolveDto) {
        if ("xml".equals(resolveDto.getContentType()))
            return true;
        return false;
    }

    @Override
    public String doSomething(ResolveDto resolveDto) {
        String data = null;
        try {
            XmlMapper xml = new XmlMapper();
            JsonNode jsonNode = xml.readTree(resolveDto.getBody());
            data = JSONUtils.obj2json(jsonNode);
        } catch (Exception e) {
            SystemError.throwException("解析回调参数失败");
        }
        return data;
    }
}
