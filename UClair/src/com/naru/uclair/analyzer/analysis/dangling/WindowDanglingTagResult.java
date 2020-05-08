package com.naru.uclair.analyzer.analysis.dangling;

/**
 *
 * <pre>
 * NAME   : com.naru.uclair.analyzer.analysis.dangling.WindowDanglingTagResult.java
 * DESC   : ȭ�� ��ũ��Ʈ ������ Dangling tag �м� ��� Ŭ����.
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
public class WindowDanglingTagResult implements DanglingTagResult {
	
	public static final int TYPE_OPEN = 1;
	public static final int TYPE_CLOSE = 2;
	
	/**
	 * ������� �ʴ� �±׸�.
	 */
	private String danglingTagName;

	/**
	 * ȭ���.
	 */
	private String windowName;
	
	/**
	 * ȭ�� ��ũ��Ʈ ����.(open/close)
	 */
	private int scriptType;
	
	/**
	 * ȭ�� ��ũ��Ʈ �˻� ��� �޽��� ����.
	 */
	private String resultMessageFormat = "[ %1s ]ȭ���� [ %2s ]��ũ��Ʈ ���� ��� �� �Դϴ�.";

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
	 * ȭ����� ��ȯ�Ѵ�.<br/>
	 * 
	 * @return windowName ȭ���.
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
	 * ��ũ��Ʈ Ÿ���� ��ȯ�Ѵ�.<br/>
	 * 
	 * @return scriptType ��ũ��Ʈ Ÿ��.
	 */
	public int getScriptType() {
		return scriptType;
	}

	/**
	 * ��ũ��Ʈ Ÿ���� �����Ѵ�.<br/>
	 * 
	 * @param scriptType ��ũ��Ʈ Ÿ��.
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
