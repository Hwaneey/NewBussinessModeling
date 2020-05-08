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
 * DESC   : figure tag linke 분석 클래스.
 *
 * references : 설계서 NARU-XXX-XXX-XXX
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
	 * singleton 을 위한 생성자.
	 */
	private ObjectTagLinkAnalyzer() {
		eventSupport = new PropertyChangeSupport(this);
	}
	
	/**
	 * 
	 * ObjectTagLinkAnalyzer 인스턴스 생성자.<br/>
	 * - 분석기 정보를 생성한다.<br/>
	 * - 이전 정보가 있는 경우 모두 소거한다.
	 * 
	 * @param project 분석 대상 프로젝트 정보.
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

	public List<ObjectTagLinkResult> analyze() {
		eventSupport.firePropertyChange(PROPERTY_NAME, "Analyze start", 0);
		if(null == targetProject) {
			// TODO 분석 프로젝트 없음 로깅.
			eventSupport.firePropertyChange(PROPERTY_NAME, "Analyze complete", 100);
			return null;
		}
		
		// 1. 화면 로딩.
		eventSupport.firePropertyChange(PROPERTY_NAME, "Window resource loading..", 20);
		Map<String, HMIDrawing> windowMap = loadFileWindow(targetProject.getWindowResourcePath());
		if(windowMap.isEmpty()) {
			// TODO 분석 화면 없음 로깅.
			eventSupport.firePropertyChange(PROPERTY_NAME, 0, 100);
			return null;
		}
		
		LinkedList<ObjectTagLinkResult> totalResultList = new LinkedList<ObjectTagLinkResult>();
		
		// 2. 화면별 객체 리스트 확인
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
			// TODO 화면 정보 없음 로깅.
			return null;
		}
		
		// 1. 객체 아이디 리스트 생성.
		List<String> figureIdList = figureIdGenerate(drawing);
		
		if(figureIdList.isEmpty()) {
			// TODO 객체 없음 로깅.
			return null;
		}
		
		// 결과 리스트.
		LinkedList<ObjectTagLinkResult> figureAnalyzeList = new LinkedList<ObjectTagLinkResult>();
		
		// 2. 객체에 대한 Effect 확인.
		HMIDrawingEffects drawingEffects = drawing.getDrawingEffects();
		WindowObjectTagLinkResult tagLinkResult;
		for(String figureId : figureIdList) {
			Effect effect = drawingEffects.get(figureId);
			
			if(null == effect) continue;
			
			if(!(effect instanceof EffectList)) continue;
			
			EffectList effectList = (EffectList) effect;
			if(effectList.isEmpty()) continue;
			
			// 위에서 이펙트가 없음을 검사하므로 아래와 같이 처리해도 상관없음.
			// 결과 표시용 객체 생성.
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
				// 컴포넌트를 처리하지 않는다.
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
	 * 분석 대상 프로젝트의 화면 리소스 폴더의 모든 화면을 로드하여 반환한다.<br/>
	 * - 화면 로드 실패시 실패 로그를 남기고 계속 로드한다.<br/>
	 * 
	 * @return drawingMap 로딩된 화면 맵.
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
