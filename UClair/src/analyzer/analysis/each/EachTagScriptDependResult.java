package analyzer.analysis.each;

/************************************************
 * @date	: 2020. 5.07.
 * @책임자 : 이승환
 * @설명  	: EachTagScriptDependResult
 * @변경이력 	: 
 ************************************************/


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
