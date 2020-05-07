package analyzer.analysis.each;

/************************************************
 * @date	: 2020. 5.07.
 * @책임자 : 이승환
 * @설명  	: EachTagEventDependResult
 * @변경이력 	: 
 ************************************************/


public class EachTagEventDependResult {
	
	/**
	 * 이벤트 유형.
	 */
	private int eventType;
	/**
	 * 이벤트 명
	 */
	private String eventName;
	/**
	 * 태그 아이디.
	 */
	private String tagId;
	/**
	 * 태그 사용 위치.
	 */
	private int tagPosition;
	public static final int POSITION_CONDITION = 0;
	public static final int POSITION_INHIBIT = 1;
	public static final int POSITION_SCRIPT = 2;
	
	private String nodeName;
	
	/**
	 * 이벤트 유형을 반환한다.<br/>
	 * 
	 * @return eventType 이벤트 유형.
	 */
	public int getEventType() {
		return eventType;
	}
	
	/**
	 * 이벤트 유형을 설정한다.<br/>
	 * 
	 * @param eventType 이벤트 유형.
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
	
	/**
	 * 태그 아이디를 반환한다.<br/>
	 * 
	 * @return tagId 태그 아이디.
	 */
	public String getTagId() {
		return tagId;
	}
	
	/**
	 * 태그 아이디를 설정한다.<br/>
	 * 
	 * @param tagId 태그 아이디.
	 */
	public void setTagId(String tagId) {
		this.tagId = tagId;
	}
	
	/**
	 * 이벤트에 사용된 태그의 위치를 반환한다.<br/>
	 * 
	 * @return tagPosition 
	 */
	public int getTagPosition() {
		return tagPosition;
	}
	
	/**
	 * 이벤트에 사용된 태그의 위치를 설정한다.<br/>
	 * 
	 * @param tagPosition 
	 */
	public void setTagPosition(int tagPosition) {
		this.tagPosition = tagPosition;
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

}
