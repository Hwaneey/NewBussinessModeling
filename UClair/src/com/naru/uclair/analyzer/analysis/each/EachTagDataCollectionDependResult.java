package com.naru.uclair.analyzer.analysis.each;

/**
 * 
 * <pre>
 * NAME   : com.naru.uclair.analyzer.analysis.each.EachTagDataCollectionDependResult.java
 * DESC   : ������ ���� �� ���� �±� ���Ӽ� �м� ��� Ŭ����.
 * 
 * references : ���輭 NARU-XXX-XXX-XXX
 * 
 * Copyright 2012 NARU Technology All rights reserved
 * 
 * <pre>
 * 
 * @author US Laboratory naruteclab4
 * @since 2012. 7. 4.
 * @version 1.0
 * 
 */
public class EachTagDataCollectionDependResult {
	/**
	 * ������ ���� �� ����.<br/>
	 * - ICollectionConstants �� ���ǵ� ����.
	 */
	private int collectionType;
	/**
	 * ������ ���� �𵨸�.
	 */
	private String collectionName;
	/**
	 * ��� �±� ���̵�.
	 */
	private String tagId;
	/**
	 * ����.
	 */
	private String nodeName;

	/**
	 * ������ ���� ������ ��ȯ�Ѵ�.<br/>
	 * 
	 * @return collectionType ������ ���� ����.
	 */
	public int getCollectionType() {
		return collectionType;
	}

	/**
	 * ������ ���� ������ �����Ѵ�.<br/>
	 * 
	 * @param collectionType
	 *            ������ ���� ����.
	 */
	public void setCollectionType(int collectionType) {
		this.collectionType = collectionType;
	}

	/**
	 * ������ ���� �� ���� ��ȯ�Ѵ�.<br/>
	 * - �⺻ ���� ��(������ ����, �溸, ����)�� �⺻ �̸��� ��ȯ�Ѵ�.
	 * 
	 * @return collectionName ������ ���� �𵨸�.
	 */
	public String getCollectionName() {
		return collectionName;
	}

	/**
	 * ������ ���� �𵨸��� �����Ѵ�.<br/>
	 * - �⺻ ���� ��(������ ����, �溸, ����)�� ������ ���õȴ�.
	 * 
	 * @param collectionName
	 *            ������ ���� �𵨸�.
	 */
	public void setCollectionName(String collectionName) {
		this.collectionName = collectionName;
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
	 * @param tagId
	 *            �±� ���̵�.
	 */
	public void setTagId(String tagId) {
		this.tagId = tagId;
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
	 * @param nodeName
	 *            ����.
	 */
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

}
