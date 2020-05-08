package com.naru.uclair.analyzer.analysis.dangling;

import com.naru.uclair.event.IHMIEvent;

/**
 *
 * <pre>
 * NAME   : com.naru.uclair.analyzer.analysis.dangling.EventDanglingTagResult.java
 * DESC   : �̺�Ʈ ������ Dangling tag �м� ��� Ŭ����.
 *
 * references : ���輭 NARU-XXX-XXX-XXX
 *
 * Copyright 2012 NARU Technology All rights reserved
 * <pre>
 *
 * @author US Laboratory naruteclab4
 * @since 2012. 6. 25.
 * @version 1.0
 *
 */
public class EventDanglingTagResult implements DanglingTagResult {
	
	/**
	 * ������� �ʴ� �±׸�.
	 */
	private String danglingTagName;
	
	/**
	 * �̺�Ʈ ����.
	 */
	private String nodeName;
	
	/**
	 * �̺�Ʈ ����.
	 */
	private int eventType;
	
	/**
	 * �̺�Ʈ ��.
	 */
	private String eventName;
	
	/**
	 * �̺�Ʈ �˻� ��� �޽��� ����.
	 */
	private String resultMessageFormat = "�̺�Ʈ ������ [ %1s ]-[ %2s ]-[ %3s ]���� ��� �� �Դϴ�.";
	
	/* (non-Javadoc)
	 * @see com.naru.uclair.analyzer.analysis.dangling.DanglingTagResult#setDanglingTagName(java.lang.String)
	 */
	public void setDanglingTagName(String tagKey) {
		this.danglingTagName = tagKey;
	}
	
	/* (non-Javadoc)
	 * @see com.naru.uclair.analyzer.analysis.dangling.DanglingTagResult#getDanglingTagName()
	 */
	@Override
	public String getDanglingTagName() {
		return danglingTagName;
	}

	/* (non-Javadoc)
	 * @see com.naru.uclair.analyzer.analysis.dangling.DanglingTagResult#getDanglingInfoType()
	 */
	@Override
	public int getDanglingInfoType() {
		return RESULT_TYPE_EVENT;
	}

	/* (non-Javadoc)
	 * @see com.naru.uclair.analyzer.analysis.dangling.DanglingTagResult#getDanglingResultMessage()
	 */
	@Override
	public String getDanglingResultMessage() {
		return String.format(resultMessageFormat, 
				getNodeName(), 
				IHMIEvent.EVENT_TYPES[getEventType()], 
				getEventName());
	}
	
	/**
	 * ������ ��ȯ�Ѵ�.<br/>
	 * 
	 * @return nodeName ����.
	 */
	public String getNodeName() {
		return nodeName;
	}

	/**
	 * ������ �����Ѵ�.<br/>
	 * 
	 * @param nodeName ����.
	 */
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	/**
	 * �̺�Ʈ Ÿ���� ��ȯ�Ѵ�.<br/>
	 * 
	 * @return eventType �̺�Ʈ Ÿ��.
	 */
	public int getEventType() {
		return eventType;
	}

	/**
	 * �̺�Ʈ Ÿ���� �����Ѵ�.<br/>
	 * 
	 * @param eventType �̺�Ʈ Ÿ��.
	 */
	public void setEventType(int eventType) {
		this.eventType = eventType;
	}

	/**
	 * �̺�Ʈ ���� ��ȯ�Ѵ�.<br/>
	 * 
	 * @return eventName �̺�Ʈ ��.
	 */
	public String getEventName() {
		return eventName;
	}

	/**
	 * �̺�Ʈ ���� �����Ѵ�.<br/>
	 * 
	 * @param eventName �̺�Ʈ ��.
	 */
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

}
