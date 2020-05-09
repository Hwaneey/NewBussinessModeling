package  analyzer.analysis.dangling;

import analyzer.constants.AnalyzerConstants;
/************************************************
 * @date	: 2020. 5.07.
 * @책임자 : 장성현
 * @설명  	: Broken 태그 정보 결과 인터페이스.
 * @변경이력 	: 
 ************************************************/

public interface DanglingTagResult {
	
	public static final int RESULT_TYPE_EVENT = 0;
	public static final int RESULT_TYPE_COLLECTION = 1;
	public static final int RESULT_TYPE_SCRIPT = 2;
	public static final int RESULT_TYPE_WINDOW = 3;
	public static final int RESULT_TYPE_WINDOW_FIGURE = 4;
	public static final String[] RESULT_TYPE_NAMES = {AnalyzerConstants.getString("DanglingTagResult.TypeName.Event"),
		AnalyzerConstants.getString("DanglingTagResult.TypeName.Collection"),
		AnalyzerConstants.getString("DanglingTagResult.TypeName.Script"),
		AnalyzerConstants.getString("DanglingTagResult.TypeName.Window"),
		AnalyzerConstants.getString("DanglingTagResult.TypeName.FigureEffect")}; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	
	/**
	 * 
	 * Broken 태그명을 반환한다.<br/>
	 * 
	 * @return 태그명(tagKey)
	 */
	public String getDanglingTagName();
	
	/**
	 * 
	 * Broken 태그명을 설정한다.<br/>
	 * 
	 * @param 태그명(tagKey)
	 */
	public void setDanglingTagName(String tagKey);

	/**
	 * 
	 * Broken 정보 유형을 반환한다.<br/>
	 * 
	 * @return 정보 유형.
	 */
	public int getDanglingInfoType();
	
	/**
	 * 
	 * Broken 검사 결과 메시지를 반환한다.<br/>
	 * 
	 * @return 검사 결과 메시지
	 */
	public String getDanglingResultMessage();
	
}
