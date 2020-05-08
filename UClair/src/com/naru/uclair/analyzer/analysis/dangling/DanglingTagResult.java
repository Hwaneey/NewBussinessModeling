package com.naru.uclair.analyzer.analysis.dangling;

import com.naru.uclair.analyzer.constants.AnalyzerConstants;

/**
 *
 * <pre>
 * NAME   : com.naru.uclair.analyzer.analysis.broken.BrokenTagResult.java
 * DESC   : Broken 태그 정보 결과 인터페이스.
 *
 * references : 설계서 NARU-XXX-XXX-XXX
 *
 * Copyright 2012 NARU Technology All rights reserved
 * <pre>
 *
 * @author US Laboratory naruteclab4
 * @since 2012. 6. 25.
 * @version 1.0
 *
 */
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
