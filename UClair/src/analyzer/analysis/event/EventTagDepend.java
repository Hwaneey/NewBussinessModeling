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

/**
 * 
 * ���:�̺�Ʈ ���Ӽ� ������ ����ִ� Ŭ����
 * ����:
 * @author parksungsoo
 *
 */
public class EventTagDepend {
	/**
	 * ��� ���̵�
	 */
	private String nodeId = null;
	/**
	 * ����̽� ���̵�
	 */
	private String eventName = null;
	private String conditionTag = null;
	private String inhibitTagName = null;
	
	
	/**
	 * �ּ����� ����Ʈ
	 * key:�ּ�
	 * value:�±� ����Ʈ
	 */
//	private Map<String, List<Tag>> tagListMap = null;
	private List<Tag> virtualTagList = null;
	private List<Tag> physicalTagList = null;
	
	private String tagString = null;
	/**
	 * ��� ���̵�� ����̽� ���̵� �⺻ �����Ѵ�.
	 * @param nodeId ��� ���̵�
	 * @param deviceId ����̽� ���̵�
	 */
	public EventTagDepend(String nodeId, String eventName) {
		init();
		
		this.nodeId = nodeId;
		this.eventName = eventName;
	}
	
	/**
	 * �⺻ ������
	 */
	public EventTagDepend() {
		init();
	}
	
	/**
	 * ������ �ʱ�ȭ �Ѵ�.
	 */
	private void init() {
		virtualTagList = new ArrayList<Tag>();
		physicalTagList = new ArrayList<Tag>();
	}
	
	/**
	 * �����±� ���ۼ� ����� ����, ���� �±� ����Ʈ�� �з��Ͽ����Ƿ�
	 * �ش� �̺�Ʈ���� ���� �±׸� ��ȯ�� ��쿡�� ����, ���� �±� ����Ʈ�� ���ļ� ��ȯ�Ѵ�.
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
				// �����±� ����
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
	 * �����±� ���Ӽ����� ����ϴ� �±׸� ��ȯ�Ѵ�.
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
			// ��ũ��Ʈ�� ���� �±װ� ���� ���, ���� �±׿� ���� �±׵� �˻��Ͽ� Ȯ���Ѵ�.
			// check = true �� ��쿡�� Ȯ������ �ʰ�, �����±� ���θ� Ȯ���Ͽ� ���� ����Ʈ�� �߰��Ͽ� ��ȯ�Ѵ�.
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
	 * �Ķ���ͷ� �־��� �±װ� �����±��� ��� �Ķ���ͷ� �־��� ����Ʈ�� �±׸� �߰��ϴ� �޼ҵ�
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
	 * �̺�Ʈ���� ���� ��� �±׸� ���ڿ� ���·� ��ȯ�Ѵ�.
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
