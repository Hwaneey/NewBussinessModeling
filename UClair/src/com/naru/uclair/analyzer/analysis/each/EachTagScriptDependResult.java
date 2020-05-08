package com.naru.uclair.analyzer.analysis.each;

/**
 *
 * <pre>
 * NAME   : 
 * DESC   : 
 *
 * references : ���輭 NARU-XXX-XXX-XXX
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
	 * �±� ���̵�.
	 */
	private String tagId;
	/**
	 * script �Լ���.
	 */
	private String functionName;

	/**
	 * �±� ���̵� ��ȯ�Ѵ�.<br/>
	 * 
	 * @return tagId �±� ���̵�.
	 */
	public String getTagId() {
		return tagId;
	}

	/**
	 * �±� ���̵� �����Ѵ�.<br/>
	 * 
	 * @param tagId �±� ���̵�.
	 */
	public void setTagId(String tagId) {
		this.tagId = tagId;
	}

	/**
	 * script �Լ� ���� ��ȯ�Ѵ�.<br/>
	 * 
	 * @return functionName �Լ� ��.
	 */
	public String getFunctionName() {
		return functionName;
	}

	/**
	 * script �Լ� ���� �����Ѵ�.<br/>
	 * 
	 * @param functionName �Լ� ��.
	 */
	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}
	
}
