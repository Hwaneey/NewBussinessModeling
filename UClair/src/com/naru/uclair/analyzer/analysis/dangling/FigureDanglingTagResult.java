package com.naru.uclair.analyzer.analysis.dangling;

/**
 *
 * <pre>
 * NAME   : com.naru.uclair.analyzer.analysis.dangling.FigureDanglingTagResult.java
 * DESC   : ȭ�� ��ü ������ Dangling tag �м� ��� Ŭ����.
 *
 * references : ���輭 NARU-XXX-XXX-XXX
 *
 * Copyright 2012 NARU Technology All rights reserved
 * <pre>
 *
 * @author US Laboratory naruteclab4
 * @since 2012. 6. 27.
 * @version 1.0
 *
 */
public class FigureDanglingTagResult implements DanglingTagResult {

	/**
	 * ������� �ʴ� �±׸�.
	 */
	private String danglingTagName;
	
	/**
	 * ȭ���.
	 */
	private String windowName;
	
	/**
	 * ȭ�� ��ü��.
	 */
	private String figureName;
	
	/**
	 * ȭ�� ��ũ��Ʈ �˻� ��� �޽��� ����.
	 */
	private String resultMessageFormat = "[ %1s ]ȭ���� [ %2s ] ��ü���� ��� �� �Դϴ�.";
	
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
	 * ȭ����� ��ȯ�Ѵ�.<br/>
	 * 
	 * @return windowName
	 */
	public String getWindowName() {
		return windowName;
	}

	/**
	 * ȭ����� �����Ѵ�.<br/>
	 * 
	 * @param windowName ȭ���.
	 */
	public void setWindowName(String windowName) {
		this.windowName = windowName;
	}

	/**
	 * ȭ�� ��ü���� ��ȯ�Ѵ�.<br/>
	 * 
	 * @return figureName ȭ�� ��ü��.
	 */
	public String getFigureName() {
		return figureName;
	}

	/**
	 * ȭ�� ��ü���� �����Ѵ�.<br/>
	 * 
	 * @param figureName ȭ�� ��ü��.
	 */
	public void setFigureName(String figureName) {
		this.figureName = figureName;
	}

}
