package com.cucund.project.mq.reader;

import com.cucund.project.mq.reader.utils.EmptyUtils;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.LinkedHashMap;

public class YmlRabbitMQ {


    private LinkedHashMap<String,String> map ;

    public YmlRabbitMQ(String filename){

        InputStream in = this.getClass().getResourceAsStream(filename);
        //读取配置文件
        loadConfig(in);
    }

    private void loadConfig(InputStream in) {
        map = new Yaml().loadAs(in, LinkedHashMap.class);
    }

    public String getConfig(String configKey) {

        String result = getValue(map, configKey);

        return result;

    }

    public String getValue(LinkedHashMap<String, ?> map, String configKey) {
        String result = "";
        String[] configKeys = configKey.split("\\.");

        LinkedHashMap<String, ?> linkedHashMap = map;
        for (String key : configKeys) {
            Object o = linkedHashMap.get(key);
            if (EmptyUtils.isNotEmpty(o) ) {
                if(o instanceof LinkedHashMap){
                    linkedHashMap = (LinkedHashMap<String, ?>) o;
                }else{
                    result = o.toString();
                }
            } else {
                continue;
            }
        }
        return result;
    }
}
