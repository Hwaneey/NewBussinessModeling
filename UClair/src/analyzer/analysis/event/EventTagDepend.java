package analyzer.analysis.event;

import java.util.ArrayList;
import java.util.List;

import com.naru.common.util.StringUtils;
import com.naru.uclair.event.AlarmDrivenEvent;
import com.naru.uclair.event.IHMIEvent;
import com.naru.uclair.event.TagDrivenEvent;
import com.naru.uclair.event.TimeDrivenEvent;
import com.naru.uclair.tag.Tag;
import com.naru.uclair.util.TagListWindowDialog;

/** @date	: 2020. 5.07.
* @책임자 : 지한별
* @설명  	: 이벤트 종속성 정보를 담고있는 클래스
* @변경이력 	: **/

public class EventTagDepend {
	/**
	 * 노드 아이디
	 */
	private String nodeId = null;
	/**
	 * 디바이스 아이디
	 */
	private String eventName = null;
	private String conditionTag = null;
	private String inhibitTagName = null;
	
	
	/**
	 * 주소정보 리스트
	 * key:주소
	 * value:태그 리스트
	 */
//	private Map<String, List<Tag>> tagListMap = null;
	private List<Tag> virtualTagList = null;
	private List<Tag> physicalTagList = null;
	
	private String tagString = null;
	/**
	 * 노드 아이디와 디바이스 아이디를 기본 설정한다.
	 * @param nodeId 노드 아이디
	 * @param deviceId 디바이스 아이디
	 */
	public EventTagDepend(String nodeId, String eventName) {
		init();
		
		this.nodeId = nodeId;
		this.eventName = eventName;
	}
	
	/**
	 * 기본 생성자
	 */
	public EventTagDepend() {
		init();
	}
	
	/**
	 * 정보를 초기화 한다.
	 */
	private void init() {
		virtualTagList = new ArrayList<Tag>();
		physicalTagList = new ArrayList<Tag>();
	}
	
	/**
	 * 가상태그 종송성 관계로 물리, 가상 태그 리스트로 분류하였으므로
	 * 해당 이벤트에서 사용된 태그를 반환할 경우에는 가상, 물리 태그 리스트를 합쳐서 반환한다.
	 * @return
	 */
	public List<Tag> getTags() {
		ArrayList<Tag> tagList = new ArrayList<Tag>();
		tagList.addAll(virtualTagList);
		tagList.addAll(physicalTagList);
		return tagList;
	}
	
	public void analyzer(IHMIEvent event) {
		analyzerTagList(event.getScriptCode(), "Tag.set");
		analyzerTagList(event.getScriptCode(), "Tag.get");
		setEventName(event.getName());
		if(event instanceof AlarmDrivenEvent) {
			setConditionTag(((AlarmDrivenEvent) event).getCondition().getConditionSource());
			setInhibitTagName(((AlarmDrivenEvent) event).getInhibitTagName());
		}
		else if(event instanceof TagDrivenEvent) {
			setConditionTag(((TagDrivenEvent) event).getCondition().getConditionSource());
			setInhibitTagName(((TagDrivenEvent) event).getInhibitTagName());
		}
		else if(event instanceof TimeDrivenEvent) {
//			setConditionTag(((TimeDrivenEvent) event).getCondition().getConditionSource());
			setInhibitTagName(((TimeDrivenEvent) event).getInhibitTagName());
		}
	}
	
	public void analyzerTagList(String sentence, String keyword) {
		while(true) {
			try {
				final char delimiter = ' ';
				int start, end;
				String subSentence;
				start = sentence.indexOf(keyword);
				if(start == -1) {
					break;
				}
				subSentence = sentence.substring(start);
				if (subSentence.length() == keyword.length())
					end = keyword.length();
				else {
					end = subSentence.indexOf(delimiter);
					if (end == -1)
						end = subSentence.length();
				}
				
				String returnString = sentence.substring(start, start + end);
				
				returnString = returnString.substring(returnString.indexOf("\"") + 1, returnString.length());
				returnString = returnString.substring(0, returnString.indexOf("\"") );
				Tag tag = TagListWindowDialog.getTag(returnString);
				// 물리태그 여부
				if(null != tag) {
					if(tag.isHardwareTag()) {
						if(!physicalTagList.contains(tag)) {
							physicalTagList.add(tag);
						}
					}
					else {
						if(!virtualTagList.contains(tag)) {
							virtualTagList.add(tag);
						}
					}
				}
				
				if(sentence.length() <=7) {
					break;
				}
				else {
					if(sentence.length() > start + end) {
						sentence = sentence.substring(start + end,sentence.length());
					}
					else {
						break;
					}
				}
			}
			catch(Exception ex) {
				break;
			}
		}
	}
	
	/**
	 * 가상태그 종속성에서 사용하는 태그를 반환한다.
	 * @param tagKey
	 * @return
	 */
	public List<Tag> getVirtualTagList(String tagKey) {
		boolean check = false;
		ArrayList<Tag> returnList = null;
		for(Tag tag : virtualTagList) {
			if(StringUtils.equals(tag.getKey(), tagKey)) {
				check = true;
				break;
			}
		}
		
		if(!check) {
			// 스크립트에 사용된 태그가 없을 경우, 조건 태그와 보류 태그도 검사하여 확인한다.
			// check = true 일 경우에는 확인하지 않고, 물리태그 여부를 확인하여 리턴 리스트에 추가하여 반환한다.
			if(StringUtils.equals(tagKey, inhibitTagName)) {
				check = true;
			}
			else if(StringUtils.equals(tagKey, conditionTag)) {
				check = true;
			}
		}
		
		if(check) {
			returnList = new ArrayList<Tag>();
			returnList.addAll(physicalTagList);
			validateHardwareTag(inhibitTagName, returnList);
			validateHardwareTag(conditionTag, returnList);
		}
		return returnList;
	}
	
	/**
	 * 파라미터로 주어진 태그가 물리태그일 경우 파라미터로 주어진 리스트에 태그를 추가하는 메소드
	 * @param tagKey
	 * @param returnList
	 */
	private void validateHardwareTag(String tagKey, ArrayList<Tag> returnList) {
		Tag tag = TagListWindowDialog.getTag(inhibitTagName);
		if(null != tag) {
			if(tag.isHardwareTag()) {
				returnList.add(tag);
			}
		}
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getConditionTag() {
		return conditionTag;
	}

	public void setConditionTag(String conditionTag) {
		this.conditionTag = conditionTag;
	}

	public String getInhibitTagName() {
		return inhibitTagName;
	}

	public void setInhibitTagName(String inhibitTagName) {
		this.inhibitTagName = inhibitTagName;
	}

	/**
	 * 이벤트에서 사용된 모든 태그를 문자열 형태로 반환한다.
	 */
	public void analyzerToString() {
		for(Tag tag : virtualTagList) {
			if((null != tagString) && (tagString.trim().length() != 0)) {
				tagString = tagString + ", " + tag.getKey();
			}
			else {
				tagString = tag.getKey();
			}
		}
		
		for(Tag tag : physicalTagList) {
			if((null != tagString) && (tagString.trim().length() != 0)) {
				tagString = tagString + ", " + tag.getKey();
			}
			else {
				tagString = tag.getKey();
			}
		}
	}

	public String getTagString() {
		return tagString;
	}

	public void setTagString(String tagString) {
		this.tagString = tagString;
	}
	
}
