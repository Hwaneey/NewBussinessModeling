/**
 * 
 */
package com.naru.uclair.analyzer.analysis.script;

/**
 *
 * <pre>
 * NAME   : com.naru.uclair.analyzer.analysis.broken.ScriptSyntaxAnalysisResult.java
 * DESC   : Broken �±� ���� ��� �������̽�.
 *
 * references : ���輭 NARU-XXX-XXX-XXX
 *
 * Copyright 2012 NARU Technology All rights reserved
 * <pre> 
 * @author �����
 *
 */
public interface ScriptSyntaxAnalysisResult {


	/**
	 * 
	 * Broken ��ũ��Ʈ���� ��ȯ�Ѵ�.<br/>
	 * 
	 * @return �±׸�(scriptName)
	 */
	public String getScriptSyntaxName();
	
	/**
	 * 
	 * ��ũ��Ʈ���� �����Ѵ�.<br/>
	 * 
	 * @param ��ũ��Ʈ��(scriptName)
	 */
	public void setScriptSyntaxName(String scriptName);
	
	/**
	 * 
	 * �˻� ��� �޽����� ��ȯ�Ѵ�.<br/>
	 * 
	 * @return �˻� ��� �޽���
	 */
	public String getScriptSyntaxAnalysisMessage();
}
