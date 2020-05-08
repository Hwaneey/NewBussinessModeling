package com.naru.uclair.analyzer.analysis.each;

/**
 *
 * <pre>
 * NAME   : 
 * DESC   : 
 *
 * references : 설계서 NARU-XXX-XXX-XXX
 *
 * Copyright 2012 NARU Technology All rights reserved
 * <pre>
 *
 * @author US Laboratory naruteclab4
 * @since 2012. 7. 4.
 * @version 1.0
 *
 */
public class EachTagScriptDependResult {
	/**
	 * 태그 아이디.
	 */
	private String tagId;
	/**
	 * script 함수명.
	 */
	private String functionName;

	/**
	 * 태그 아이디를 반환한다.<br/>
	 * 
	 * @return tagId 태그 아이디.
	 */
	public String getTagId() {
		return tagId;
	}

	/**
	 * 태그 아이디를 설정한다.<br/>
	 * 
	 * @param tagId 태그 아이디.
	 */
	public void setTagId(String tagId) {
		this.tagId = tagId;
	}

	/**
	 * script 함수 명을 반환한다.<br/>
	 * 
	 * @return functionName 함수 명.
	 */
	public String getFunctionName() {
		return functionName;
	}

	/**
	 * script 함수 명을 설정한다.<br/>
	 * 
	 * @param functionName 함수 명.
	 */
	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}
	
}
