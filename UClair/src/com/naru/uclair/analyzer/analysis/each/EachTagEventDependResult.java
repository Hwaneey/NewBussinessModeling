package com.naru.uclair.analyzer.analysis.each;

/**
 *
 * <pre>
 * NAME   : 
 * DESC   : 
 *
 * references : ���輭 NARU-XXX-XXX-XXX
 *
 * Copyright 2012 NARU Technology All rights reserved
 * <pre>
 *
 * @author US Laboratory naruteclab4
 * @since 2012. 7. 3.
 * @version 1.0
 *
 */
public class EachTagEventDependResult {
	
	/**
	 * �̺�Ʈ ����.
	 */
	private int eventType;
	/**
	 * �̺�Ʈ ��
	 */
	private String eventName;
	/**
	 * �±� ���̵�.
	 */
	private String tagId;
	/**
	 * �±� ��� ��ġ.
	 */
	private int tagPosition;
	public static final int POSITION_CONDITION = 0;
	public static final int POSITION_INHIBIT = 1;
	public static final int POSITION_SCRIPT = 2;
	
	private String nodeName;
	
	/**
	 * �̺�Ʈ ������ ��ȯ�Ѵ�.<br/>
	 * 
	 * @return eventType �̺�Ʈ ����.
	 */
	public int getEventType() {
		return eventType;
	}
	
	/**
	 * �̺�Ʈ ������ �����Ѵ�.<br/>
	 * 
	 * @param eventType �̺�Ʈ ����.
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
	
	/**
	 * �±� ���̵� ��ȯ�Ѵ�.<br/>
	 * 
	 * @return tagId �±� ���̵�.
	 */
	public String getTagId() {
		return tagId;
	}
	
	/**
	 * �±� ���̵� �����Ѵ�.<br/>
	 * 
	 * @param tagId �±� ���̵�.
	 */
	public void setTagId(String tagId) {
		this.tagId = tagId;
	}
	
	/**
	 * �̺�Ʈ�� ���� �±��� ��ġ�� ��ȯ�Ѵ�.<br/>
	 * 
	 * @return tagPosition 
	 */
	public int getTagPosition() {
		return tagPosition;
	}
	
	/**
	 * �̺�Ʈ�� ���� �±��� ��ġ�� �����Ѵ�.<br/>
	 * 
	 * @param tagPosition 
	 */
	public void setTagPosition(int tagPosition) {
		this.tagPosition = tagPosition;
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

}
