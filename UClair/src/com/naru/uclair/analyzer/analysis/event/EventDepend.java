package com.naru.uclair.analyzer.analysis.event;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.naru.uclair.analyzer.analysis.virtual.VirtualDepend;
import com.naru.uclair.event.EventGroupList;
import com.naru.uclair.project.EventDictionary;
import com.naru.uclair.tag.Tag;

public class EventDepend {
	/**
	 * 물리주소 종속성 노드별 Map 정보
	 * key:노드명
	 * value:물리주소 노드별 정보
	 */
	private Map<String, EventGroupDepend> physicalMap;
	
	public EventDepend() {
		physicalMap = new TreeMap<String, EventGroupDepend>();
	}
	
	/**
	 * 이벤트 사전에서 종송석 여부를 분석한다.
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
	 * 전체 이벤트에 대한 정보를 반환한다.
	 * 이벤트 종속성 분석의 테이블에 표시할 내용이다.
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
////				System.out.println("디바이스 태그입니다. - 태그키 : " + tag.getKey());
//				PhysicalTagDepend physicalTagDepend = physicalMap.get(tag.getNode());
//				if(null == physicalTagDepend) {
//					physicalTagDepend = new PhysicalTagDepend(tag.getNode(), tag.getDeviceAdapter().getDeviceName());
//					physicalMap.put(tag.getNode(), physicalTagDepend);
//				}
//				physicalTagDepend.analyzer(tag);
//			}
////			else {
////				System.out.println("가상 태그 또는 그룹태그 입니다. - 태그키 : " + tag.getKey());
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
