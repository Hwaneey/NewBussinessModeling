package analyzer.analysis.each;

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

/************************************************
 * @date	: 2020. 5.07.
 * @책임자 : 이승환
 * @설명  	: 이벤트 사전에 있는 모든 이벤트의 태그 분석 클래스.
 * @변경이력 	: 
 ************************************************/


public class EventDictionaryAnalyzer {
	
	
	/**
	 * 
	 * 이벤트 사전에 있는 모든 이벤트의 태그를 분석한다.<br/>
	 * - 각 이벤트 리스트의 이벤트 그룹별로 분석하여 반환한다.
	 * 
	 * @param existTagList 확인 대상 태그 리스트.
	 * @param eventDictionary 이벤트 사전
	 * @return eachTagResults EachTagEventDependResult 리스트 정보.
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
	 * 태그 값 변경 이벤트 그룹에 있는 모든 이벤트의 태그를 분석한다.<br/>
	 * - 이벤트의 유형이 {@link TagDrivenEvent}인지 검사한다.<br/>
	 * - 이벤트의 condition tag를 확인한다.<br/>
	 * - 이벤트의 inhibit tag를 확인한다.<br/>
	 * - 이벤트의 스크립트에 사용되는 태그를 확인한다.
	 * 
	 * @param existTagList 확인 대상 태그 리스트.
	 * @param eventGroup 태그 값 변경 이벤트 그룹.
	 * @return eachTagResults EachTagEventDependResult 리스트 정보.
	 */
	private static List<EachTagEventDependResult> dataChangeEventAnalyze(List<String> existTagList, EventGroup eventGroup) {
		ArrayList<EachTagEventDependResult> eachTagResults = new ArrayList<EachTagEventDependResult>();
		
		Collection<IHMIEvent> events = eventGroup.getEvents();
		for(IHMIEvent event : events) {
			if(!(event instanceof TagDrivenEvent)) {
				// data change event 가 아니라고 로깅한다.
				continue;
			}
			
			TagDrivenEvent drivenEvent = (TagDrivenEvent) event;
			TagValueCondition condition = drivenEvent.getCondition();
			
			// condition tag 존재 유무 검사.
			if(existTagList.contains(condition.getConditionSource())) {
				// 개별 태그 종속성 결과 객체 생성.
				// 생성 결과 리스트에 추가.
				EachTagEventDependResult dependResult = new EachTagEventDependResult();
				dependResult.setEventName(event.getName());
				dependResult.setEventType(event.getEventType());
				dependResult.setTagId(condition.getConditionSource());
				dependResult.setTagPosition(EachTagEventDependResult.POSITION_CONDITION);
				dependResult.setNodeName(drivenEvent.getNodeName());
				eachTagResults.add(dependResult);
			}
			
			// inhibit tag 존재 유무 검사.
			if(!StringUtils.isEmpty(drivenEvent.getInhibitTagName(), false)
					&& existTagList.contains(drivenEvent.getInhibitTagName())) {
				// 개별 태그 종속성 결과 객체 생성.
				// 생성 결과 리스트에 추가.
				EachTagEventDependResult dependResult = new EachTagEventDependResult();
				dependResult.setEventName(event.getName());
				dependResult.setEventType(event.getEventType());
				dependResult.setTagId(drivenEvent.getInhibitTagName());
				dependResult.setTagPosition(EachTagEventDependResult.POSITION_INHIBIT);
				dependResult.setNodeName(drivenEvent.getNodeName());
				eachTagResults.add(dependResult);
			}
			
			// 스크립트 검사.
			List<String> scriptTagList = ScriptDictionaryAnalyzer.scriptTagGenerate(drivenEvent.getScriptCode());
			if(null == scriptTagList) {
				continue;
			}
			
			for(String scriptTag : scriptTagList) {
				if(existTagList.contains(scriptTag)) {
					// 개별 태그 종속성 결과 객체 생성.
					// 생성 결과 리스트에 추가.
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
	 * 경보 이벤트 그룹에 있는 모든 이벤트의 태그를 분석한다.<br/>
	 * - 이벤트의 유형이 {@link TagDrivenEvent}인지 검사한다.<br/>
	 * - 이벤트의 condition tag를 확인한다.<br/>
	 * - 이벤트의 inhibit tag를 확인한다.<br/>
	 * - 이벤트의 스크립트에 사용되는 태그를 확인한다.
	 * 
	 * @param existTagList 확인 대상 태그 리스트.
	 * @param eventGroup 경보 이벤트 그룹
	 * @return eachTagResults EachTagEventDependResult 리스트 정보.
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
			
			// condition tag 존재 유무 검사.
			if(existTagList.contains(condition.getConditionSource())) {
				// 개별 태그 종속성 결과 객체 생성.
				// 생성 결과 리스트에 추가.
				EachTagEventDependResult dependResult = new EachTagEventDependResult();
				dependResult.setEventName(event.getName());
				dependResult.setEventType(event.getEventType());
				dependResult.setTagId(drivenEvent.getInhibitTagName());
				dependResult.setTagPosition(EachTagEventDependResult.POSITION_CONDITION);
				dependResult.setNodeName(drivenEvent.getNodeName());
				eachTagResults.add(dependResult);
			}
			
			// inhibit tag 존재 유무 검사.
			if(!StringUtils.isEmpty(drivenEvent.getInhibitTagName(), false)
					&& existTagList.contains(drivenEvent.getInhibitTagName())) {
				// 개별 태그 종속성 결과 객체 생성.
				// 생성 결과 리스트에 추가.
				EachTagEventDependResult dependResult = new EachTagEventDependResult();
				dependResult.setEventName(event.getName());
				dependResult.setEventType(event.getEventType());
				dependResult.setTagId(drivenEvent.getInhibitTagName());
				dependResult.setTagPosition(EachTagEventDependResult.POSITION_INHIBIT);
				dependResult.setNodeName(drivenEvent.getNodeName());
				eachTagResults.add(dependResult);
			}
			
			// 스크립트 검사.
			List<String> scriptTagList = ScriptDictionaryAnalyzer.scriptTagGenerate(drivenEvent.getScriptCode());
			if(null == scriptTagList) {
				continue;
			}
			
			for(String scriptTag : scriptTagList) {
				if(existTagList.contains(scriptTag)) {
					// 개별 태그 종속성 결과 객체 생성.
					// 생성 결과 리스트에 추가.
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
	 * 시간 이벤트 그룹에 있는 모든 이벤트의 태그를 분석한다.<br/>
	 * - 이벤트의 유형이 {@link TagDrivenEvent}인지 검사한다.<br/>
	 * - 이벤트의 inhibit tag를 확인한다.<br/>
	 * - 이벤트의 스크립트에 사용되는 태그를 확인한다.
	 * 
	 * @param existTagList 확인 대상 태그 리스트.
	 * @param eventGroup 시간 이벤트 그룹.
	 * @return eachTagResults EachTagEventDependResult 리스트 정보.
	 */
	private static List<EachTagEventDependResult> timeEventAnalyze(List<String> existTagList, EventGroup eventGroup) {
		ArrayList<EachTagEventDependResult> eachTagResults = new ArrayList<EachTagEventDependResult>();
		
		Collection<IHMIEvent> events = eventGroup.getEvents();
		for(IHMIEvent event : events) {
			if(!(event instanceof TimeDrivenEvent)) {
				// time event 가 아니라고 로깅한다.
				continue;
			}
			
			TimeDrivenEvent drivenEvent = (TimeDrivenEvent) event;
			// TimeEvent는 Condition이 없다.
			
			// inhibit tag 존재 유무 검사.
			if(!StringUtils.isEmpty(drivenEvent.getInhibitTagName(), false)
					&& existTagList.contains(drivenEvent.getInhibitTagName())) {
				// 개별 태그 종속성 결과 객체 생성.
				// 생성 결과 리스트에 추가.
				EachTagEventDependResult dependResult = new EachTagEventDependResult();
				dependResult.setEventName(event.getName());
				dependResult.setEventType(event.getEventType());
				dependResult.setTagId(drivenEvent.getInhibitTagName());
				dependResult.setTagPosition(EachTagEventDependResult.POSITION_INHIBIT);
				dependResult.setNodeName(drivenEvent.getNodeName());
				eachTagResults.add(dependResult);
			}
			
			// 스크립트 검사.
			List<String> scriptTagList = ScriptDictionaryAnalyzer.scriptTagGenerate(drivenEvent.getScriptCode());
			if(null == scriptTagList) {
				continue;
			}
			
			for(String scriptTag : scriptTagList) {
				if(existTagList.contains(scriptTag)) {
					// 개별 태그 종속성 결과 객체 생성.
					// 생성 결과 리스트에 추가.
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
