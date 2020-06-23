package com.cucund.project.third.service;

import java.util.Map;

public interface ProxyService {

    Map<String, Object> invoke(String pathId, Map<String,String> map );

}
