package com.naru.uclair.analyzer.analysis.dangling;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jhotdraw.draw.AttributeKeys;
import org.jhotdraw.draw.Figure;

import com.naru.common.util.StringUtils;
import com.naru.uclair.draw.HMIDrawing;
import com.naru.uclair.draw.HMIDrawingEffects;
import com.naru.uclair.draw.HMIDrawingScriptProperty;
import com.naru.uclair.draw.common.HMIDrawingFileFilter;
import com.naru.uclair.draw.effect.Effect;
import com.naru.uclair.util.HmiVMOptions;

/**
 *
 * <pre>
 * NAME   : com.naru.uclair.analyzer.analysis.dangling.WindowAnalyzer.java
 * DESC   : 
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
public class WindowAnalyzer {

	public static List<DanglingTagResult> windowResourceAnalyze(List<String> existTagList,
			URI windowResourcePath) {
		// DanglingTagResultList
		ArrayList<DanglingTagResult> danglingTagResults = new ArrayList<DanglingTagResult>();
		
		// windows load.
		Map<String, HMIDrawing> drawingMap = loadFileWindow(windowResourcePath);
		Set<String> drawingKeySet = drawingMap.keySet();
		
		// ȭ�麰 �м�.
		HMIDrawing drawing;
		List<DanglingTagResult> windowResultList;
		for(String key : drawingKeySet) {
			// key �� ȭ����� �ȴ�.
			drawing = drawingMap.get(key);
			
			// ȭ�� ��ũ��Ʈ ���� �м�.
			windowResultList = windowScriptPropertyAnalyze(existTagList, key, drawing.getScriptProperty());
			if(null != windowResultList
					&& !windowResultList.isEmpty()) {
				danglingTagResults.addAll(windowResultList);
			}
			
			// Figure�� Effect �м�.
			windowResultList = windowFigureEffectAnalyze(existTagList, key, drawing);
			if(null != windowResultList
					&& !windowResultList.isEmpty()) {
				danglingTagResults.addAll(windowResultList);
			}
		}
		
		return danglingTagResults;
	}
	
	private static List<DanglingTagResult> windowFigureEffectAnalyze(List<String> existTagList, 
			String windowName, 
			HMIDrawing drawing) {
		ArrayList<DanglingTagResult> danglingTagResults = new ArrayList<DanglingTagResult>();
		
		List<Figure> figureList = drawing.getChildren();
		if(null ==  figureList) {
			// TODO Figure ���� �α�.
			return null;
		}
		
		if( figureList.size() == 0) {
			// TODO Figure ���� �α�.
			return null;
		}
		
		// Tag Ȯ���� ���� effect ����.
		HMIDrawingEffects drawingEffects = drawing.getDrawingEffects();
		if(null == drawingEffects) {
			// TODO ����Ʈ �� �α�.
			return null;
		}
		
		Effect effect;
		for(Figure figure :  figureList) {
			effect = drawingEffects.get(AttributeKeys.ID.get(figure));
			
			if(null == effect) {
				continue;
			}
			
			Set<String> linkTags = effect.getLinkTags();
			for(String tag : linkTags) {
				if(!existTagList.equals(tag)) {
					FigureDanglingTagResult danglingTagResult = new FigureDanglingTagResult();
					danglingTagResult.setDanglingTagName(tag);
					danglingTagResult.setWindowName(windowName);
					danglingTagResult.setFigureName(AttributeKeys.ID.get(figure));
					
					danglingTagResults.add(danglingTagResult);
				}
			}
		}
		
		return danglingTagResults;
	}

	private static List<DanglingTagResult> windowScriptPropertyAnalyze(List<String> existTagList, 
			String windowName, 
			HMIDrawingScriptProperty scriptProperty) {
		if(null == scriptProperty) {
			// TODO ȭ�� ��ũ��Ʈ ���� ���� �α�.
			return null;
		}
		ArrayList<DanglingTagResult> danglingTagResults = new ArrayList<DanglingTagResult>();
		
		String openScriptCode = scriptProperty.getOpenScript();
		if(null != openScriptCode
				&& !StringUtils.isEmpty(openScriptCode, false)) {
			List<String> openScriptTagList = ScriptDictionaryAnalyzer.scriptTagGenerate(openScriptCode);
			
			if(null != openScriptTagList) {
				for(String scriptTag : openScriptTagList) {
					if(!existTagList.contains(scriptTag)) {
						WindowDanglingTagResult danglingTagResult = new WindowDanglingTagResult();
						danglingTagResult.setDanglingTagName(scriptTag);
						danglingTagResult.setWindowName(windowName);
						danglingTagResult.setScriptType(WindowDanglingTagResult.TYPE_OPEN);
						
						danglingTagResults.add(danglingTagResult);
					}
				}
			}
		}
		
		String closeScriptCode = scriptProperty.getCloseScript();
		if(null != closeScriptCode
				&& !StringUtils.isEmpty(closeScriptCode, false)) {
			List<String> closeScriptTagList = ScriptDictionaryAnalyzer.scriptTagGenerate(closeScriptCode);
			
			if(null != closeScriptTagList) {
				for(String scriptTag : closeScriptTagList) {
					if(!existTagList.contains(scriptTag)) {
						WindowDanglingTagResult danglingTagResult = new WindowDanglingTagResult();
						danglingTagResult.setDanglingTagName(scriptTag);
						danglingTagResult.setWindowName(windowName);
						danglingTagResult.setScriptType(WindowDanglingTagResult.TYPE_CLOSE);
						
						danglingTagResults.add(danglingTagResult);
					}
				}
			}
		}
		
		return danglingTagResults;
	}
	
	/**
	 * �м� ��� ������Ʈ�� ȭ�� ���ҽ� ������ ��� ȭ���� �ε��Ͽ� ��ȯ�Ѵ�.<br/>
	 * - ȭ�� �ε� ���н� ���� �α׸� ����� ��� �ε��Ѵ�.<br/>
	 * 
	 * @return drawingMap �ε��� ȭ�� ��.
	 */
	private static Map<String, HMIDrawing> loadFileWindow(URI windowResourcePath) {
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
