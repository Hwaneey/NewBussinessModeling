package com.naru.uclair.analyzer.analysis.dangling;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.naru.uclair.project.Project;
import com.naru.uclair.project.TagDictionary;
import com.naru.uclair.tag.Tag;

/**
 *
 * <pre>
 * NAME   : com.naru.uclair.analyzer.analysis.dangling.DanglingTagAnalyzer.java
 * DESC   : dangling tag 분석 클래스.
 *
 * references : 설계서 NARU-XXX-XXX-XXX
 *
 * Copyright 2012 NARU Technology All rights reserved
 * <pre>
 *
 * @author US Laboratory naruteclab4
 * @since 2012. 6. 25.
 * @version 1.0
 *
 */
public class DanglingTagAnalyzer {
	
	private static DanglingTagAnalyzer danglingTagAnalyzer;
	
	private Project targetProject;
	
	private PropertyChangeSupport eventSupport;
	
	public static final String PROPERTY_NAME = "dangling.tag.anlyzer";
	
	/**
	 * singleton 을 위한 생성자.
	 */
	private DanglingTagAnalyzer() {
		eventSupport = new PropertyChangeSupport(this);
	}

	/**
	 * 
	 * DanglingTagAnalyzer 인스턴스 생성자.<br/>
	 * - 분석기 정보를 생성한다.<br/>
	 * - 이전 정보가 있는 경우 모두 소거한다.
	 * 
	 * @param project 분석 대상 프로젝트 정보.
	 * @return
	 */
	public static DanglingTagAnalyzer getInstance(Project project) {
		if(null == danglingTagAnalyzer) {
			danglingTagAnalyzer = new DanglingTagAnalyzer();
		}
		else {
			danglingTagAnalyzer.clean();
		}
		danglingTagAnalyzer.setProject(project);
		return danglingTagAnalyzer;
	}
	
	/**
	 * 
	 * 분석 대상 프로젝트 정보를 설정한다.<br/>
	 * 
	 * @param project 프로젝트 정보.
	 */
	private void setProject(Project project) {
		targetProject = project;
	}
	
	private void clean() {
		PropertyChangeListener[] listeners = eventSupport.getPropertyChangeListeners();
		for(PropertyChangeListener l : listeners) {
			eventSupport.removePropertyChangeListener(l);
		}
	}
	
	public void addPropertyChangeListeners(PropertyChangeListener listener) {
		eventSupport.addPropertyChangeListener(listener);
	}
	
	/**
	 * 
	 * Broken Tag를 분석한다.<br/>
	 * - 메소드의 처리 절차 기술.
	 *
	 */
	public List<DanglingTagResult> analyze() {
		eventSupport.firePropertyChange(PROPERTY_NAME, "Analyze start", 0);
		ArrayList<DanglingTagResult> totalResultList = new ArrayList<DanglingTagResult>();

		// 정보를 분석한다.
		if(null == targetProject) {
			// TODO 대상 프로젝트 없음. 분석 대상 없음 메시지 로깅.
			eventSupport.firePropertyChange(PROPERTY_NAME, "Analyze complete", 100);
			return totalResultList;
		}
		// 검사 결과 리스트.
		
		// 검사용 태그 정보 generate
		eventSupport.firePropertyChange(PROPERTY_NAME, "Tag info generate...", 10);
		List<String> existTagList = tagDictionaryGenerate();
		if(existTagList.isEmpty()) {
			// TODO 검사 태그가 없음을 로깅하고 분석을 종료한다.
			return totalResultList;
		}
		
		// 1. 이벤트 사전의 태그 확인.
		eventSupport.firePropertyChange(PROPERTY_NAME, "Event dictionary analyze...", 20);
		List<DanglingTagResult> analyzeResultList = EventDictionaryAnalyzer.eventDictionaryAnalyze(existTagList, 
				targetProject.getEventDictionary());
		if(null != analyzeResultList
				&& !analyzeResultList.isEmpty()) {
			totalResultList.addAll(analyzeResultList);
		}
		
		// 2. 데이터 수집 환경 태그 확인.
		eventSupport.firePropertyChange(PROPERTY_NAME, "Data collection configuration analyze...", 40);
		analyzeResultList = DataCollectionConfigurationAnalyzer.dataCollectionAnalyzer(existTagList, 
				targetProject.getDataCollectionConfiguration());
		if(null != analyzeResultList
				&& !analyzeResultList.isEmpty()) {
			totalResultList.addAll(analyzeResultList);
		}
		
		// 3. 사용자 함수 태그 확인.
		eventSupport.firePropertyChange(PROPERTY_NAME, "Script dictionary analyze...", 60);
		analyzeResultList = ScriptDictionaryAnalyzer.scriptDictionaryAnalyze(existTagList, 
				targetProject.getScriptDictionary());
		if(null != analyzeResultList
				&& !analyzeResultList.isEmpty()) {
			totalResultList.addAll(analyzeResultList);
		}
		
		// 4. 화면의 태그 확인.
		eventSupport.firePropertyChange(PROPERTY_NAME, "Window resource analyze...", 80);
		analyzeResultList = WindowAnalyzer.windowResourceAnalyze(existTagList, targetProject.getWindowResourcePath());
		if(null != analyzeResultList
				&& !analyzeResultList.isEmpty()) {
			totalResultList.addAll(analyzeResultList);
		}
		eventSupport.firePropertyChange(PROPERTY_NAME, "Analyze complete", 100);
		return totalResultList;
	}
	
	private List<String> tagDictionaryGenerate() {
		TagDictionary tagDic = targetProject.getTagDictionary();
		Iterator<Tag> allTags = tagDic.getAllTags();
		
		ArrayList<String> existTagList = new ArrayList<String>();
		while (allTags.hasNext()) {
			Tag tag = allTags.next();
			existTagList.add(tag.getKey());
		}
		
		return existTagList;
	}
	
}
