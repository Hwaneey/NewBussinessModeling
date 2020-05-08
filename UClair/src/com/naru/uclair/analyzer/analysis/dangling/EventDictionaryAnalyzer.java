package com.naru.uclair.analyzer.analysis.dangling;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.naru.common.util.StringUtils;
import com.naru.uclair.condition.AlarmStateCondition;
import com.naru.uclair.condition.TagValueCondition;
import com.naru.uclair.event.AlarmDrivenEvent;
import com.naru.uclair.event.EventGroup;
import com.naru.uclair.event.EventGroupList;
import com.naru.uclair.event.IHMIEvent;
import com.naru.uclair.event.TagDrivenEvent;
import com.naru.uclair.event.TimeDrivenEvent;
import com.naru.uclair.project.EventDictionary;

/**
 *
 * <pre>
 * NAME   : com.naru.uclair.analyzer.analysis.dangling.EventDictionaryAnalyzer.java
 * DESC   : �̺�Ʈ ������ �ִ� ��� �̺�Ʈ�� Dangling �±� �м� Ŭ����.
 *
 * references : ���輭 NARU-XXX-XXX-XXX
 *
 * Copyright 2012 NARU Technology All rights reserved
 * <pre>
 *
 * @author US Laboratory naruteclab4
 * @since 2012. 6. 26.
 * @version 1.0
 *
 */
public class EventDictionaryAnalyzer {
	
	
	/**
	 * 
	 * �̺�Ʈ ������ �ִ� ��� �̺�Ʈ�� Dangling �±׸� �м��Ѵ�.<br/>
	 * - �� �̺�Ʈ ����Ʈ�� �̺�Ʈ �׷캰�� �м��Ͽ� ��ȯ�Ѵ�.
	 * 
	 * @param existTagList Ȯ�� ��� �±� ����Ʈ.
	 * @param eventDictionary �̺�Ʈ ����
	 * @return danglingTagResults EventDanglingTagResult ����Ʈ ����.
	 */
	public static List<DanglingTagResult> eventDictionaryAnalyze(List<String> existTagList, EventDictionary eventDictionary) {
		// DanglingTagResultList
		ArrayList<DanglingTagResult> danglingTagResults = new ArrayList<DanglingTagResult>();
		
		Collection<EventGroupList> groupList = eventDictionary.getEventGroupLists();
		
		List<DanglingTagResult> eventResultList;
		for(EventGroupList eventGroupList : groupList) {
			eventResultList = dataChangeEventAnalyze(existTagList, 
					eventGroupList.getEventGroup(IHMIEvent.TYPE_DATACHANGE));
			if(null != eventResultList
					&& !eventResultList.isEmpty()) {
				danglingTagResults.addAll(eventResultList);
			}
			
			eventResultList = alarmEventAnalyze(existTagList, 
					eventGroupList.getEventGroup(IHMIEvent.TYPE_ALARM));
			if(null != eventResultList
					&& !eventResultList.isEmpty()) {
				danglingTagResults.addAll(eventResultList);
			}
			
			eventResultList = timeEventAnalyze(existTagList, 
					eventGroupList.getEventGroup(IHMIEvent.TYPE_TIME));
			if(null != eventResultList
					&& !eventResultList.isEmpty()) {
				danglingTagResults.addAll(eventResultList);
			}
		}
		return danglingTagResults;
	}
	
