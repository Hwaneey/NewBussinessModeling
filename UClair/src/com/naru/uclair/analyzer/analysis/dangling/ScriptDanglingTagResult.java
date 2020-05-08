package com.naru.uclair.analyzer.analysis.dangling;

/**
 *
 * <pre>
 * NAME   : com.naru.uclair.analyzer.analysis.dangling.ScriptDanglingTagResult.java
 * DESC   : ����� �Լ� ��ũ��Ʈ �ڵ��� Dangling tag �м� ��� Ŭ����.
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
public class ScriptDanglingTagResult implements DanglingTagResult {
	
	/**
	 * ������� �ʴ� �±׸�.
	 */
	private String danglingTagName;
	
	/**
	 * ����� �Լ� ��.
	 */
	private String functionName;

	/**
	 * ��ũ��Ʈ �˻� ��� �޽��� ����.
	 */
	private String resultMessageFormat = "������Լ� ������ [ %1s ]�Լ����� ��� �� �Դϴ�.";
	
	/* (non-Javadoc)
	 * @see com.naru.uclair.analyzer.analysis.dangling.DanglingTagResult#getDanglingTagName()
	 */
	@Override
	public String getDanglingTagName() {
		return danglingTagName;
	}

	/* (non-Javadoc)
	 * @see com.naru.uclair.analyzer.analysis.dangling.DanglingTagResult#setDanglingTagName(java.lang.String)
	 */
	@Override
	public void setDanglingTagName(String tagKey) {
		danglingTagName = tagKey;
	}

	/* (non-Javadoc)
	 * @see com.naru.uclair.analyzer.analysis.dangling.DanglingTagResult#getDanglingInfoType()
	 */
	@Override
	public int getDanglingInfoType() {
		return RESULT_TYPE_SCRIPT;
	}

	/* (non-Javadoc)
	 * @see com.naru.uclair.analyzer.analysis.dangling.DanglingTagResult#getDanglingResultMessage()
	 */
	@Override
	public String getDanglingResultMessage() {
		return String.format(resultMessageFormat, getFunctionName());
	}

	/**
	 * ����� �Լ����� ��ȯ�Ѵ�.<br/>
	 * 
	 * @return functionName ����� �Լ���.
	 */
	public String getFunctionName() {
		return functionName;
	}

	/**
	 * ����� �Լ����� �����Ѵ�.<br/>
	 * 
	 * @param functionName ����� �Լ���.
	 */
	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	
}
