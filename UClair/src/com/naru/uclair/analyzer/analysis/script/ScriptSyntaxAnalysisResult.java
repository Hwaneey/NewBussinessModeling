/**
 * 
 */
package com.naru.uclair.analyzer.analysis.script;

/**
 *
 * <pre>
 * NAME   : com.naru.uclair.analyzer.analysis.broken.ScriptSyntaxAnalysisResult.java
 * DESC   : Broken 태그 정보 결과 인터페이스.
 *
 * references : 설계서 NARU-XXX-XXX-XXX
 *
 * Copyright 2012 NARU Technology All rights reserved
 * <pre> 
 * @author 김기태
 *
 */
public interface ScriptSyntaxAnalysisResult {


	/**
	 * 
	 * Broken 스크립트명을 반환한다.<br/>
	 * 
	 * @return 태그명(scriptName)
	 */
	public String getScriptSyntaxName();
	
	/**
	 * 
	 * 스크립트명을 설정한다.<br/>
	 * 
	 * @param 스크립트명(scriptName)
	 */
	public void setScriptSyntaxName(String scriptName);
	
	/**
	 * 
	 * 검사 결과 메시지를 반환한다.<br/>
	 * 
	 * @return 검사 결과 메시지
	 */
	public String getScriptSyntaxAnalysisMessage();
}
