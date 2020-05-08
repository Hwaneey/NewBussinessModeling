package com.naru.uclair.analyzer.analysis.dangling;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.naru.uclair.project.ScriptDictionary;
import com.naru.uclair.script.Script;

/**
 *
 * <pre>
 * NAME   : com.naru.uclair.analyzer.analysis.dangling.ScriptDictionaryAnalyzer.java
 * DESC   : ������Լ� ������ �ִ� ��� ��ũ��Ʈ �ڵ��� Dangling �±� �м� Ŭ����.
 *
 * references : ���輭 NARU-XXX-XXX-XXX
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
	 * ������Լ� ������ �ִ� ��� ��ũ��Ʈ�� Dangling �±׸� �м��Ѵ�.<br/>
	 * - �� ��ũ��Ʈ �ڵ带 �м��Ͽ� ��ȯ�Ѵ�.
	 * 
	 * @param existTagList Ȯ�� ��� �±� ����Ʈ.
	 * @param scriptDictionary ������Լ� ����.
	 * @return danglingTagResults EventDanglingTagResult ����Ʈ ����.
	 */
	public static List<DanglingTagResult> scriptDictionaryAnalyze(
			List<String> existTagList, ScriptDictionary scriptDictionary) {
		// DanglingTagResultList
		ArrayList<DanglingTagResult> danglingTagResults = new ArrayList<DanglingTagResult>();
		
		Collection<Script> scripts = scriptDictionary.getAllScripts();
		
		List<DanglingTagResult> scriptResultList;
		for(Script script : scripts) {
			scriptResultList = scriptAnalyze(existTagList, script);
			if(null != scriptResultList
					&& !scriptResultList.isEmpty()) {
				danglingTagResults.addAll(scriptResultList);
			}
		}
		
		return danglingTagResults;
	}
	
	/**
	 * 
	 * ��ũ��Ʈ �ڵ忡 �ִ� ��� �±׿� ���� Dangling �±׸� �м��Ѵ�.<br/>
	 * - ��ũ��Ʈ �ڵ��� �±� ����Ʈ ���� �� Ȯ���Ѵ�.
	 * 
	 * @param existTagList Ȯ�� ��� �±� ����Ʈ.
	 * @param script ��ũ��Ʈ ����.
	 * @return danglingTagResults ScriptDanglingTagResult ����Ʈ ����.
	 */
	private static List<DanglingTagResult> scriptAnalyze(List<String> existTagList, Script script) {
		ArrayList<DanglingTagResult> danglingTagResults = new ArrayList<DanglingTagResult>();
		String code = script.getCode();
		
		if(!code.contains("Tag.")) {
			return null;
		}
		
		List<String> scriptTagList = scriptTagGenerate(code);
		for(String tagKey : scriptTagList) {
			if(!existTagList.contains(tagKey)) {
				ScriptDanglingTagResult danglingTagResult = new ScriptDanglingTagResult();
				danglingTagResult.setDanglingTagName(tagKey);
				danglingTagResult.setFunctionName(script.getName());
				
				danglingTagResults.add(danglingTagResult);
			}
		}
		
		return danglingTagResults;
	}
	
	/**
	 * 
	 * ��ũ��Ʈ �ڵ忡 ���Ǵ� ��� �±׸� ����Ʈ�� ��ȯ�Ѵ�.<br/>
	 * - �±� �Լ� ���� ���θ� Ȯ���Ͽ� �����Ѵ�.
	 * 
	 * @param scriptCode ��ũ��Ʈ �ڵ�.
	 * @return scriptTagList �±� ����Ʈ.
	 */
	public static List<String> scriptTagGenerate(String scriptCode) {
		if(null == scriptCode) {
			// TODO �߸��� ��ũ��Ʈ �ڵ� �α�.
			return null;
		}
		
		if(!scriptCode.contains("Tag.")) {
			// TODO tag ���� �α�.
			return null;
		}

		// Tag. �� ���� �ε����� ã�´�.
		int tagIndex = scriptCode.indexOf("Tag.");
		// Tag. ���� �ε������� �ڵ带 �ڸ���.
		scriptCode = scriptCode.substring(tagIndex, scriptCode.length());
		// ���� �±� ����Ʈ.
		ArrayList<String> scriptTagList = new ArrayList<String>();
		
		// �±�key ã�� �ڵ�.
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
