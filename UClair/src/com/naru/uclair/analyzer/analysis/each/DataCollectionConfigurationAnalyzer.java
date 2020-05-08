package com.naru.uclair.analyzer.analysis.each;

import java.util.Collection;
import java.util.LinkedList;
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
	 * ������ ���� ȯ�濡 �ִ� ��� ������ ���� ���� �±׸� �м��Ѵ�.<br/>
	 * - �� ������ ���� ����Ʈ�� ���� �������� �м��Ͽ� ��ȯ�Ѵ�.
	 * 
	 * @param existTagList Ȯ�� ��� �±� ����Ʈ.
	 * @param dataCollectionConfiguration ������ ���� ȯ��.
	 * @return dependTagResultList EachTagDataCollectionDependResult ����Ʈ ����.
	 */
	public static List<EachTagDataCollectionDependResult> dataCollectionAnalyzer(List<String> existTagList, DataCollectionConfiguration dataCollectionConfiguration) {
		LinkedList<EachTagDataCollectionDependResult> dependTagResultList = new LinkedList<EachTagDataCollectionDependResult>();
		
		Collection<CollectionList> collectionList = dataCollectionConfiguration.getAllCollectionList();
		
		List<EachTagDataCollectionDependResult> collectionResultList;
		for(CollectionList cl : collectionList) {
			collectionResultList = alarmCollectionAnalyze(existTagList, 
					cl.getName(), 
					cl.getAlarmCollection());
			if(null != collectionResultList
					&& !collectionResultList.isEmpty()) {
				dependTagResultList.addAll(collectionResultList);
			}
			
			collectionResultList = operationCollectionAnalyze(existTagList, 
					cl.getName(), 
					cl.getOperationCollection());
			if(null != collectionResultList
					&& !collectionResultList.isEmpty()) {
				dependTagResultList.addAll(collectionResultList);
			}
			
			collectionResultList = changeDataCollectionAnalyze(existTagList, 
					cl.getName(), 
					cl.getChangeCollection());
			if(null != collectionResultList
					&& !collectionResultList.isEmpty()) {
				dependTagResultList.addAll(collectionResultList);
			}
			
			collectionResultList = trendDataCollectionAnalyze(existTagList, 
					cl.getName(), 
					cl.getTrendCollections());
			if(null != collectionResultList
					&& !collectionResultList.isEmpty()) {
				dependTagResultList.addAll(collectionResultList);
			}
			
		}
		
		return dependTagResultList;
	}
	
	/**
	 * 
	 * �溸 ������ ������ �ִ� ��� ������ ���� ���� �±׸� �м��Ѵ�.<br/>
	 * - ������ ���� ���� ���� ��� �±׸� Ȯ���Ѵ�.
	 * 
	 * @param existTagList Ȯ�� ��� �±� ����Ʈ.
	 * @param nodeName ����.
	 * @param alarmDataCollection �溸 ������ ���� �� ����.
	 * @return dependResultList EachTagDataCollectionDependResult ����Ʈ ����.
	 */
	private static List<EachTagDataCollectionDependResult> alarmCollectionAnalyze(List<String> existTagList, String nodeName, AlarmDataCollection alarmDataCollection) {
		LinkedList<EachTagDataCollectionDependResult> dependResultList = new LinkedList<EachTagDataCollectionDependResult>();
		
		List<String> collectionTargetList = alarmDataCollection.getCollectionList();
		for(String tagName : collectionTargetList) {
			if(!existTagList.contains(tagName)) {
				continue;
			}
			
			EachTagDataCollectionDependResult dependResult = new EachTagDataCollectionDependResult();
			dependResult.setTagId(tagName);
			dependResult.setNodeName(nodeName);
			dependResult.setCollectionType(ICollectionConstants.TYPE_ALARM);
			
			dependResultList.add(dependResult);
		}
		return dependResultList;
	}
	
	/**
	 * 
	 * ���� ������ ������ �ִ� ��� ������ ���� ���� �±׸� �м��Ѵ�.<br/>
	 * - ������ ���� ���� ���� ��� �±׸� Ȯ���Ѵ�.
	 * 
	 * @param existTagList Ȯ�� ��� �±� ����Ʈ.
	 * @param nodeName ����.
	 * @param operationDataCollection ���� ������ ���� �� ����.
	 * @return dependResultList EachTagDataCollectionDependResult ����Ʈ ����.
	 */
	private static List<EachTagDataCollectionDependResult> operationCollectionAnalyze(List<String> existTagList, String nodeName, OperationDataCollection operationDataCollection) {
		LinkedList<EachTagDataCollectionDependResult> dependResultList = new LinkedList<EachTagDataCollectionDependResult>();
		
		List<String> collectionTargetList = operationDataCollection.getCollectionList();
		for(String tagName : collectionTargetList) {
			if(!existTagList.contains(tagName)) {
				continue;
			}
			
			EachTagDataCollectionDependResult dependResult = new EachTagDataCollectionDependResult();
			dependResult.setTagId(tagName);
			dependResult.setNodeName(nodeName);
			dependResult.setCollectionType(ICollectionConstants.TYPE_OPERATION);
			
			dependResultList.add(dependResult);
		}
		return dependResultList;
	}
	
	/**
	 * 
	 * �� ���� ������ ������ �ִ� ��� ������ ���� ���� �±׸� �м��Ѵ�.<br/>
	 * - ������ ���� ���� ���� ��� �±׸� Ȯ���Ѵ�.
	 * 
	 * @param existTagList Ȯ�� ��� �±� ����Ʈ.
	 * @param nodeName ����.
	 * @param changeDataCollection �� ���� ������ ���� �� ����.
	 * @return dependResultList EachTagDataCollectionDependResult ����Ʈ ����.
	 */
	private static List<EachTagDataCollectionDependResult> changeDataCollectionAnalyze(List<String> existTagList, String nodeName, ChangeDataCollection changeDataCollection) {
		LinkedList<EachTagDataCollectionDependResult> dependResultList = new LinkedList<EachTagDataCollectionDependResult>();
		
		List<String> collectionTargetList = changeDataCollection.getCollectionList();
		for(String tagName : collectionTargetList) {
			if(!existTagList.contains(tagName)) {
				continue;
			}
			
			EachTagDataCollectionDependResult dependResult = new EachTagDataCollectionDependResult();
			dependResult.setTagId(tagName);
			dependResult.setNodeName(nodeName);
			dependResult.setCollectionType(ICollectionConstants.TYPE_CHANGE);
			
			dependResultList.add(dependResult);
		}
		return dependResultList;
	}
	
	/**
	 * 
	 * Ʈ���� ������ ������ �ִ� ��� ������ ���� ���� �±׸� �м��Ѵ�.<br/>
	 * - ������ ���� ���� ���� ��� �±׸� Ȯ���Ѵ�.
	 * 
	 * @param existTagList Ȯ�� ��� �±� ����Ʈ.
	 * @param nodeName ����.
	 * @param trendDataCollections Ʈ���� ������ ���� �� ����.
	 * @return dependResultList EachTagDataCollectionDependResult ����Ʈ ����.
	 */
	private static List<EachTagDataCollectionDependResult> trendDataCollectionAnalyze(List<String> existTagList, String nodeName, List<TrendDataCollection> trendDataCollections) {
		LinkedList<EachTagDataCollectionDependResult> dependResultList = new LinkedList<EachTagDataCollectionDependResult>();
		
		for(TrendDataCollection trendDataCollection : trendDataCollections) {
			List<String> collectionTargetList = trendDataCollection.getCollectionList();
			for(String tagName : collectionTargetList) {
				
				if(!existTagList.contains(tagName)) {
					continue;
				}
				
				EachTagDataCollectionDependResult dependResult = new EachTagDataCollectionDependResult();
				dependResult.setTagId(tagName);
				dependResult.setNodeName(nodeName);
				dependResult.setCollectionType(trendDataCollection.getType());
				dependResult.setCollectionName(trendDataCollection.getCollectionName());
				
				dependResultList.add(dependResult);
			}
		}
		
		return dependResultList;
	}	
	
}
