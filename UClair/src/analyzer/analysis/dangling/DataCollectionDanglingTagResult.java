package analyzer.analysis.dangling;

import com.naru.common.util.StringUtils;
import com.naru.uclair.collection.ICollectionConstants;

/************************************************
 * @date	: 2020. 5.07.
 * @책임자 : 지이삭
 * @설명  	: 데이터 수집 모델 정보의 Dangling tag 분석 결과 클래스.
 * @변경이력 	: 
 ************************************************/



public class DataCollectionDanglingTagResult implements DanglingTagResult {
	
	/**
	 * 사용하지 않는 태그명.
	 */
	private String danglingTagName;
	
	/**
	 * 데이터 수집 노드명.
	 */
	private String nodeName;
	
	/**
	 * 데이터 수집 유형.
	 */
	private int collectionType;
	
	/**
	 * 데이터 수집 모델 명.
	 */
	private String collectionName;
	
	/**
	 * 데이터 수집 모델 검사 결과 메시지 포맷.
	 */
	private String resultMessageFormat = "데이터 수집 환경의 [ %1s ]-[ %2s ]에서 사용 중 입니다.";
	/**
	 * 트렌드 데이터 수집 모델 검사 결과 메시지 포맷.
	 */
	private String resultMessageFormatForTrend = "데이터 수집 환경의 [ %1s ]-[ %2s ]-[ %3s ]에서 사용 중 입니다.";
	

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
	 * 노드명을 반환한다.<br/>
	 * 
	 * @return nodeName 노드명.
	 */
	public String getNodeName() {
		return nodeName;
	}

	/**
	 * 노드명을 설정한다.<br/>
	 * 
	 * @param nodeName 노드명.
	 */
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	/**
	 * 데이터 수집 모델 타입을 반환한다.<br/>
	 * 
	 * @return collectionType 데이터 수집 모델 타입.
	 */
	public int getCollectionType() {
		return collectionType;
	}

	/**
	 * 데이터 수집 모델 타입을 설정한다.<br/>
	 * 
	 * @param collectionType 데이터 수집 모델 타입. 
	 */
	public void setCollectionType(int collectionType) {
		this.collectionType = collectionType;
	}

	/**
	 * 데이터 수집 모델명을 반환한다.<br/>
	 * 
	 * @return collectionName 데이터 수집 모델명.
	 */
	public String getCollectionName() {
		return collectionName;
	}

	/**
	 * 데이터 수집 모델명을 설정한다.<br/>
	 * 
	 * @param collectionName 데이터 수집 모델명.
	 */
	public void setCollectionName(String collectionName) {
		this.collectionName = collectionName;
	}

	/**
	 * 
	 * 데이터 수집 모델 유형에 따른 명칭을 반환한다.<br/>
	 * - 일치하는 유형이 없는 경우 ICollectionConstants.TREND_COLLECTION_NAME으로 처리한다.
	 * 
	 * @param collectionType
	 * @return 데이터 수집 유형명.
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
