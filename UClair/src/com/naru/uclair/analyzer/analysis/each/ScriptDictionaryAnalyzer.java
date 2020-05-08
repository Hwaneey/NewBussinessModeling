package com.naru.uclair.analyzer.analysis.each;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import com.naru.uclair.project.ScriptDictionary;
import com.naru.uclair.script.Script;

/**
 *
 * <pre>
 * NAME   : com.naru.uclair.analyzer.analysis.dangling.ScriptDictionaryAnalyzer.java
 * DESC   : 사용자함수 사전에 있는 모든 스크립트 코드의 태그 분석 클래스.
 *
 * references : 설계서 NARU-XXX-XXX-XXX
 *
 * Copyright 2012 NARU Technology All rights reserved
 * <pre>
 *
 * @author US Laboratory naruteclab4
 * @since 2012. 6. 26.
 * @version 1.0
 *
 */
public class ScriptDictionaryAnalyzer {
	
	/**
	 * 
	 * 사용자함수 사전에 있는 모든 스크립트의 태그를 분석한다.<br/>
	 * - 각 스크립트 코드를 분석하여 반환한다.
	 * 
	 * @param existTagList 확인 대상 태그 리스트.
	 * @param scriptDictionary 사용자함수 사전.
	 * @return danglingTagResults EventDanglingTagResult 리스트 정보.
	 */
	public static List<EachTagScriptDependResult> scriptDictionaryAnalyze(
			List<String> existTagList, ScriptDictionary scriptDictionary) {
		// 
		LinkedList<EachTagScriptDependResult> dependResultList = new LinkedList<EachTagScriptDependResult>();
		
		Collection<Script> scripts = scriptDictionary.getAllScripts();
		
		List<EachTagScriptDependResult> scriptResultList;
		for(Script script : scripts) {
			scriptResultList = scriptAnalyze(existTagList, script);
			if(null != scriptResultList
					&& !scriptResultList.isEmpty()) {
				dependResultList.addAll(scriptResultList);
			}
		}
		
		return dependResultList;
	}
	
	/**
	 * 
	 * 스크립트 코드에 있는 모든 태그에 대해 태그를 분석한다.<br/>
	 * - 스크립트 코드의 태그 리스트 생성 후 확인한다.
	 * 
	 * @param existTagList 확인 대상 태그 리스트.
	 * @param script 스크립트 정보.
	 * @return danglingTagResults ScriptDanglingTagResult 리스트 정보.
	 */
	private static List<EachTagScriptDependResult> scriptAnalyze(List<String> existTagList, Script script) {
		LinkedList<EachTagScriptDependResult> dependResultList = new LinkedList<EachTagScriptDependResult>();
		String code = script.getCode();
		
		if(!code.contains("Tag.")) {
			return null;
		}
		
		List<String> scriptTagList = scriptTagGenerate(code);
		for(String tagKey : scriptTagList) {
			if(existTagList.contains(tagKey)) {
				EachTagScriptDependResult dependResult = new EachTagScriptDependResult();
				dependResult.setTagId(tagKey);
				dependResult.setFunctionName(script.getName());
				
				dependResultList.add(dependResult);
			}
		}
		
		return dependResultList;
	}
	
	/**
	 * 
	 * 스크립트 코드에 사용되는 모든 태그를 리스트로 반환한다.<br/>
	 * - 태그 함수 존재 여부를 확인하여 생성한다.
	 * 
	 * @param scriptCode 스크립트 코드.
	 * @return scriptTagList 태그 리스트.
	 */
	public static List<String> scriptTagGenerate(String scriptCode) {
		if(null == scriptCode) {
			// TODO 잘못된 스크립트 코드 로깅.
			return null;
		}
		
		if(!scriptCode.contains("Tag.")) {
			// TODO tag 없음 로깅.
			return null;
		}

		// Tag. 의 시작 인덱스를 찾는다.
		int tagIndex = scriptCode.indexOf("Tag.");
		// Tag. 시작 인덱스부터 코드를 자른다.
		scriptCode = scriptCode.substring(tagIndex, scriptCode.length());
		// 사용된 태그 리스트.
		LinkedList<String> scriptTagList = new LinkedList<String>();
		
		// 태그key 찾기 코드.
		while(scriptCode.contains("Tag.")) {
			int startIndex = 0;
			int lastIndex = 0;
			boolean started = false;
			
			for(int charIndex = 0; charIndex < scriptCode.length(); charIndex++) {
				char c = scriptCode.charAt(charIndex);
				
				if(!started) {
					if(c == '"') {
						started = true;
						startIndex = charIndex;
					}
				}
				else {
					if(c == '"') {
						started = false;
						lastIndex = charIndex;
						scriptTagList.add(scriptCode.substring(startIndex + 1, charIndex));
						break;
					}
				}
			}
			scriptCode = scriptCode.substring(lastIndex, scriptCode.length()); 
			
			tagIndex = scriptCode.indexOf("Tag.");
			if(tagIndex < 0) break; 
			scriptCode = scriptCode.substring(tagIndex, scriptCode.length());
		}
		
		return scriptTagList;
	}

}