	/**
	 * 
	 * �±� �� ���� �̺�Ʈ �׷쿡 �ִ� ��� �̺�Ʈ�� Dangling �±׸� �м��Ѵ�.<br/>
	 * - �̺�Ʈ�� ������ {@link TagDrivenEvent}���� �˻��Ѵ�.<br/>
	 * - �̺�Ʈ�� condition tag�� Ȯ���Ѵ�.<br/>
	 * - �̺�Ʈ�� inhibit tag�� Ȯ���Ѵ�.<br/>
	 * - �̺�Ʈ�� ��ũ��Ʈ�� ���Ǵ� �±׸� Ȯ���Ѵ�.
	 * 
	 * @param existTagList Ȯ�� ��� �±� ����Ʈ.
	 * @param eventGroup �±� �� ���� �̺�Ʈ �׷�.
	 * @return danglingTagResults EventDanglingTagResult ����Ʈ ����.
	 */
	private static List<DanglingTagResult> dataChangeEventAnalyze(List<String> existTagList, EventGroup eventGroup) {
		ArrayList<DanglingTagResult> danglingTagResults = new ArrayList<DanglingTagResult>();
		
		Collection<IHMIEvent> events = eventGroup.getEvents();
		for(IHMIEvent event : events) {
			if(!(event instanceof TagDrivenEvent)) {
				// TODO data change event �� �ƴ϶�� �α��Ѵ�.
				continue;
			}
			
			TagDrivenEvent drivenEvent = (TagDrivenEvent) event;
			TagValueCondition condition = drivenEvent.getCondition();
			
			// condition tag ���� ���� �˻�.
			if(!existTagList.contains(condition.getConditionSource())) {
				EventDanglingTagResult brokenTagResult = new EventDanglingTagResult();
				brokenTagResult.setEventType(IHMIEvent.TYPE_DATACHANGE);
				brokenTagResult.setEventName(drivenEvent.getName());
				brokenTagResult.setNodeName(drivenEvent.getNodeName());
				brokenTagResult.setDanglingTagName(condition.getConditionSource());
				
				danglingTagResults.add(brokenTagResult);
			}
			
			// inhibit tag ���� ���� �˻�.
			if(!StringUtils.isEmpty(drivenEvent.getInhibitTagName(), false)
					&& !existTagList.contains(drivenEvent.getInhibitTagName())) {
				EventDanglingTagResult brokenTagResult = new EventDanglingTagResult();
				brokenTagResult.setEventType(IHMIEvent.TYPE_DATACHANGE);
				brokenTagResult.setEventName(drivenEvent.getName());
				brokenTagResult.setNodeName(drivenEvent.getNodeName());
				brokenTagResult.setDanglingTagName(drivenEvent.getInhibitTagName());
				
				danglingTagResults.add(brokenTagResult);
			}
			
			// ��ũ��Ʈ �˻�.
			List<String> scriptTagList = ScriptDictionaryAnalyzer.scriptTagGenerate(drivenEvent.getScriptCode());
			if(null == scriptTagList) {
				continue;
			}
			
			for(String scriptTag : scriptTagList) {
				if(!existTagList.contains(scriptTag)) {
					EventDanglingTagResult brokenTagResult = new EventDanglingTagResult();
					brokenTagResult.setEventType(IHMIEvent.TYPE_DATACHANGE);
					brokenTagResult.setEventName(drivenEvent.getName());
					brokenTagResult.setNodeName(drivenEvent.getNodeName());
					brokenTagResult.setDanglingTagName(scriptTag);
					
					danglingTagResults.add(brokenTagResult);
				}
			}
		}
		return danglingTagResults;
	}
	/**
	 * 
	 * �溸 �̺�Ʈ �׷쿡 �ִ� ��� �̺�Ʈ�� Dangling �±׸� �м��Ѵ�.<br/>
	 * - �̺�Ʈ�� ������ {@link TagDrivenEvent}���� �˻��Ѵ�.<br/>
	 * - �̺�Ʈ�� condition tag�� Ȯ���Ѵ�.<br/>
	 * - �̺�Ʈ�� inhibit tag�� Ȯ���Ѵ�.<br/>
	 * - �̺�Ʈ�� ��ũ��Ʈ�� ���Ǵ� �±׸� Ȯ���Ѵ�.
	 * 
	 * @param existTagList Ȯ�� ��� �±� ����Ʈ.
	 * @param eventGroup �溸 �̺�Ʈ �׷�
	 * @return danglingTagResults EventDanglingTagResult ����Ʈ ����.
	 */
	private static List<DanglingTagResult> alarmEventAnalyze(List<String> existTagList, EventGroup eventGroup) {
		ArrayList<DanglingTagResult> danglingTagResults = new ArrayList<DanglingTagResult>();
		
		Collection<IHMIEvent> events = eventGroup.getEvents();
		for(IHMIEvent event : events) {
			if(!(event instanceof AlarmDrivenEvent)) {
				// TODO alarm event �� �ƴ϶�� �α��Ѵ�.
				continue;
			}
			
			AlarmDrivenEvent drivenEvent = (AlarmDrivenEvent) event;
			AlarmStateCondition condition = drivenEvent.getCondition();
			
			// condition tag ���� ���� �˻�.
			if(!existTagList.contains(condition.getConditionSource())) {
				EventDanglingTagResult brokenTagResult = new EventDanglingTagResult();
				brokenTagResult.setEventType(IHMIEvent.TYPE_ALARM);
				brokenTagResult.setEventName(drivenEvent.getName());
				brokenTagResult.setNodeName(drivenEvent.getNodeName());
				brokenTagResult.setDanglingTagName(condition.getConditionSource());
				
				danglingTagResults.add(brokenTagResult);
			}
			
			// inhibit tag ���� ���� �˻�.
			if(!StringUtils.isEmpty(drivenEvent.getInhibitTagName(), false)
					&& !existTagList.contains(drivenEvent.getInhibitTagName())) {
				EventDanglingTagResult brokenTagResult = new EventDanglingTagResult();
				brokenTagResult.setEventType(IHMIEvent.TYPE_ALARM);
				brokenTagResult.setEventName(drivenEvent.getName());
				brokenTagResult.setNodeName(drivenEvent.getNodeName());
				brokenTagResult.setDanglingTagName(drivenEvent.getInhibitTagName());
				
				danglingTagResults.add(brokenTagResult);
			}
			
			// ��ũ��Ʈ �˻�.
			List<String> scriptTagList = ScriptDictionaryAnalyzer.scriptTagGenerate(drivenEvent.getScriptCode());
			if(null == scriptTagList) {
				continue;
			}
			
			for(String scriptTag : scriptTagList) {
				if(!existTagList.contains(scriptTag)) {
					EventDanglingTagResult brokenTagResult = new EventDanglingTagResult();
					brokenTagResult.setEventType(IHMIEvent.TYPE_ALARM);
					brokenTagResult.setEventName(drivenEvent.getName());
					brokenTagResult.setNodeName(drivenEvent.getNodeName());
					brokenTagResult.setDanglingTagName(scriptTag);
					
					danglingTagResults.add(brokenTagResult);
				}
			}
			
		}
		return danglingTagResults;
	}
	
