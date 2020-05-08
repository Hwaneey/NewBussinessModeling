package com.naru.uclair.analyzer.analysis.dangling;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.naru.uclair.collection.AlarmDataCollection;
import com.naru.uclair.collection.ChangeDataCollection;
import com.naru.uclair.collection.CollectionList;
import com.naru.uclair.collection.ICollectionConstants;
import com.naru.uclair.collection.OperationDataCollection;
import com.naru.uclair.collection.TrendDataCollection;
import com.naru.uclair.project.DataCollectionConfiguration;

/**
 *
 * <pre>
 * NAME   : com.naru.uclair.analyzer.analysis.dangling.DataCollectionConfigurationAnalyzer.java
 * DESC   : ������ ���� ȯ�濡 �ִ� ��� ������ ���� ���� Dangling �±� �м� Ŭ����.
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
public class DataCollectionConfigurationAnalyzer {

	/**
	 * 
	 * ������ ���� ȯ�濡 �ִ� ��� ������ ���� ���� Dangling �±׸� �м��Ѵ�.<br/>
	 * - �� ������ ���� ����Ʈ�� ���� �������� �м��Ͽ� ��ȯ�Ѵ�.
	 * 
	 * @param existTagList Ȯ�� ��� �±� ����Ʈ.
	 * @param dataCollectionConfiguration ������ ���� ȯ��.
	 * @return danglingTagResults DataCollectionDanglingTagResult ����Ʈ ����.
	 */
	public static List<DanglingTagResult> dataCollectionAnalyzer(List<String> existTagList, DataCollectionConfiguration dataCollectionConfiguration) {
		ArrayList<DanglingTagResult> danglingTagResults = new ArrayList<DanglingTagResult>();
		
		Collection<CollectionList> collectionList = dataCollectionConfiguration.getAllCollectionList();
		
		List<DanglingTagResult> collectionResultList;
		for(CollectionList cl : collectionList) {
			collectionResultList = alarmCollectionAnalyze(existTagList, 
					cl.getName(), 
					cl.getAlarmCollection());
			if(null != collectionResultList
					&& !collectionResultList.isEmpty()) {
				danglingTagResults.addAll(collectionResultList);
			}
			
			collectionResultList = operationCollectionAnalyze(existTagList, 
					cl.getName(), 
					cl.getOperationCollection());
			if(null != collectionResultList
					&& !collectionResultList.isEmpty()) {
				danglingTagResults.addAll(collectionResultList);
			}
			
			collectionResultList = changeDataCollectionAnalyze(existTagList, 
					cl.getName(), 
					cl.getChangeCollection());
			if(null != collectionResultList
					&& !collectionResultList.isEmpty()) {
				danglingTagResults.addAll(collectionResultList);
			}
			
			collectionResultList = trendDataCollectionAnalyze(existTagList, 
					cl.getName(), 
					cl.getTrendCollections());
			if(null != collectionResultList
					&& !collectionResultList.isEmpty()) {
				danglingTagResults.addAll(collectionResultList);
			}
			
		}
		
		return danglingTagResults;
	}
	
	/**
	 * 
	 * �溸 ������ ������ �ִ� ��� ������ ���� ���� Dangling �±׸� �м��Ѵ�.<br/>
	 * - ������ ���� ���� ���� ��� �±׸� Ȯ���Ѵ�.
	 * 
	 * @param existTagList Ȯ�� ��� �±� ����Ʈ.
	 * @param nodeName ����.
	 * @param alarmDataCollection �溸 ������ ���� �� ����.
	 * @return danglingTagResults DataCollectionDanglingTagResult ����Ʈ ����.
	 */
	private static List<DanglingTagResult> alarmCollectionAnalyze(List<String> existTagList, String nodeName, AlarmDataCollection alarmDataCollection) {
		ArrayList<DanglingTagResult> danglingTagResults = new ArrayList<DanglingTagResult>();
		
		List<String> collectionTargetList = alarmDataCollection.getCollectionList();
		for(String tagName : collectionTargetList) {
			if(existTagList.contains(tagName)) {
				continue;
			}
			
			DataCollectionDanglingTagResult danglingTagResult = new DataCollectionDanglingTagResult();
			danglingTagResult.setDanglingTagName(tagName);
			danglingTagResult.setNodeName(nodeName);
			danglingTagResult.setCollectionType(ICollectionConstants.TYPE_ALARM);
			
			danglingTagResults.add(danglingTagResult);
		}
		return danglingTagResults;
	}
	
	/**
	 * 
	 * ���� ������ ������ �ִ� ��� ������ ���� ���� Dangling �±׸� �м��Ѵ�.<br/>
	 * - ������ ���� ���� ���� ��� �±׸� Ȯ���Ѵ�.
	 * 
	 * @param existTagList Ȯ�� ��� �±� ����Ʈ.
	 * @param nodeName ����.
	 * @param operationDataCollection ���� ������ ���� �� ����.
	 * @return danglingTagResults DataCollectionDanglingTagResult ����Ʈ ����.
	 */
	private static List<DanglingTagResult> operationCollectionAnalyze(List<String> existTagList, String nodeName, OperationDataCollection operationDataCollection) {
		ArrayList<DanglingTagResult> danglingTagResults = new ArrayList<DanglingTagResult>();
		
		List<String> collectionTargetList = operationDataCollection.getCollectionList();
		for(String tagName : collectionTargetList) {
			if(existTagList.contains(tagName)) {
				continue;
			}
			
			DataCollectionDanglingTagResult danglingTagResult = new DataCollectionDanglingTagResult();
			danglingTagResult.setDanglingTagName(tagName);
			danglingTagResult.setNodeName(nodeName);
			danglingTagResult.setCollectionType(ICollectionConstants.TYPE_OPERATION);
			
			danglingTagResults.add(danglingTagResult);
		}
		return danglingTagResults;
	}
	
	/**
	 * 
	 * �� ���� ������ ������ �ִ� ��� ������ ���� ���� Dangling �±׸� �м��Ѵ�.<br/>
	 * - ������ ���� ���� ���� ��� �±׸� Ȯ���Ѵ�.
	 * 
	 * @param existTagList Ȯ�� ��� �±� ����Ʈ.
	 * @param nodeName ����.
	 * @param changeDataCollection �� ���� ������ ���� �� ����.
	 * @return danglingTagResults DataCollectionDanglingTagResult ����Ʈ ����.
	 */
	private static List<DanglingTagResult> changeDataCollectionAnalyze(List<String> existTagList, String nodeName, ChangeDataCollection changeDataCollection) {
		ArrayList<DanglingTagResult> danglingTagResults = new ArrayList<DanglingTagResult>();
		
		List<String> collectionTargetList = changeDataCollection.getCollectionList();
		for(String tagName : collectionTargetList) {
			if(existTagList.contains(tagName)) {
				continue;
			}
			
			DataCollectionDanglingTagResult danglingTagResult = new DataCollectionDanglingTagResult();
			danglingTagResult.setDanglingTagName(tagName);
			danglingTagResult.setNodeName(nodeName);
			danglingTagResult.setCollectionType(ICollectionConstants.TYPE_CHANGE);
			
			danglingTagResults.add(danglingTagResult);
		}
		return danglingTagResults;
	}
	
	/**
	 * 
	 * Ʈ���� ������ ������ �ִ� ��� ������ ���� ���� Dangling �±׸� �м��Ѵ�.<br/>
	 * - ������ ���� ���� ���� ��� �±׸� Ȯ���Ѵ�.
	 * 
	 * @param existTagList Ȯ�� ��� �±� ����Ʈ.
	 * @param nodeName ����.
	 * @param trendDataCollections Ʈ���� ������ ���� �� ����.
	 * @return danglingTagResults DataCollectionDanglingTagResult ����Ʈ ����.
	 */
	private static List<DanglingTagResult> trendDataCollectionAnalyze(List<String> existTagList, String nodeName, List<TrendDataCollection> trendDataCollections) {
		ArrayList<DanglingTagResult> danglingTagResults = new ArrayList<DanglingTagResult>();
		
		for(TrendDataCollection trendDataCollection : trendDataCollections) {
			List<String> collectionTargetList = trendDataCollection.getCollectionList();
			for(String tagName : collectionTargetList) {
				
				if(existTagList.contains(tagName)) {
					continue;
				}
				
				DataCollectionDanglingTagResult danglingTagResult = new DataCollectionDanglingTagResult();
				danglingTagResult.setDanglingTagName(tagName);
				danglingTagResult.setNodeName(nodeName);
				danglingTagResult.setCollectionType(trendDataCollection.getType());
				danglingTagResult.setCollectionName(trendDataCollection.getCollectionName());
				
				danglingTagResults.add(danglingTagResult);
			}
		}
		
		return danglingTagResults;
	}	
	
}
