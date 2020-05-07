package analyzer.analysis.dangling;

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

/************************************************
 * @date	: 2020. 5.07.
 * @책임자 : 지이삭
 * @설명  	: Dangling 태그 윈도우 분석 클래스.
 * @변경이력 	: 
 ************************************************/


public class WindowAnalyzer {

	public static List<DanglingTagResult> windowResourceAnalyze(List<String> existTagList,
			URI windowResourcePath) {
		// DanglingTagResultList
		ArrayList<DanglingTagResult> danglingTagResults = new ArrayList<DanglingTagResult>();
		
		// windows load.
		Map<String, HMIDrawing> drawingMap = loadFileWindow(windowResourcePath);
		Set<String> drawingKeySet = drawingMap.keySet();
		
		// 화면별 분석.
		HMIDrawing drawing;
		List<DanglingTagResult> windowResultList;
		for(String key : drawingKeySet) {
			// key 가 화면명이 된다.
			drawing = drawingMap.get(key);
			
			// 화면 스크립트 정보 분석.
			windowResultList = windowScriptPropertyAnalyze(existTagList, key, drawing.getScriptProperty());
			if(null != windowResultList
					&& !windowResultList.isEmpty()) {
				danglingTagResults.addAll(windowResultList);
			}
			
			// Figure별 Effect 분석.
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
			// TODO Figure 없음 로깅.
			return null;
		}
		
		if( figureList.size() == 0) {
			// TODO Figure 없음 로깅.
			return null;
		}
		
		// Tag 확인을 위한 effect 정보.
		HMIDrawingEffects drawingEffects = drawing.getDrawingEffects();
		if(null == drawingEffects) {
			// TODO 이펙트 널 로깅.
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
			// TODO 화면 스크립트 정보 없음 로깅.
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