	/**
	 * 
	 * �ð� �̺�Ʈ �׷쿡 �ִ� ��� �̺�Ʈ�� Dangling �±׸� �м��Ѵ�.<br/>
	 * - �̺�Ʈ�� ������ {@link TagDrivenEvent}���� �˻��Ѵ�.<br/>
	 * - �̺�Ʈ�� condition tag�� Ȯ���Ѵ�.<br/>
	 * - �̺�Ʈ�� inhibit tag�� Ȯ���Ѵ�.<br/>
	 * - �̺�Ʈ�� ��ũ��Ʈ�� ���Ǵ� �±׸� Ȯ���Ѵ�.
	 * 
	 * @param existTagList Ȯ�� ��� �±� ����Ʈ.
	 * @param eventGroup �ð� �̺�Ʈ �׷�.
	 * @return danglingTagResults EventDanglingTagResult ����Ʈ ����.
	 */
	private static List<DanglingTagResult> timeEventAnalyze(List<String> existTagList, EventGroup eventGroup) {
		ArrayList<DanglingTagResult> danglingTagResults = new ArrayList<DanglingTagResult>();
		
		Collection<IHMIEvent> events = eventGroup.getEvents();
		for(IHMIEvent event : events) {
			if(!(event instanceof TimeDrivenEvent)) {
				// TODO time event �� �ƴ϶�� �α��Ѵ�.
				continue;
			}
			
			TimeDrivenEvent drivenEvent = (TimeDrivenEvent) event;
			// TimeEvent�� Condition�� ����.
			
			// inhibit tag ���� ���� �˻�.
			if(!StringUtils.isEmpty(drivenEvent.getInhibitTagName(), false)
					&& !existTagList.contains(drivenEvent.getInhibitTagName())) {
				EventDanglingTagResult brokenTagResult = new EventDanglingTagResult();
				brokenTagResult.setEventType(IHMIEvent.TYPE_TIME);
				brokenTagResult.setEventName(drivenEvent.getName());
				brokenTagResult.setNodeName(drivenEvent.getNodeName());
				brokenTagResult.setDanglingTagName(drivenEvent.getInhibitTagName());
				
				danglingTagResults.add(brokenTagResult);
			}
			
			// ��ũ��Ʈ �˻�.
			List<String> scriptTagList = ScriptDictionaryAnalyzer.scriptTagGenerate(drivenEvent.getScriptCode());
			if(null == scriptTagList) {
				continue;
			}
			
			for(String scriptTag : scriptTagList) {
				if(!existTagList.contains(scriptTag)) {
					EventDanglingTagResult brokenTagResult = new EventDanglingTagResult();
					brokenTagResult.setEventType(IHMIEvent.TYPE_TIME);
					brokenTagResult.setEventName(drivenEvent.getName());
					brokenTagResult.setNodeName(drivenEvent.getNodeName());
					brokenTagResult.setDanglingTagName(scriptTag);
					
					danglingTagResults.add(brokenTagResult);
				}
			}
		}
		return danglingTagResults;
	}

}
