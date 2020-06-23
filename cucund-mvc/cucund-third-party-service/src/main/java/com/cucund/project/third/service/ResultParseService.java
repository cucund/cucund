package com.cucund.project.third.service;

import java.util.Map;

public interface ResultParseService {
    public Map<String,Object> resolve(String body, String pathId);
}
