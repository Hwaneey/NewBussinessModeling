package com.naru.uclair.analyzer.analysis.event;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.naru.uclair.analyzer.analysis.virtual.VirtualDepend;
import com.naru.uclair.event.EventGroup;
import com.naru.uclair.event.EventGroupList;
import com.naru.uclair.event.IHMIEvent;
import com.naru.uclair.tag.Tag;

public class EventGroupDepend {
	/**
	 * 노드 아이디
	 */
	private String nodeId = null;
	
	/**
	 * 주소정보 리스트
	 * key:주소
	 * value:태그 리스트
	 */
	private Map<String, EventTagDepend> eventListMap = null;
	
	
	/**
	 * 기본 생성자
	 */
	public EventGroupDepend() {
		init();
	}
	
	/**
	 * 정보를 초기화 한다.
	 */
	private void init() {
		eventListMap = new TreeMap<String, EventTagDepend>();
	}
	
	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public void analyzer(EventGroupList eGroupList) {
		this.nodeId = eGroupList.getName();
		
		EventGroup eventGroup = eGroupList.getEventGroup(IHMIEvent.TYPE_DATACHANGE);
		Iterator<IHMIEvent> eventIt = eventGroup.getEvents().iterator();
		
		while(eventIt.hasNext()) {
			IHMIEvent event = eventIt.next();
			EventTagDepend eventTagDepend = new EventTagDepend();
			eventTagDepend.analyzer(event);
			eventListMap.put(event.getName(), eventTagDepend);
		}
		eventGroup = eGroupList.getEventGroup(IHMIEvent.TYPE_ALARM);
		eventIt = eventGroup.getEvents().iterator();
		while(eventIt.hasNext()) {
			IHMIEvent event = eventIt.next();
			EventTagDepend eventTagDepend = new EventTagDepend();
			eventTagDepend.analyzer(event);
			eventListMap.put(event.getName(), eventTagDepend);
		}
		eventGroup = eGroupList.getEventGroup(IHMIEvent.TYPE_TIME);
		eventIt = eventGroup.getEvents().iterator();
		while(eventIt.hasNext()) {
			IHMIEvent event = eventIt.next();
			EventTagDepend eventTagDepend = new EventTagDepend();
			eventTagDepend.analyzer(event);
			eventListMap.put(event.getName(), eventTagDepend);
		}
	}

	public List<EventTagDepend> getEventTagDepends() {
		ArrayList<EventTagDepend> list = new ArrayList<EventTagDepend>();
		for(EventTagDepend value : eventListMap.values()) {
			list.add(value);
		}
		return list;
	}
	
	public List<Tag> getVirtualTagList(String tagKey) {
		ArrayList<Tag> list = new ArrayList<Tag>();
		for(EventTagDepend value : eventListMap.values()) {
			List<Tag> tagList = value.getVirtualTagList(tagKey);
			if(null != tagList) {
				list.addAll(tagList);
			}
		}		
		return list;
	}
	
	public List<VirtualDepend> getVirtualTagEventDepend(String tagKey) {
		ArrayList<VirtualDepend> list = new ArrayList<VirtualDepend>();
		for(EventTagDepend value : eventListMap.values()) {
			List<Tag> tagList = value.getVirtualTagList(tagKey);
			if(null != tagList) {
				for(Tag tag : tagList) {
					list.add(new VirtualDepend(value.getEventName(), tag));
				}
			}
		}		
		return list;
	}
	
}
