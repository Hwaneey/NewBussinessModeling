package analyzer.analysis.dangling;

/************************************************
 * @date	: 2020. 5.07.
 * @책임자 : 지이삭
 * @설명  	: 사용자 함수 스크립트 코드의 Dangling tag 분석 결과 클래스.
 * @변경이력 	: 
 ************************************************/


public class ScriptDanglingTagResult implements DanglingTagResult {
	
	/**
	 * 사용하지 않는 태그명.
	 */
	private String danglingTagName;
	
	/**
	 * 사용자 함수 명.
	 */
	private String functionName;

	/**
	 * 스크립트 검사 결과 메시지 포맷.
	 */
	private String resultMessageFormat = "사용자함수 사전의 [ %1s ]함수에서 사용 중 입니다.";
	
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
	 * 사용자 함수명을 반환한다.<br/>
	 * 
	 * @return functionName 사용자 함수명.
	 */
	public String getFunctionName() {
		return functionName;
	}

	/**
	 * 사용자 함수명을 설정한다.<br/>
	 * 
	 * @param functionName 사용자 함수명.
	 */
	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	
}
