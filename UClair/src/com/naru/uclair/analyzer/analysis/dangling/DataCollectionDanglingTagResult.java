package com.naru.uclair.analyzer.analysis.dangling;

import com.naru.common.util.StringUtils;
import com.naru.uclair.collection.ICollectionConstants;

/**
 *
 * <pre>
 * NAME   : com.naru.uclair.analyzer.analysis.dangling.DataCollectionDanglingTagResult.java
 * DESC   : ������ ���� �� ������ Dangling tag �м� ��� Ŭ����.
 *
 * references : ���輭 NARU-XXX-XXX-XXX
 *
 * Copyright 2012 NARU Technology All rights reserved
 * <pre>
 *
 * @author US Laboratory naruteclab4
 * @since 2012. 6. 26.
 * @version 1.0
 *
 */
public class DataCollectionDanglingTagResult implements DanglingTagResult {
	
	/**
	 * ������� �ʴ� �±׸�.
	 */
	private String danglingTagName;
	
	/**
	 * ������ ���� ����.
	 */
	private String nodeName;
	
	/**
	 * ������ ���� ����.
	 */
	private int collectionType;
	
	/**
	 * ������ ���� �� ��.
	 */
	private String collectionName;
	
	/**
	 * ������ ���� �� �˻� ��� �޽��� ����.
	 */
	private String resultMessageFormat = "������ ���� ȯ���� [ %1s ]-[ %2s ]���� ��� �� �Դϴ�.";
	/**
	 * Ʈ���� ������ ���� �� �˻� ��� �޽��� ����.
	 */
	private String resultMessageFormatForTrend = "������ ���� ȯ���� [ %1s ]-[ %2s ]-[ %3s ]���� ��� �� �Դϴ�.";
	

	/* (non-Javadoc)
	 * @see com.naru.uclair.analyzer.analysis.dangling.DanglingTagResult#getDanglingTagName()
	 */
	@Override
	public String getDanglingTagName() {
		return this.danglingTagName;
	}
	
	/* (non-Javadoc)
	 * @see com.naru.uclair.analyzer.analysis.dangling.DanglingTagResult#setDanglingTagName(java.lang.String)
	 */
	public void setDanglingTagName(String tagKey) {
		this.danglingTagName = tagKey;
	}

	/* (non-Javadoc)
	 * @see com.naru.uclair.analyzer.analysis.dangling.DanglingTagResult#getDanglingInfoType()
	 */
	@Override
	public int getDanglingInfoType() {
		// TODO Auto-generated method stub
		return RESULT_TYPE_COLLECTION;
	}

	/* (non-Javadoc)
	 * @see com.naru.uclair.analyzer.analysis.dangling.DanglingTagResult#getDanglingResultMessage()
	 */
	@Override
	public String getDanglingResultMessage() {
		if(null != getCollectionName()
				&& !StringUtils.isEmpty(getCollectionName(), false)) {
			return String.format(resultMessageFormatForTrend, 
					getNodeName(), 
					getCollectionTypeName(getCollectionType()), 
					getCollectionName());
		}
		return String.format(resultMessageFormat, 
				getNodeName(), 
				getCollectionTypeName(getCollectionType()));
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
	 * ������ ���� �� Ÿ���� ��ȯ�Ѵ�.<br/>
	 * 
	 * @return collectionType ������ ���� �� Ÿ��.
	 */
	public int getCollectionType() {
		return collectionType;
	}

	/**
	 * ������ ���� �� Ÿ���� �����Ѵ�.<br/>
	 * 
	 * @param collectionType ������ ���� �� Ÿ��. 
	 */
	public void setCollectionType(int collectionType) {
		this.collectionType = collectionType;
	}

	/**
	 * ������ ���� �𵨸��� ��ȯ�Ѵ�.<br/>
	 * 
	 * @return collectionName ������ ���� �𵨸�.
	 */
	public String getCollectionName() {
		return collectionName;
	}

	/**
	 * ������ ���� �𵨸��� �����Ѵ�.<br/>
	 * 
	 * @param collectionName ������ ���� �𵨸�.
	 */
	public void setCollectionName(String collectionName) {
		this.collectionName = collectionName;
	}

	/**
	 * 
	 * ������ ���� �� ������ ���� ��Ī�� ��ȯ�Ѵ�.<br/>
	 * - ��ġ�ϴ� ������ ���� ��� ICollectionConstants.TREND_COLLECTION_NAME���� ó���Ѵ�.
	 * 
	 * @param collectionType
	 * @return ������ ���� ������.
	 */
	private String getCollectionTypeName(int collectionType) {
		switch(collectionType) {
		case ICollectionConstants.TYPE_ALARM:
			return ICollectionConstants.ALARM_COLLECTION_NAME;
		case ICollectionConstants.TYPE_OPERATION:
			return ICollectionConstants.OPERATION_COLLECTION_NAME;
		case ICollectionConstants.TYPE_CHANGE:
			return ICollectionConstants.CHANGE_COLLECTION_NAME;
		case ICollectionConstants.TYPE_MINUTE_TREND:
			return ICollectionConstants.TREND_MINUTE_NAME;
		case ICollectionConstants.TYPE_HOUR_TREND:
			return ICollectionConstants.TREND_HOUR_NAME;
		case ICollectionConstants.TYPE_DAY_TREND:
			return ICollectionConstants.TREND_DAY_NAME;
		default:
			return ICollectionConstants.TREND_COLLECTION_NAME;
		}
	}
}
