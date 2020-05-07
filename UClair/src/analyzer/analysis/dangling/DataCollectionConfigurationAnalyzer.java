package analyzer.analysis.dangling;

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

/************************************************
 * @date	: 2020. 5.07.
 * @책임자 : 지이삭
 * @설명  	: 데이터 수집 환경에 있는 모든 데이터 수집 모델의 Dangling 태그 분석 클래스.
 * @변경이력 	: 
 ************************************************/

public class DataCollectionConfigurationAnalyzer {

	/**
	 * 
	 * 데이터 수집 환경에 있는 모든 데이터 수집 모델의 Dangling 태그를 분석한다.<br/>
	 * - 각 데이터 수집 리스트의 수집 유형별로 분석하여 반환한다.
	 * 
	 * @param existTagList 확인 대상 태그 리스트.
	 * @param dataCollectionConfiguration 데이터 수집 환경.
	 * @return danglingTagResults DataCollectionDanglingTagResult 리스트 정보.
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
	 * 경보 데이터 유형에 있는 모든 데이터 수집 모델의 Dangling 태그를 분석한다.<br/>
	 * - 데이터 수집 모델의 수집 대상 태그를 확인한다.
	 * 
	 * @param existTagList 확인 대상 태그 리스트.
	 * @param nodeName 노드명.
	 * @param alarmDataCollection 경보 데이터 수집 모델 정보.
	 * @return danglingTagResults DataCollectionDanglingTagResult 리스트 정보.
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
	 * 가동 데이터 유형에 있는 모든 데이터 수집 모델의 Dangling 태그를 분석한다.<br/>
	 * - 데이터 수집 모델의 수집 대상 태그를 확인한다.
	 * 
	 * @param existTagList 확인 대상 태그 리스트.
	 * @param nodeName 노드명.
	 * @param operationDataCollection 가동 데이터 수집 모델 정보.
	 * @return danglingTagResults DataCollectionDanglingTagResult 리스트 정보.
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
	 * 값 변경 데이터 유형에 있는 모든 데이터 수집 모델의 Dangling 태그를 분석한다.<br/>
	 * - 데이터 수집 모델의 수집 대상 태그를 확인한다.
	 * 
	 * @param existTagList 확인 대상 태그 리스트.
	 * @param nodeName 노드명.
	 * @param changeDataCollection 값 변경 데이터 수집 모델 정보.
	 * @return danglingTagResults DataCollectionDanglingTagResult 리스트 정보.
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
	 * 트렌드 데이터 유형에 있는 모든 데이터 수집 모델의 Dangling 태그를 분석한다.<br/>
	 * - 데이터 수집 모델의 수집 대상 태그를 확인한다.
	 * 
	 * @param existTagList 확인 대상 태그 리스트.
	 * @param nodeName 노드명.
	 * @param trendDataCollections 트렌드 데이터 수집 모델 정보.
	 * @return danglingTagResults DataCollectionDanglingTagResult 리스트 정보.
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
