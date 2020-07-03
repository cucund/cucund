package com.cucund.project.tool.utils.string;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public
class MessageUtil {
    /**
     *  组装信息
     * @param soap 原始信息
     * @param regex  条件 占位符
     * @param startStr  删除掉的内容
     * @param endStr 删除掉的内容
     * @param map 根据原始信息占位符中的内容作为key,获取替换进占位符中的内容
     * @return
     */
    public static String getMes(String soap, String regex, String startStr, String endStr, Map<String, String> map) {
        List<String> subUtil = getSubUtil(soap, regex);
        for (String s : subUtil) {
            if (map.containsKey(s) && null != map.get(s)) {
                soap = soap.replace(startStr + s + endStr, map.get(s)!=null?map.get(s):"");
            }
        }
        return soap;
    }

    public static List<String> replace(String soap, String regex){
        List<String> subUtil = getSubUtil(soap, regex);
        return subUtil;
    }

    private static List<String> getSubUtil(String soap, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(soap);
        List<String> list = new ArrayList<>();
        int i = 1;
        while (matcher.find()) {
            list.add(matcher.group(i));
        }
        return list;
    }
}
