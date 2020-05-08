package com.naru.uclair.analyzer.analysis.linked;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.net.URI;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jhotdraw.draw.AttributeKeys;
import org.jhotdraw.draw.Figure;

import com.naru.uclair.draw.HMIDrawing;
import com.naru.uclair.draw.HMIDrawingEffects;
import com.naru.uclair.draw.common.HMIDrawingFileFilter;
import com.naru.uclair.draw.effect.Effect;
import com.naru.uclair.draw.effect.EffectList;
import com.naru.uclair.draw.figure.HMIFigure;
import com.naru.uclair.draw.figure.HMIGroupFigure;
import com.naru.uclair.project.Project;
import com.naru.uclair.util.HmiVMOptions;

/**
 *
 * <pre>
 * NAME   : com.naru.uclair.analyzer.analysis.linked.ObjectTagLinkAnalyzer.java
 * DESC   : figure tag linke �м� Ŭ����.
 *
 * references : ���輭 NARU-XXX-XXX-XXX
 *
 * Copyright 2012 NARU Technology All rights reserved
 * <pre>
 *
 * @author US Laboratory naruteclab4
 * @since 2012. 6. 27.
 * @version 1.0
 *
 */
public class ObjectTagLinkAnalyzer {
	
	private Project targetProject;
	
	private static ObjectTagLinkAnalyzer objectTagLinkAnalyzer;
	
	private PropertyChangeSupport eventSupport;
	
	public static final String PROPERTY_NAME = "object.tag.link.anlyzer";
	
	/**
	 * singleton �� ���� ������.
	 */
	private ObjectTagLinkAnalyzer() {
		eventSupport = new PropertyChangeSupport(this);
	}
	
