package com.cucund.project.tool.utils.js;


import org.apache.commons.lang3.StringUtils;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.util.Map;

/************************************************
 * Copyright (c)  by 
 * All right reserved.
 * Create Date: 2011-6-21
 * Create Author: liurong
 * File Name:  js执行方法
 * Last version:  1.0
 * Function:
 * Last Update Date:
 * Change Log:
**************************************************/	
 
public class ScriptUtil {
	public final static String ERROR="error";
	public final static String OK="ok";
	public final static String EXCEPTION="exerror";
	public static String evel(String str){
		return evel(str, "JavaScript");
	}
	public static String evel(String str,String shortname){
		String s="";
		if(StringUtils.isBlank(str)){
			return s;
		}
		if(StringUtils.isBlank(shortname)){
			shortname="JavaScript";
		}
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName(shortname);
		try {
			str = str.replace("\r", "").replace("\n", "").replace("\r\n", "");
			Object obj=engine.eval(str);
			if(null!=obj){
				s=obj.toString();
			}
		} catch (Exception e) {
			s=EXCEPTION;
		}
		return s;
	}
	public static Object eval(String str,Map<String,Object> map){
		if(StringUtils.isBlank(str))return null;
		int ilen=str.indexOf("return");
		if(ilen<0){
			str=" return "+str;
		}else if(ilen<str.length()) {
			String s=str.substring(ilen+6, ilen+7);
			if(!" ".equals(s)){
				str=" return "+str;
			}
		}
		str=str.trim();
		if(!";".equals(str.substring(str.length()-1))){
			str+=";";
		}
		str = str.replace("\r", "").replace("\n", "").replace("\r\n", "");
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("JavaScript");
		String fs="function do_execute(";
		String fs_end=") {" +str+" }";
		fs+=fs_end;
		if(!(null==map||map.isEmpty())){
			for (String key : map.keySet()) {
				engine.put(key, map.get(key));
			}
		}
		try {
			engine.eval(fs);
			Invocable invokeEngine = (Invocable)engine;
			Object o = invokeEngine.invokeFunction("do_execute");
			return o;
		} catch (Exception e) {
			return EXCEPTION;
		}
	}
}
