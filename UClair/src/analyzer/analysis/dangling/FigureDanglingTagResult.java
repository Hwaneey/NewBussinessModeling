package analyzer.analysis.dangling;

/************************************************
 * @date	: 2020. 5.07.
 * @책임자 : 지이삭
 * @설명  	: 화면 객체 정보의 Dangling tag 분석 결과 클래스.
 * @변경이력 	: 
 ************************************************/


public class FigureDanglingTagResult implements DanglingTagResult {

	/**
	 * 사용하지 않는 태그명.
	 */
	private String danglingTagName;
	
	/**
	 * 화면명.
	 */
	private String windowName;
	
	/**
	 * 화면 객체명.
	 */
	private String figureName;
	
	/**
	 * 화면 스크립트 검사 결과 메시지 포맷.
	 */
	private String resultMessageFormat = "[ %1s ]화면의 [ %2s ] 객체에서 사용 중 입니다.";
	
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
		return RESULT_TYPE_WINDOW_FIGURE;
	}

	/* (non-Javadoc)
	 * @see com.naru.uclair.analyzer.analysis.dangling.DanglingTagResult#getDanglingResultMessage()
	 */
	@Override
	public String getDanglingResultMessage() {
		return String.format(resultMessageFormat, 
				getWindowName(), 
				getFigureName());
	}

	/**
	 * 화면명을 반환한다.<br/>
	 * 
	 * @return windowName
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
	 * 화면 객체명을 반환한다.<br/>
	 * 
	 * @return figureName 화면 객체명.
	 */
	public String getFigureName() {
		return figureName;
	}

	/**
	 * 화면 객체명을 설정한다.<br/>
	 * 
	 * @param figureName 화면 객체명.
	 */
	public void setFigureName(String figureName) {
		this.figureName = figureName;
	}

}
