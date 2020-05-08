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
 * DESC   : dangling tag �м� Ŭ����.
 *
 * references : ���輭 NARU-XXX-XXX-XXX
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
	 * singleton �� ���� ������.
	 */
	private DanglingTagAnalyzer() {
		eventSupport = new PropertyChangeSupport(this);
	}

	/**
	 * 
	 * DanglingTagAnalyzer �ν��Ͻ� ������.<br/>
	 * - �м��� ������ �����Ѵ�.<br/>
	 * - ���� ������ �ִ� ��� ��� �Ұ��Ѵ�.
	 * 
	 * @param project �м� ��� ������Ʈ ����.
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
	
	/**
	 * 
	 * Broken Tag�� �м��Ѵ�.<br/>
	 * - �޼ҵ��� ó�� ���� ���.
	 *
	 */
	public List<DanglingTagResult> analyze() {
		eventSupport.firePropertyChange(PROPERTY_NAME, "Analyze start", 0);
		ArrayList<DanglingTagResult> totalResultList = new ArrayList<DanglingTagResult>();

		// ������ �м��Ѵ�.
		if(null == targetProject) {
			// TODO ��� ������Ʈ ����. �м� ��� ���� �޽��� �α�.
			eventSupport.firePropertyChange(PROPERTY_NAME, "Analyze complete", 100);
			return totalResultList;
		}
		// �˻� ��� ����Ʈ.
		
		// �˻�� �±� ���� generate
		eventSupport.firePropertyChange(PROPERTY_NAME, "Tag info generate...", 10);
		List<String> existTagList = tagDictionaryGenerate();
		if(existTagList.isEmpty()) {
			// TODO �˻� �±װ� ������ �α��ϰ� �м��� �����Ѵ�.
			return totalResultList;
		}
		
		// 1. �̺�Ʈ ������ �±� Ȯ��.
		eventSupport.firePropertyChange(PROPERTY_NAME, "Event dictionary analyze...", 20);
		List<DanglingTagResult> analyzeResultList = EventDictionaryAnalyzer.eventDictionaryAnalyze(existTagList, 
				targetProject.getEventDictionary());
		if(null != analyzeResultList
				&& !analyzeResultList.isEmpty()) {
			totalResultList.addAll(analyzeResultList);
		}
		
		// 2. ������ ���� ȯ�� �±� Ȯ��.
		eventSupport.firePropertyChange(PROPERTY_NAME, "Data collection configuration analyze...", 40);
		analyzeResultList = DataCollectionConfigurationAnalyzer.dataCollectionAnalyzer(existTagList, 
				targetProject.getDataCollectionConfiguration());
		if(null != analyzeResultList
				&& !analyzeResultList.isEmpty()) {
			totalResultList.addAll(analyzeResultList);
		}
		
		// 3. ����� �Լ� �±� Ȯ��.
		eventSupport.firePropertyChange(PROPERTY_NAME, "Script dictionary analyze...", 60);
		analyzeResultList = ScriptDictionaryAnalyzer.scriptDictionaryAnalyze(existTagList, 
				targetProject.getScriptDictionary());
		if(null != analyzeResultList
				&& !analyzeResultList.isEmpty()) {
			totalResultList.addAll(analyzeResultList);
		}
		
		// 4. ȭ���� �±� Ȯ��.
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
