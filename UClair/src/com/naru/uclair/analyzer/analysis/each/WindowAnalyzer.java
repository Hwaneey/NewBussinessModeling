package com.naru.uclair.analyzer.analysis.each;

import java.io.File;
import java.net.URI;
import java.util.HashMap;
import java.util.LinkedList;
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
import com.naru.uclair.draw.effect.EffectList;
import com.naru.uclair.draw.effect.TouchEffect;
import com.naru.uclair.draw.figure.HMIFigure;
import com.naru.uclair.draw.figure.HMIGroupFigure;
import com.naru.uclair.util.HmiVMOptions;

/**
 *
 * <pre>
 * NAME   : com.naru.uclair.analyzer.analysis.dangling.WindowAnalyzer.java
 * DESC   : 
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
public class WindowAnalyzer {

	public static List<EachTagWindowDependResult> windowResourceAnalyze(List<String> existTagList,
			URI windowResourcePath) {
		// DanglingTagResultList
		LinkedList<EachTagWindowDependResult> dependResultList = new LinkedList<EachTagWindowDependResult>();
		
		// windows load.
		Map<String, HMIDrawing> drawingMap = loadFileWindow(windowResourcePath);
		Set<String> drawingKeySet = drawingMap.keySet();
		
		// 화면별 분석.
		HMIDrawing drawing;
		List<EachTagWindowDependResult> windowResultList;
		for(String key : drawingKeySet) {
			// key 가 화면명이 된다.
			drawing = drawingMap.get(key);
			
			// 화면 스크립트 정보 분석.
			windowResultList = windowScriptPropertyAnalyze(existTagList, key, drawing.getScriptProperty());
			if(null != windowResultList
					&& !windowResultList.isEmpty()) {
				dependResultList.addAll(windowResultList);
			}
			
			// Figure별 Effect 분석.
			windowResultList = windowFigureEffectAnalyze(existTagList, key, drawing);
			if(null != windowResultList
					&& !windowResultList.isEmpty()) {
				dependResultList.addAll(windowResultList);
			}
		}
		
		return dependResultList;
	}
	
	private static List<EachTagWindowDependResult> windowFigureEffectAnalyze(List<String> existTagList, 
			String windowName, 
			HMIDrawing drawing) {
		LinkedList<EachTagWindowDependResult> dependResultList = new LinkedList<EachTagWindowDependResult>();
		
		// 1. 객체 아이디 리스트 생성.
		List<String> figureIdList = figureIdGenerate(drawing);
		
		if(figureIdList.isEmpty()) {
			// TODO 객체 없음 로깅.
			return null;
		}
		
		// Tag 확인을 위한 effect 정보.
		HMIDrawingEffects drawingEffects = drawing.getDrawingEffects();
		if(null == drawingEffects) {
			// TODO 이펙트 널 로깅.
			return null;
		}
		
		Effect effect;
		for(String figureId :  figureIdList) {
			effect = drawingEffects.get(figureId);
			
			if(null == effect) continue;
			
			if(!(effect instanceof EffectList)) continue;
			
			EffectList effectList = (EffectList) effect;
			if(effectList.isEmpty()) continue;
			
			List<EachTagWindowDependResult> effectListResultList = effectListAnalyze(effectList, existTagList, windowName);
			if(null != effectListResultList
					&& !effectListResultList.isEmpty()) {
				dependResultList.addAll(effectListResultList);
			}
		}
		
		return dependResultList;
	}
	
	private static List<EachTagWindowDependResult> effectListAnalyze(EffectList effectList, List<String> existTagList, String windowName) {
		LinkedList<EachTagWindowDependResult> dependResultList = new LinkedList<EachTagWindowDependResult>();
		Effect[] effects = effectList.getEffects();
		for(Effect effect : effects) {
			if(null == effect) continue;
			
			if(!(Effect.EMERGE_IDX == effect.getType()
					|| Effect.BLINK_IDX == effect.getType()
					|| Effect.MOVE_IDX == effect.getType()
					|| Effect.DRAG_IDX == effect.getType()
					|| Effect.FILL_IDX == effect.getType()
					|| Effect.COLORCHANGE_IDX == effect.getType()
					|| Effect.TAGDISPLAY_IDX == effect.getType()
					|| Effect.TOUCH_IDX == effect.getType())) continue;
			
			Set<String> linkTags = effect.getLinkTags();
			if(null != linkTags) {
				for(String tag : linkTags) {
					// 종속성 확인.
					if(existTagList.equals(tag)) {
						EachTagWindowDependResult dependResult = new EachTagWindowDependResult();
						dependResult.setTagId(tag);
						dependResult.setWindowName(windowName);
						dependResult.setType(EachTagWindowDependResult.TYPE_FIGURE);
						
						dependResultList.add(dependResult);
					}
				}
			}
			
			// Touch 효과인 경우 스크립트에 대해 추가로 처리한다.
			if(Effect.TOUCH_IDX == effect.getType()
					&& effect instanceof TouchEffect) {
				TouchEffect touchEffect = (TouchEffect) effect;
				String scriptCode = touchEffect.getScript();
				if(null == scriptCode) continue;
				
				List<String> scriptTags = ScriptDictionaryAnalyzer.scriptTagGenerate(scriptCode);
				if(null == scriptTags) continue; 
					
				for(String tagId : scriptTags) {
					// 종속성 확인.
					if(existTagList.equals(tagId)) {
						EachTagWindowDependResult dependResult = new EachTagWindowDependResult();
						dependResult.setTagId(tagId);
						dependResult.setWindowName(windowName);
						dependResult.setType(EachTagWindowDependResult.TYPE_FIGURE);
						
						dependResultList.add(dependResult);
					}
				}
			}
		}
		return dependResultList;
	}

	private static List<EachTagWindowDependResult> windowScriptPropertyAnalyze(List<String> existTagList, 
			String windowName, 
			HMIDrawingScriptProperty scriptProperty) {
		if(null == scriptProperty) {
			// TODO 화면 스크립트 정보 없음 로깅.
			return null;
		}
		LinkedList<EachTagWindowDependResult> dependResultList = new LinkedList<EachTagWindowDependResult>();
		
		String openScriptCode = scriptProperty.getOpenScript();
		if(null != openScriptCode
				&& !StringUtils.isEmpty(openScriptCode, false)) {
			List<String> openScriptTagList = ScriptDictionaryAnalyzer.scriptTagGenerate(openScriptCode);
			
			if(null != openScriptTagList) {
				for(String scriptTag : openScriptTagList) {
					// 종속성 확인.
					if(existTagList.contains(scriptTag)) {
						EachTagWindowDependResult dependResult = new EachTagWindowDependResult();
						dependResult.setTagId(scriptTag);
						dependResult.setWindowName(windowName);
						dependResult.setType(EachTagWindowDependResult.TYPE_WINDOW);
						
						dependResultList.add(dependResult);
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
					// 종속성 확인.
					if(existTagList.contains(scriptTag)) {
						EachTagWindowDependResult dependResult = new EachTagWindowDependResult();
						dependResult.setTagId(scriptTag);
						dependResult.setWindowName(windowName);
						dependResult.setType(EachTagWindowDependResult.TYPE_WINDOW);
						
						dependResultList.add(dependResult);
					}
				}
			}
		}
		
		return dependResultList;
	}
	
	private static List<String> figureIdGenerate(HMIDrawing drawing) {
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
