package analyzer.analysis.event;

import java.util.ArrayList;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import analyzer.analysis.virtual.VirtualDepend;
import com.naru.uclair.event.EventGroupList;
import com.naru.uclair.project.EventDictionary;
import com.naru.uclair.tag.Tag;

public class EventDepend {
	/**
	 * �����ּ� ���Ӽ� ��庰 Map ����
	 * key:����
	 * value:�����ּ� ��庰 ����
	 */
	private Map<String, EventGroupDepend> physicalMap;
	
	public EventDepend() {
		physicalMap = new TreeMap<String, EventGroupDepend>();
	}
	
	/**
	 * �̺�Ʈ �������� ���ۼ� ���θ� �м��Ѵ�.
	 * @param eventDictionary
	 */
	public void analyzer(EventDictionary eventDictionary) {
		Iterator<EventGroupList> egIt = eventDictionary.getEventGroupLists().iterator();
		while(egIt.hasNext()) {
			EventGroupList eGroupList = egIt.next();
			EventGroupDepend groupDepend = new EventGroupDepend();
			groupDepend.setNodeId(eGroupList.getName());
			groupDepend.analyzer(eGroupList);
			physicalMap.put(eGroupList.getName(), groupDepend);
		}
	}
		
	/**
	 * ��ü �̺�Ʈ�� ���� ������ ��ȯ�Ѵ�.
	 * �̺�Ʈ ���Ӽ� �м��� ���̺� ǥ���� �����̴�.
	 * @return
	 */
	public List<EventTagDepend> getEventTagDepend() {
		List<EventTagDepend> list = new ArrayList<EventTagDepend>();
		for(EventGroupDepend value : physicalMap.values()) {
			list.addAll(value.getEventTagDepends());
		}
		return list;
	}
	
	public List<Tag> getVirtualTagList(String tagKey) {
		List<Tag> list = new ArrayList<Tag>();
		for(EventGroupDepend value : physicalMap.values()) {
			list.addAll(value.getVirtualTagList(tagKey));
		}
		return list;
	}
	
	public List<VirtualDepend> getVirtualTagEventDepend(String tagKey) {
		List<VirtualDepend> list = new ArrayList<VirtualDepend>();
		for(EventGroupDepend value : physicalMap.values()) {
			list.addAll(value.getVirtualTagEventDepend(tagKey));
		}
		return list;
	}
	
//	public void analyzer(EventDictionary eventDictionary) {
//		Iterator<EventGroupList> egIt = eventDictionary.getEventGroupLists().iterator();
//		while(egIt.hasNext()) {
//			EventGroupList eGroupList = egIt.next();
//			EventGroup eventGroup = eGroupList.getEventGroup(IHMIEvent.TYPE_DATACHANGE);
//			Iterator<IHMIEvent> eventIt = eventGroup.getEvents().iterator();
//			EventGroupDepend groupDepend = new EventGroupDepend();
//			groupDepend.setNodeId(eGroupList.getName());
//			
//			physicalMap.put(eGroupList.getName(), groupDepend);
//			
//			while(eventIt.hasNext()) {
//				IHMIEvent event = eventIt.next();
//				groupDepend.analyzer(eGroupList);
//				getTagList(event.getScriptCode(), "Tag.set");
//				getTagList(event.getScriptCode(), "Tag.get");
//			}
//			eventGroup = eGroupList.getEventGroup(IHMIEvent.TYPE_ALARM);
//			eventIt = eventGroup.getEvents().iterator();
//			while(eventIt.hasNext()) {
//				IHMIEvent event = eventIt.next();
//				getTagList(event.getScriptCode(), "Tag.set");
//				getTagList(event.getScriptCode(), "Tag.get");
//			}
//			eventGroup = eGroupList.getEventGroup(IHMIEvent.TYPE_TIME);
//			eventIt = eventGroup.getEvents().iterator();
//			while(eventIt.hasNext()) {
//				IHMIEvent event = eventIt.next();
//				getTagList(event.getScriptCode(), "Tag.set");
//				getTagList(event.getScriptCode(), "Tag.get");
//			}
//		}
		
//		List<DataTag> tagList = eventDictionary.getAllNoneSystemDataTags();
//		for(DataTag tag : tagList) {
//			if(tag.isHardwareTag()) {
////				System.out.println("����̽� �±��Դϴ�. - �±�Ű : " + tag.getKey());
//				PhysicalTagDepend physicalTagDepend = physicalMap.get(tag.getNode());
//				if(null == physicalTagDepend) {
//					physicalTagDepend = new PhysicalTagDepend(tag.getNode(), tag.getDeviceAdapter().getDeviceName());
//					physicalMap.put(tag.getNode(), physicalTagDepend);
//				}
//				physicalTagDepend.analyzer(tag);
//			}
////			else {
////				System.out.println("���� �±� �Ǵ� �׷��±� �Դϴ�. - �±�Ű : " + tag.getKey());
////			}
//			
//		}
////		System.out.println("");
////		System.out.println("");
////		System.out.println("");
////		
////		
////		Set<String> keySet = physicalMap.keySet();
////		for(String nodeId : keySet) {
////			PhysicalTagDepend physicalTagDepend = physicalMap.get(nodeId);
////			physicalTagDepend.test();
////		}
//	}
}
