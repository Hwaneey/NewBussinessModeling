package analyzer.analysis.dangling;

import com.naru.uclair.event.IHMIEvent;

/************************************************
 * @date	: 2020. 5.07.
 * @책임자 : 지이삭
 * @설명  	: 이벤트 정보의 Dangling tag 분석 결과 클래스.
 * @변경이력 	: 
 ************************************************/

public class EventDanglingTagResult implements DanglingTagResult {
	
	/**
	 * 사용하지 않는 태그명.
	 */
	private String danglingTagName;
	
	/**
	 * 이벤트 노드명.
	 */
	private String nodeName;
	
	/**
	 * 이벤트 유형.
	 */
	private int eventType;
	
	/**
	 * 이벤트 명.
	 */
	private String eventName;
	
	/**
	 * 이벤트 검사 결과 메시지 포맷.
	 */
	private String resultMessageFormat = "이벤트 사전의 [ %1s ]-[ %2s ]-[ %3s ]에서 사용 중 입니다.";
	
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
	 * 이벤트 타입을 반환한다.<br/>
	 * 
	 * @return eventType 이벤트 타입.
	 */
	public int getEventType() {
		return eventType;
	}

	/**
	 * 이벤트 타입을 설정한다.<br/>
	 * 
	 * @param eventType 이벤트 타입.
	 */
	public void setEventType(int eventType) {
		this.eventType = eventType;
	}

	/**
	 * 이벤트 명을 반환한다.<br/>
	 * 
	 * @return eventName 이벤트 명.
	 */
	public String getEventName() {
		return eventName;
	}

	/**
	 * 이벤트 명을 설정한다.<br/>
	 * 
	 * @param eventName 이벤트 명.
	 */
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

}
