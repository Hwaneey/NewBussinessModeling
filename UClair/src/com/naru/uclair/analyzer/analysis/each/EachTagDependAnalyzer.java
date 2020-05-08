package com.naru.uclair.analyzer.analysis.each;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

import com.naru.uclair.project.Project;
import com.naru.uclair.project.TagDictionary;
import com.naru.uclair.tag.Tag;

/**
 *
 * <pre>
 * NAME   : com.naru.uclair.analyzer.analysis.each.EachTagDependAnalyzer.java
 * DESC   : 
 *
 * references : ���輭 NARU-XXX-XXX-XXX
 *
 * Copyright 2012 NARU Technology All rights reserved
 * <pre>
 *
 * @author US Laboratory naruteclab4
 * @since 2012. 7. 3.
 * @version 1.0
 *
 */
public class EachTagDependAnalyzer {
	
	private Project targetProject;
	
	private static EachTagDependAnalyzer eachTagDependAnalyzer;
	
	private PropertyChangeSupport eventSupport;
	
	public static final String PROPERTY_NAME = "each.tag.depend.anlyzer";
	
	/**
	 * singleton �� ���� ������.
	 */
	private EachTagDependAnalyzer() {
		eventSupport = new PropertyChangeSupport(this);
	}
	
	public static EachTagDependAnalyzer getInstance(Project project) {
		if(null == eachTagDependAnalyzer) {
			eachTagDependAnalyzer = new EachTagDependAnalyzer();
		}
		else {
			eachTagDependAnalyzer.clean();
		}
		eachTagDependAnalyzer.setProject(project);
		return eachTagDependAnalyzer;
	}
	
