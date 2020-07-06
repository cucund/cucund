package com.cucund.project.test.util;

import com.cucund.project.tool.utils.json.JSONUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WordTree {
	
	private Map<Character,Word> map = new ConcurrentHashMap<Character,Word>();

	public Map<Character, Word> getMap() {
		return map;
	}

	public Word addNode(Character node) {
		Word word = map.get(node);
		if(word == null) {
			word = new Word(node);
			map.put(node, word);
		}
		return word;
	}

	@Override
	public String toString() {
		try {
			return JSONUtils.obj2json(map);
		} catch (Exception e) {
			return "";
		}
	}
	
	public Word get(char key) {
		return map.get(key);
	}
	
	public Word remove(char key) {
		return null;
	}
	
	public void removeAll() {
		map.clear();
	}
}