	/**
	 * 
	 * ObjectTagLinkAnalyzer �ν��Ͻ� ������.<br/>
	 * - �м��� ������ �����Ѵ�.<br/>
	 * - ���� ������ �ִ� ��� ��� �Ұ��Ѵ�.
	 * 
	 * @param project �м� ��� ������Ʈ ����.
	 * @return
	 */
	public static ObjectTagLinkAnalyzer getInstance(Project project) {
		if(null == objectTagLinkAnalyzer) {
			objectTagLinkAnalyzer = new ObjectTagLinkAnalyzer();
		}
		else {
			objectTagLinkAnalyzer.clean();
		}
		objectTagLinkAnalyzer.setProject(project);
		return objectTagLinkAnalyzer;
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

	public List<ObjectTagLinkResult> analyze() {
		eventSupport.firePropertyChange(PROPERTY_NAME, "Analyze start", 0);
		if(null == targetProject) {
			// TODO �м� ������Ʈ ���� �α�.
			eventSupport.firePropertyChange(PROPERTY_NAME, "Analyze complete", 100);
			return null;
		}
		
		// 1. ȭ�� �ε�.
		eventSupport.firePropertyChange(PROPERTY_NAME, "Window resource loading..", 20);
		Map<String, HMIDrawing> windowMap = loadFileWindow(targetProject.getWindowResourcePath());
		if(windowMap.isEmpty()) {
			// TODO �м� ȭ�� ���� �α�.
			eventSupport.firePropertyChange(PROPERTY_NAME, 0, 100);
			return null;
		}
		
		LinkedList<ObjectTagLinkResult> totalResultList = new LinkedList<ObjectTagLinkResult>();
		
		// 2. ȭ�麰 ��ü ����Ʈ Ȯ��
		Set<String> windowNameSet = windowMap.keySet();
		int totalSize = windowNameSet.size();
		int tempProgress = 20;
		List<ObjectTagLinkResult> drawingResultList;
		for(String windowName : windowNameSet) {
			drawingResultList = drawingAnalyze(windowName, windowMap.get(windowName));
			tempProgress += (80 / totalSize);
			eventSupport.firePropertyChange(PROPERTY_NAME, "Analyzing.. - " + windowName, tempProgress);
			if(null != drawingResultList
					&& !drawingResultList.isEmpty()) {
				totalResultList.addAll(drawingResultList);
			}
		}
		eventSupport.firePropertyChange(PROPERTY_NAME, "Analyze complete", 100);
		return totalResultList;
	}
	
	private List<ObjectTagLinkResult> drawingAnalyze(String windowName, HMIDrawing drawing) {
		if(null == drawing) {
			// TODO ȭ�� ���� ���� �α�.
			return null;
		}
		
		// 1. ��ü ���̵� ����Ʈ ����.
		List<String> figureIdList = figureIdGenerate(drawing);
		
		if(figureIdList.isEmpty()) {
			// TODO ��ü ���� �α�.
			return null;
		}
		
		// ��� ����Ʈ.
		LinkedList<ObjectTagLinkResult> figureAnalyzeList = new LinkedList<ObjectTagLinkResult>();
		
		// 2. ��ü�� ���� Effect Ȯ��.
		HMIDrawingEffects drawingEffects = drawing.getDrawingEffects();
		WindowObjectTagLinkResult tagLinkResult;
		for(String figureId : figureIdList) {
			Effect effect = drawingEffects.get(figureId);
			
			if(null == effect) continue;
			
			if(!(effect instanceof EffectList)) continue;
			
			EffectList effectList = (EffectList) effect;
			if(effectList.isEmpty()) continue;
			
			// ������ ����Ʈ�� ������ �˻��ϹǷ� �Ʒ��� ���� ó���ص� �������.
			// ��� ǥ�ÿ� ��ü ����.
			tagLinkResult = new WindowObjectTagLinkResult();
			tagLinkResult.setWindowName(windowName);
			tagLinkResult.setFigureId(figureId);
			tagLinkResult.setEffectList(effectList);
			if(!tagLinkResult.isEmpty()) {
				figureAnalyzeList.add(tagLinkResult);
			}
		}
		
		return figureAnalyzeList;
	}
	
	private List<String> figureIdGenerate(HMIDrawing drawing) {
		LinkedList<String> figureIdList = new LinkedList<String>();
		
		List<Figure> figureList = drawing.getChildren();
		for(Figure figure : figureList) {
			if(!(figure instanceof HMIFigure)) continue;
			
			HMIFigure hmiFigure = (HMIFigure) figure;
			
			int figureType = hmiFigure.getFigureType();
			if(HMIFigure.TYPE_BEANS == figureType) {
				// ������Ʈ�� ó������ �ʴ´�.
				continue;
			}
			else if(HMIFigure.TYPE_GROUP == figureType
					&& hmiFigure instanceof HMIGroupFigure) {
				HMIGroupFigure groupFigure = (HMIGroupFigure) hmiFigure;
				figureIdList.addAll(drawing.getChildrenIdList(groupFigure));
			}
			else {
				figureIdList.add(AttributeKeys.ID.get(figure));
			}
		}
		return figureIdList;
	}
	
	/**
	 * �м� ��� ������Ʈ�� ȭ�� ���ҽ� ������ ��� ȭ���� �ε��Ͽ� ��ȯ�Ѵ�.<br/>
	 * - ȭ�� �ε� ���н� ���� �α׸� ����� ��� �ε��Ѵ�.<br/>
	 * 
	 * @return drawingMap �ε��� ȭ�� ��.
	 */
	private Map<String, HMIDrawing> loadFileWindow(URI windowResourcePath) {
		Map<String, HMIDrawing> drawingMap = new HashMap<String, HMIDrawing>();
		
		File windowDir = new File(windowResourcePath);
		File[] windowFiles = windowDir.listFiles(new HMIDrawingFileFilter());

		HMIDrawing drawing = null;
		for (File wf : windowFiles) {
			drawing = HMIDrawing.load(wf);

			if (null == drawing) {
				System.out.println("[ " + wf.getName() + " ] window file load fail.");
			} else {
				drawingMap.put(wf.getName(), drawing);
				if(HmiVMOptions.isDebug()) {
					System.out.println("[ " + wf.getName() + " ] window file load success.");
				}
			}
		}
		return drawingMap;
	}
	
}
