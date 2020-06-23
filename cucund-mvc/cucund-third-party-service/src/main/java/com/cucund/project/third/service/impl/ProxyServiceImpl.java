package com.cucund.project.third.service.impl;


import com.cucund.project.third.entity.PartyPathDat;
import com.cucund.project.third.service.PartyPathDatService;
import com.cucund.project.third.service.PostageService;
import com.cucund.project.third.service.ProxyService;
import com.cucund.project.third.service.ResultParseService;
import com.cucund.project.tool.utils.exception.SystemError;
import com.cucund.project.tool.utils.json.JSONUtils;
import com.cucund.project.tool.utils.string.MessageUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Slf4j
@Service
public class ProxyServiceImpl implements ProxyService {

	@Autowired
	PartyPathDatService PathDatService;

	@Autowired
	ResultParseService resultParseService;

	@Autowired
    RestTemplate restTemplate;

	@Autowired
	PostageService postageService;

	@Override
	public Map<String, Object> invoke(String pathId, Map<String, String> map) {
		//服务是否欠费
		PartyPathDat dat = PathDatService.selectById(pathId);
		if (!postageService.isArrears(pathId))
			SystemError.throwException("服务欠费");
		HttpEntity httpEntity = createHttpEntity(dat, map);
		String path = dat.getPath();
		//HTTP调用
		ResponseEntity<String> entity = restTemplate.exchange(path, HttpMethod.valueOf(dat.getMethod()), httpEntity, String.class);
		HttpStatus statusCode = entity.getStatusCode();
		if (!HttpStatus.OK.equals(statusCode)) {
			log.error(path + ":" + entity.getStatusCodeValue() + ":" + entity.getBody());
			SystemError.throwException("调用失败");
		}
		//解析
		Map<String, Object> resolve = resultParseService.resolve(entity.getBody(), pathId);
		return resolve;
	}

	/**
	 * 用于构建 HttpEntity
	 *
	 * @param dat
	 * @param map
	 * @return
	 */
	private HttpEntity<Object> createHttpEntity(PartyPathDat dat, Map<String, String> map) {
		String formJson = dat.getFormJson();
		Map<String, String> formMap = getJson2Map(formJson, map);
		String headerJson = dat.getHeaderJson();
		Map<String, String> headerMap = getJson2Map(headerJson, map);
		MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
		mapToMultiValueMap(headerMap, multiValueMap);
		HttpEntity httpEntity = new HttpEntity(formMap, multiValueMap);
		return httpEntity;
	}

	/**
	 * 普通Map转 多参数Map
	 *
	 * @param headerMap
	 * @param multiValueMap
	 */
	private void mapToMultiValueMap(Map<String, String> headerMap, MultiValueMap<String, String> multiValueMap) {
		for (String key : headerMap.keySet()) {
			multiValueMap.put(key, Arrays.asList(headerMap.get(key)));
		}
	}

	//空MAP防止重复创建
	private static Map<String, String> EMPTY = new HashMap<>();

	/**
	 * JSON  替换解析转Map
	 *
	 * @param json
	 * @param map
	 * @return
	 */
	private Map<String, String> getJson2Map(String json, Map<String, String> map) {
		String xJson = MessageUtil.getMes(json, "\\$\\{(.*?)\\}", "${", "}", map);
		if (StringUtils.isBlank(json))
			return EMPTY;
		try {
			return JSONUtils.json2map(json, String.class);
		} catch (Exception e) {
			log.error("json:" + json);
			log.error("xJson" + xJson);
			log.error("JSON解析异常:", e);
			return EMPTY;
		}
	}
}
