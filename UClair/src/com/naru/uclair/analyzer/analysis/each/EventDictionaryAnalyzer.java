package com.naru.uclair.analyzer.analysis.each;

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
 * DESC   : �̺�Ʈ ������ �ִ� ��� �̺�Ʈ�� �±� �м� Ŭ����.
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
	 * �̺�Ʈ ������ �ִ� ��� �̺�Ʈ�� �±׸� �м��Ѵ�.<br/>
	 * - �� �̺�Ʈ ����Ʈ�� �̺�Ʈ �׷캰�� �м��Ͽ� ��ȯ�Ѵ�.
	 * 
	 * @param existTagList Ȯ�� ��� �±� ����Ʈ.
	 * @param eventDictionary �̺�Ʈ ����
	 * @return eachTagResults EachTagEventDependResult ����Ʈ ����.
	 */
	public static List<EachTagEventDependResult> eventDictionaryAnalyze(List<String> existTagList, EventDictionary eventDictionary) {
		// DanglingTagResultList
		ArrayList<EachTagEventDependResult> eachTagResults = new ArrayList<EachTagEventDependResult>();
		
		Collection<EventGroupList> groupList = eventDictionary.getEventGroupLists();
		
		List<EachTagEventDependResult> eventResultList;
		for(EventGroupList eventGroupList : groupList) {
			eventResultList = dataChangeEventAnalyze(existTagList, 
					eventGroupList.getEventGroup(IHMIEvent.TYPE_DATACHANGE));
			if(null != eventResultList
					&& !eventResultList.isEmpty()) {
				eachTagResults.addAll(eventResultList);
			}
			
			eventResultList = alarmEventAnalyze(existTagList, 
					eventGroupList.getEventGroup(IHMIEvent.TYPE_ALARM));
			if(null != eventResultList
					&& !eventResultList.isEmpty()) {
				eachTagResults.addAll(eventResultList);
			}
			
			eventResultList = timeEventAnalyze(existTagList, 
					eventGroupList.getEventGroup(IHMIEvent.TYPE_TIME));
			if(null != eventResultList
					&& !eventResultList.isEmpty()) {
				eachTagResults.addAll(eventResultList);
			}
		}
		return eachTagResults;
	}
	
	/**
	 * 
	 * �±� �� ���� �̺�Ʈ �׷쿡 �ִ� ��� �̺�Ʈ�� �±׸� �м��Ѵ�.<br/>
	 * - �̺�Ʈ�� ������ {@link TagDrivenEvent}���� �˻��Ѵ�.<br/>
	 * - �̺�Ʈ�� condition tag�� Ȯ���Ѵ�.<br/>
	 * - �̺�Ʈ�� inhibit tag�� Ȯ���Ѵ�.<br/>
	 * - �̺�Ʈ�� ��ũ��Ʈ�� ���Ǵ� �±׸� Ȯ���Ѵ�.
	 * 
	 * @param existTagList Ȯ�� ��� �±� ����Ʈ.
	 * @param eventGroup �±� �� ���� �̺�Ʈ �׷�.
	 * @return eachTagResults EachTagEventDependResult ����Ʈ ����.
	 */
	private static List<EachTagEventDependResult> dataChangeEventAnalyze(List<String> existTagList, EventGroup eventGroup) {
		ArrayList<EachTagEventDependResult> eachTagResults = new ArrayList<EachTagEventDependResult>();
		
		Collection<IHMIEvent> events = eventGroup.getEvents();
		for(IHMIEvent event : events) {
			if(!(event instanceof TagDrivenEvent)) {
				// data change event �� �ƴ϶�� �α��Ѵ�.
				continue;
			}
			
			TagDrivenEvent drivenEvent = (TagDrivenEvent) event;
			TagValueCondition condition = drivenEvent.getCondition();
			
			// condition tag ���� ���� �˻�.
			if(existTagList.contains(condition.getConditionSource())) {
				// ���� �±� ���Ӽ� ��� ��ü ����.
				// ���� ��� ����Ʈ�� �߰�.
				EachTagEventDependResult dependResult = new EachTagEventDependResult();
				dependResult.setEventName(event.getName());
				dependResult.setEventType(event.getEventType());
				dependResult.setTagId(condition.getConditionSource());
				dependResult.setTagPosition(EachTagEventDependResult.POSITION_CONDITION);
				dependResult.setNodeName(drivenEvent.getNodeName());
				eachTagResults.add(dependResult);
			}
			
			// inhibit tag ���� ���� �˻�.
			if(!StringUtils.isEmpty(drivenEvent.getInhibitTagName(), false)
					&& existTagList.contains(drivenEvent.getInhibitTagName())) {
				// ���� �±� ���Ӽ� ��� ��ü ����.
				// ���� ��� ����Ʈ�� �߰�.
				EachTagEventDependResult dependResult = new EachTagEventDependResult();
				dependResult.setEventName(event.getName());
				dependResult.setEventType(event.getEventType());
				dependResult.setTagId(drivenEvent.getInhibitTagName());
				dependResult.setTagPosition(EachTagEventDependResult.POSITION_INHIBIT);
				dependResult.setNodeName(drivenEvent.getNodeName());
				eachTagResults.add(dependResult);
			}
			
			// ��ũ��Ʈ �˻�.
			List<String> scriptTagList = ScriptDictionaryAnalyzer.scriptTagGenerate(drivenEvent.getScriptCode());
			if(null == scriptTagList) {
				continue;
			}
			
			for(String scriptTag : scriptTagList) {
				if(existTagList.contains(scriptTag)) {
					// ���� �±� ���Ӽ� ��� ��ü ����.
					// ���� ��� ����Ʈ�� �߰�.
					EachTagEventDependResult dependResult = new EachTagEventDependResult();
					dependResult.setEventName(event.getName());
					dependResult.setEventType(event.getEventType());
					dependResult.setTagId(drivenEvent.getInhibitTagName());
					dependResult.setTagPosition(EachTagEventDependResult.POSITION_SCRIPT);
					dependResult.setNodeName(drivenEvent.getNodeName());
					eachTagResults.add(dependResult);
				}
			}
		}
		return eachTagResults;
	}
	/**
	 * 
	 * �溸 �̺�Ʈ �׷쿡 �ִ� ��� �̺�Ʈ�� �±׸� �м��Ѵ�.<br/>
	 * - �̺�Ʈ�� ������ {@link TagDrivenEvent}���� �˻��Ѵ�.<br/>
	 * - �̺�Ʈ�� condition tag�� Ȯ���Ѵ�.<br/>
	 * - �̺�Ʈ�� inhibit tag�� Ȯ���Ѵ�.<br/>
	 * - �̺�Ʈ�� ��ũ��Ʈ�� ���Ǵ� �±׸� Ȯ���Ѵ�.
	 * 
	 * @param existTagList Ȯ�� ��� �±� ����Ʈ.
	 * @param eventGroup �溸 �̺�Ʈ �׷�
	 * @return eachTagResults EachTagEventDependResult ����Ʈ ����.
	 */
	private static List<EachTagEventDependResult> alarmEventAnalyze(List<String> existTagList, EventGroup eventGroup) {
		ArrayList<EachTagEventDependResult> eachTagResults = new ArrayList<EachTagEventDependResult>();
		
		Collection<IHMIEvent> events = eventGroup.getEvents();
		for(IHMIEvent event : events) {
			if(!(event instanceof AlarmDrivenEvent)) {
				continue;
			}
			
			AlarmDrivenEvent drivenEvent = (AlarmDrivenEvent) event;
			AlarmStateCondition condition = drivenEvent.getCondition();
			
			// condition tag ���� ���� �˻�.
			if(existTagList.contains(condition.getConditionSource())) {
				// ���� �±� ���Ӽ� ��� ��ü ����.
				// ���� ��� ����Ʈ�� �߰�.
				EachTagEventDependResult dependResult = new EachTagEventDependResult();
				dependResult.setEventName(event.getName());
				dependResult.setEventType(event.getEventType());
				dependResult.setTagId(drivenEvent.getInhibitTagName());
				dependResult.setTagPosition(EachTagEventDependResult.POSITION_CONDITION);
				dependResult.setNodeName(drivenEvent.getNodeName());
				eachTagResults.add(dependResult);
			}
			
			// inhibit tag ���� ���� �˻�.
			if(!StringUtils.isEmpty(drivenEvent.getInhibitTagName(), false)
					&& existTagList.contains(drivenEvent.getInhibitTagName())) {
				// ���� �±� ���Ӽ� ��� ��ü ����.
				// ���� ��� ����Ʈ�� �߰�.
				EachTagEventDependResult dependResult = new EachTagEventDependResult();
				dependResult.setEventName(event.getName());
				dependResult.setEventType(event.getEventType());
				dependResult.setTagId(drivenEvent.getInhibitTagName());
				dependResult.setTagPosition(EachTagEventDependResult.POSITION_INHIBIT);
				dependResult.setNodeName(drivenEvent.getNodeName());
				eachTagResults.add(dependResult);
			}
			
			// ��ũ��Ʈ �˻�.
			List<String> scriptTagList = ScriptDictionaryAnalyzer.scriptTagGenerate(drivenEvent.getScriptCode());
			if(null == scriptTagList) {
				continue;
			}
			
			for(String scriptTag : scriptTagList) {
				if(existTagList.contains(scriptTag)) {
					// ���� �±� ���Ӽ� ��� ��ü ����.
					// ���� ��� ����Ʈ�� �߰�.
					EachTagEventDependResult dependResult = new EachTagEventDependResult();
					dependResult.setEventName(event.getName());
					dependResult.setEventType(event.getEventType());
					dependResult.setTagId(drivenEvent.getInhibitTagName());
					dependResult.setTagPosition(EachTagEventDependResult.POSITION_SCRIPT);
					dependResult.setNodeName(drivenEvent.getNodeName());
					eachTagResults.add(dependResult);
				}
			}
			
		}
		return eachTagResults;
	}
	
	/**
	 * 
	 * �ð� �̺�Ʈ �׷쿡 �ִ� ��� �̺�Ʈ�� �±׸� �м��Ѵ�.<br/>
	 * - �̺�Ʈ�� ������ {@link TagDrivenEvent}���� �˻��Ѵ�.<br/>
	 * - �̺�Ʈ�� inhibit tag�� Ȯ���Ѵ�.<br/>
	 * - �̺�Ʈ�� ��ũ��Ʈ�� ���Ǵ� �±׸� Ȯ���Ѵ�.
	 * 
	 * @param existTagList Ȯ�� ��� �±� ����Ʈ.
	 * @param eventGroup �ð� �̺�Ʈ �׷�.
	 * @return eachTagResults EachTagEventDependResult ����Ʈ ����.
	 */
	private static List<EachTagEventDependResult> timeEventAnalyze(List<String> existTagList, EventGroup eventGroup) {
		ArrayList<EachTagEventDependResult> eachTagResults = new ArrayList<EachTagEventDependResult>();
		
		Collection<IHMIEvent> events = eventGroup.getEvents();
		for(IHMIEvent event : events) {
			if(!(event instanceof TimeDrivenEvent)) {
				// time event �� �ƴ϶�� �α��Ѵ�.
				continue;
			}
			
			TimeDrivenEvent drivenEvent = (TimeDrivenEvent) event;
			// TimeEvent�� Condition�� ����.
			
			// inhibit tag ���� ���� �˻�.
			if(!StringUtils.isEmpty(drivenEvent.getInhibitTagName(), false)
					&& existTagList.contains(drivenEvent.getInhibitTagName())) {
				// ���� �±� ���Ӽ� ��� ��ü ����.
				// ���� ��� ����Ʈ�� �߰�.
				EachTagEventDependResult dependResult = new EachTagEventDependResult();
				dependResult.setEventName(event.getName());
				dependResult.setEventType(event.getEventType());
				dependResult.setTagId(drivenEvent.getInhibitTagName());
				dependResult.setTagPosition(EachTagEventDependResult.POSITION_INHIBIT);
				dependResult.setNodeName(drivenEvent.getNodeName());
				eachTagResults.add(dependResult);
			}
			
			// ��ũ��Ʈ �˻�.
			List<String> scriptTagList = ScriptDictionaryAnalyzer.scriptTagGenerate(drivenEvent.getScriptCode());
			if(null == scriptTagList) {
				continue;
			}
			
			for(String scriptTag : scriptTagList) {
				if(existTagList.contains(scriptTag)) {
					// ���� �±� ���Ӽ� ��� ��ü ����.
					// ���� ��� ����Ʈ�� �߰�.
					EachTagEventDependResult dependResult = new EachTagEventDependResult();
					dependResult.setEventName(event.getName());
					dependResult.setEventType(event.getEventType());
					dependResult.setTagId(drivenEvent.getInhibitTagName());
					dependResult.setTagPosition(EachTagEventDependResult.POSITION_SCRIPT);
					dependResult.setNodeName(drivenEvent.getNodeName());
					eachTagResults.add(dependResult);
				}
			}
		}
		return eachTagResults;
	}

}
