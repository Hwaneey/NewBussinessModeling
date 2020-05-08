/**
 * 
 */
package com.naru.uclair.analyzer.analysis.window;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.jhotdraw.draw.Figure;

import com.naru.uclair.analyzer.analysis.window.validator.EffectCompatibilityValidator;
import com.naru.uclair.analyzer.analysis.window.validator.FigureInfoValidator;
import com.naru.uclair.draw.effect.Effect;
import com.naru.uclair.draw.effect.EffectList;
import com.naru.uclair.draw.effect.GroupEffects;
import com.naru.uclair.draw.figure.HMIFigure;

/**
 * 객체 분석 정보를 담는 클래스.<br/>
 * - 각 객체의 속성 정보를 가진다.
 * 
 * @author ywpark
 *
 */
public class FigureAnalyzeInfo {
	/**
	 * 객체 아이디.
	 */
	private String figureId;
	/**
	 * 객체가 속한 화면명.
	 */
	private String windowName;
	/**
	 * 객체 유형.
	 */
	private int figureType;
	/**
	 * 효과 분석 정보 맵. 효과 유형을 키로 갖는다.
	 */
	private Map<Integer, EffectAnalyzeInfo> effectAnalyzeMap;
	
	/**
	 * FigureAnalyzeInfo 생성자.<br/>
	 * - 지정된 아이디로 Figure 아이디를 설정한다.
	 * 
	 * @param figureId 화면 객체 아이디.
	 */
	public FigureAnalyzeInfo(String figureId) {
		effectAnalyzeMap = new HashMap<Integer, EffectAnalyzeInfo>();
		setFigureId(figureId);
	}

	/**
	 * FigureAnalyzeInfo 에서 사용하는 모든 정보를 소거한다.<br/>
	 * - {@link #effectAnalyzeMap}의 정보 소거.
	 */
	public void clean() {
		Collection<EffectAnalyzeInfo> values = effectAnalyzeMap.values();
		for(EffectAnalyzeInfo value : values) {
			value.clean();
		}
		effectAnalyzeMap.clear();
	}
	
	/**
	 * 지정된 Figure 정보로 분석 정보를 설정한다.<br/>
	 * - 객체 기본 정보 설정 및 객체 오류(이미지, 영역, 스크립트등) 사항을 점검한다.<br/>
	 * - 객체 타입별 분석 정보를 생성한다.
	 * 
	 * @param figure 화면 객체 정보.
	 */
	public void setFigure(Figure figure) {
		// 1. 객체 타입 확인.
		setFigureType(((HMIFigure) figure).getFigureType());
		
		// 2. 객체 기본 정보 오류 사항 점검.
		// * 점검 결과는 리스트 혹은 코드로 저장한다.
		FigureInfoValidator.validate(figure);
		
		// TODO 3. 타입 별 분석 정보 생성.(태그, 스크립트)
		switch(figureType) {
		case HMIFigure.TYPE_LINE:
		case HMIFigure.TYPE_RECT:
		case HMIFigure.TYPE_ROUND_RECT:
		case HMIFigure.TYPE_ELLIPSE:
		case HMIFigure.TYPE_TRIANGLE:
		case HMIFigure.TYPE_DIAMOND:
		case HMIFigure.TYPE_ARC:
		case HMIFigure.TYPE_POLYLINE:
		case HMIFigure.TYPE_POLYGON:
		case HMIFigure.TYPE_TEXT:
		case HMIFigure.TYPE_IMAGE:
		case HMIFigure.TYPE_GROUP:
		case HMIFigure.TYPE_SYMBOL:
		case HMIFigure.TYPE_MULTI_TEXT:
		case HMIFigure.TYPE_BEANS:
			break;
		default:
			break;
		}
		
	}

	/**
	 * 지정된 Effect(효과) 정보로 분석 정보를 설정한다.<br/>
	 * - 객체에 대한 효과 정보 설정 및 효과 양립성 분석, 사용 태그 사항을 점검한다.<br/>
	 * - 효과별 분석 정보를 생성한다.
	 * 
	 * @param effect 화면 객체 효과 정보.
	 */
	public void setEffect(Effect effect) {
		// TODO 1. 효과 양립성 확인.
		// * 양립성 확인 결과를 List 혹은 Code 로 저장한다.
		// List<String> compatibilityResult = EffectCompatibilityValidator.validate(effect);
		EffectCompatibilityValidator.validate(effect);
		
		// TODO GroupEffect에 대한 처리 추가.
		if(effect instanceof GroupEffects) {
			return;
		}
		// 2. 모든 효과 유형을 확인하여 효과 분석 정보 생성.
		// 생성된 효과 분석 정보 Map 으로 관리.
		EffectList effectList = (EffectList) effect;
		Effect[] effects = effectList.getEffects();
		EffectAnalyzeInfo effectAnalyzeInfo = null;
		for(Effect ef : effects) {
			if(null == ef) continue;
			
			effectAnalyzeInfo = new EffectAnalyzeInfo(ef.getType());
			effectAnalyzeInfo.setFigureId(getFigureId());
			effectAnalyzeInfo.setWindowName(getWindowName());
			
			effectAnalyzeMap.put(ef.getType(), effectAnalyzeInfo);
		}
	}

	/**
	 * @return the figureId
	 */
	public String getFigureId() {
		return figureId;
	}

	/**
	 * @param figureId the figureId to set
	 */
	public void setFigureId(String figureId) {
		this.figureId = figureId;
	}

	/**
	 * @return the windowName
	 */
	public String getWindowName() {
		return windowName;
	}

	/**
	 * @param windowName the windowName to set
	 */
	public void setWindowName(String windowName) {
		this.windowName = windowName;
	}

	/**
	 * @return the figureType
	 */
	public int getFigureType() {
		return figureType;
	}

	/**
	 * @param figureType the figureType to set
	 */
	public void setFigureType(int figureType) {
		this.figureType = figureType;
	}
	
}
