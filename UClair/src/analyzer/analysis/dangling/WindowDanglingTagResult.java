package analyzer.analysis.dangling;


/************************************************
 * @date	: 2020. 5.07.
 * @책임자 : 장성현
 * @설명  	: 화면 스크립트 정보의 Dangling tag 분석 결과 클래스.
 * @변경이력 	: 
 ************************************************/

public class WindowDanglingTagResult implements DanglingTagResult {
	
	public static final int TYPE_OPEN = 1;
	public static final int TYPE_CLOSE = 2;
	
	/**
	 * 사용하지 않는 태그명.
	 */
	private String danglingTagName;

	/**
	 * 화면명.
	 */
	private String windowName;
	
	/**
	 * 화면 스크립트 유형.(open/close)
	 */
	private int scriptType;
	
	/**
	 * 화면 스크립트 검사 결과 메시지 포맷.
	 */
	private String resultMessageFormat = "[ %1s ]화면의 [ %2s ]스크립트 에서 사용 중 입니다.";

	/* (non-Javadoc)
	 * @see com.naru.uclair.analyzer.analysis.dangling.DanglingTagResult#getDanglingTagName()
	 */
	@Override
	public String getDanglingTagName() {
		// TODO Auto-generated method stub
		return danglingTagName;
	}

	/* (non-Javadoc)
	 * @see com.naru.uclair.analyzer.analysis.dangling.DanglingTagResult#setDanglingTagName(java.lang.String)
	 */
	@Override
	public void setDanglingTagName(String tagKey) {
		// TODO Auto-generated method stub
		danglingTagName = tagKey;
	}

	/* (non-Javadoc)
	 * @see com.naru.uclair.analyzer.analysis.dangling.DanglingTagResult#getDanglingInfoType()
	 */
	@Override
	public int getDanglingInfoType() {
		// TODO Auto-generated method stub
		return RESULT_TYPE_WINDOW;
	}

	/* (non-Javadoc)
	 * @see com.naru.uclair.analyzer.analysis.dangling.DanglingTagResult#getDanglingResultMessage()
	 */
	@Override
	public String getDanglingResultMessage() {
		return String.format(resultMessageFormat, 
				getWindowName(), 
				getScriptTypeName(getScriptType()));
	}

	/**
	 * 화면명을 반환한다.<br/>
	 * 
	 * @return windowName 화면명.
	 */
	public String getWindowName() {
		return windowName;
	}

	/**
	 * 화면명을 설정한다.<br/>
	 * 
	 * @param windowName 화면명.
	 */
	public void setWindowName(String windowName) {
		this.windowName = windowName;
	}

	/**
	 * 스크립트 타입을 반환한다.<br/>
	 * 
	 * @return scriptType 스크립트 타입.
	 */
	public int getScriptType() {
		return scriptType;
	}

	/**
	 * 스크립트 타입을 설정한다.<br/>
	 * 
	 * @param scriptType 스크립트 타입.
	 */
	public void setScriptType(int scriptType) {
		if(TYPE_OPEN == scriptType 
				|| TYPE_CLOSE == scriptType) {
			this.scriptType = scriptType;
		}
		else {
			this.scriptType = TYPE_OPEN;
		}
	}
	
	private String getScriptTypeName(int scriptType) {
		switch(scriptType) {
		case TYPE_OPEN:
			return "open";
		case TYPE_CLOSE:
			return "close";
		}
		return "other";
	}

}
