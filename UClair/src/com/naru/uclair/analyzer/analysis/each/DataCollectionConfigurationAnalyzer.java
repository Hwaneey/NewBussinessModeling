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
 * DESC   : 데이터 수집 환경에 있는 모든 데이터 수집 모델의 Dangling 태그 분석 클래스.
 *
 * references : 설계서 NARU-XXX-XXX-XXX
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
	 * 데이터 수집 환경에 있는 모든 데이터 수집 모델의 태그를 분석한다.<br/>
	 * - 각 데이터 수집 리스트의 수집 유형별로 분석하여 반환한다.
	 * 
	 * @param existTagList 확인 대상 태그 리스트.
	 * @param dataCollectionConfiguration 데이터 수집 환경.
	 * @return dependTagResultList EachTagDataCollectionDependResult 리스트 정보.
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
	 * 경보 데이터 유형에 있는 모든 데이터 수집 모델의 태그를 분석한다.<br/>
	 * - 데이터 수집 모델의 수집 대상 태그를 확인한다.
	 * 
	 * @param existTagList 확인 대상 태그 리스트.
	 * @param nodeName 노드명.
	 * @param alarmDataCollection 경보 데이터 수집 모델 정보.
	 * @return dependResultList EachTagDataCollectionDependResult 리스트 정보.
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
	 * 가동 데이터 유형에 있는 모든 데이터 수집 모델의 태그를 분석한다.<br/>
	 * - 데이터 수집 모델의 수집 대상 태그를 확인한다.
	 * 
	 * @param existTagList 확인 대상 태그 리스트.
	 * @param nodeName 노드명.
	 * @param operationDataCollection 가동 데이터 수집 모델 정보.
	 * @return dependResultList EachTagDataCollectionDependResult 리스트 정보.
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
	 * 값 변경 데이터 유형에 있는 모든 데이터 수집 모델의 태그를 분석한다.<br/>
	 * - 데이터 수집 모델의 수집 대상 태그를 확인한다.
	 * 
	 * @param existTagList 확인 대상 태그 리스트.
	 * @param nodeName 노드명.
	 * @param changeDataCollection 값 변경 데이터 수집 모델 정보.
	 * @return dependResultList EachTagDataCollectionDependResult 리스트 정보.
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
	 * 트렌드 데이터 유형에 있는 모든 데이터 수집 모델의 태그를 분석한다.<br/>
	 * - 데이터 수집 모델의 수집 대상 태그를 확인한다.
	 * 
	 * @param existTagList 확인 대상 태그 리스트.
	 * @param nodeName 노드명.
	 * @param trendDataCollections 트렌드 데이터 수집 모델 정보.
	 * @return dependResultList EachTagDataCollectionDependResult 리스트 정보.
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