	/**
	 * 
	 * �м� ��� ������Ʈ ������ �����Ѵ�.<br/>
	 * 
	 * @param project ������Ʈ ����.
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
	
	public List<EachTagDependResult> analyze() {
		eventSupport.firePropertyChange(PROPERTY_NAME, "Analyze start", 0);
		LinkedList<EachTagDependResult> totalResultList = new LinkedList<EachTagDependResult>();

		// ������ �м��Ѵ�.
		if(null == targetProject) {
			// TODO ��� ������Ʈ ����. �м� ��� ���� �޽��� �α�.
			eventSupport.firePropertyChange(PROPERTY_NAME, "Analyze complete", 100);
			return totalResultList;
		}
		// 1. ��ü �±� ��� ����.
		// �˻�� �±� ���� generate
		eventSupport.firePropertyChange(PROPERTY_NAME, "Tag info generate...", 10);
		List<String> existTagList = tagDictionaryGenerate();
		if(existTagList.isEmpty()) {
			// TODO �˻� �±װ� ������ �α��ϰ� �м��� �����Ѵ�.
			eventSupport.firePropertyChange(PROPERTY_NAME, "Analyze complete", 100);
			return totalResultList;
		}
		
		// 2. �˻� ��� �� ����.
		TreeMap<String, EachTagDependResult> totalTempResultMap = new TreeMap<String, EachTagDependResult>();
		TagDictionary tagDictionary = targetProject.getTagDictionary();
		
		// 3. �̺�Ʈ ���� �˻�.
		eventSupport.firePropertyChange(PROPERTY_NAME, "Event dictionary analyze...", 20);
		List<EachTagEventDependResult> eventResultList = EventDictionaryAnalyzer.eventDictionaryAnalyze(existTagList, targetProject.getEventDictionary());
		for(EachTagEventDependResult dependResult : eventResultList) {
			// totalTempResultMap�� �ִ��� Ȯ��.
			// ������ ���� �����Ͽ� EachTagEventDependResult�� �߰�.
			EachTagDependResult tagDependResult = totalTempResultMap.get(dependResult.getTagId());
			if(null == tagDependResult) {
				tagDependResult = new EachTagDependResult();
				tagDependResult.setTag(tagDictionary.get(dependResult.getTagId()));
				totalTempResultMap.put(dependResult.getTagId(), tagDependResult);
			}
			tagDependResult.addEachTagEventDependResult(dependResult);
		}
		
		// 4. ������ ���� ȯ�� �˻�.
		eventSupport.firePropertyChange(PROPERTY_NAME, "Data collection configuration analyze...", 40);
		List<EachTagDataCollectionDependResult> dataCollectionResultList = DataCollectionConfigurationAnalyzer.dataCollectionAnalyzer(existTagList, targetProject.getDataCollectionConfiguration());
		for(EachTagDataCollectionDependResult dependResult : dataCollectionResultList) {
			// totalTempResultMap�� �ִ��� Ȯ��.
			// ������ ���� �����Ͽ� EachTagDataCollectionDependResult�� �߰�.
			EachTagDependResult tagDependResult = totalTempResultMap.get(dependResult.getTagId());
			if(null == tagDependResult) {
				tagDependResult = new EachTagDependResult();
				tagDependResult.setTag(tagDictionary.get(dependResult.getTagId()));
				totalTempResultMap.put(dependResult.getTagId(), tagDependResult);
			}
			tagDependResult.addEachTagDataCollectionDependList(dependResult);
		}
		
		// 5. ����� �Լ� Ȯ��. 
		eventSupport.firePropertyChange(PROPERTY_NAME, "Script dictionary analyze...", 60);
		List<EachTagScriptDependResult> scriptResultList = ScriptDictionaryAnalyzer.scriptDictionaryAnalyze(existTagList, targetProject.getScriptDictionary());
		for(EachTagScriptDependResult dependResult : scriptResultList) {
			// totalTempResultMap�� �ִ��� Ȯ��.
			// ������ ���� �����Ͽ� EachTagScriptDependResult�� �߰�.
			EachTagDependResult tagDependResult = totalTempResultMap.get(dependResult.getTagId());
			if(null == tagDependResult) {
				tagDependResult = new EachTagDependResult();
				tagDependResult.setTag(tagDictionary.get(dependResult.getTagId()));
				totalTempResultMap.put(dependResult.getTagId(), tagDependResult);
			}
			tagDependResult.addEachTagScriptDependResult(dependResult);
		}
		
		// 6. ȭ�� �±� Ȯ��.
		eventSupport.firePropertyChange(PROPERTY_NAME, "Window resource analyze...", 80);
		List<EachTagWindowDependResult> windowResultList = WindowAnalyzer.windowResourceAnalyze(existTagList, targetProject.getWindowResourcePath());
		for(EachTagWindowDependResult dependResult : windowResultList) {
			// totalTempResultMap�� �ִ��� Ȯ��.
			// ������ ���� �����Ͽ� EachTagWindowDependResult �߰�.
			EachTagDependResult tagDependResult = totalTempResultMap.get(dependResult.getTagId());
			if(null == tagDependResult) {
				tagDependResult = new EachTagDependResult();
				tagDependResult.setTag(tagDictionary.get(dependResult.getTagId()));
				totalTempResultMap.put(dependResult.getTagId(), tagDependResult);
			}
			tagDependResult.addEachTagWindowDependResult(dependResult);
		}
		
		if(!totalTempResultMap.isEmpty()) {
			totalResultList.addAll(totalTempResultMap.values());
		}
		
		eventSupport.firePropertyChange(PROPERTY_NAME, "Analyze complete", 100);
		return totalResultList;
	}
	
	private List<String> tagDictionaryGenerate() {
		TagDictionary tagDic = targetProject.getTagDictionary();
		Iterator<Tag> allTags = tagDic.getAllTags();
		
		LinkedList<String> existTagList = new LinkedList<String>();
		while (allTags.hasNext()) {
			Tag tag = allTags.next();
			existTagList.add(tag.getKey());
		}
		
		return existTagList;
	}

}
