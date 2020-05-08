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
 * ��ü �м� ������ ��� Ŭ����.<br/>
 * - �� ��ü�� �Ӽ� ������ ������.
 * 
 * @author ywpark
 *
 */
public class FigureAnalyzeInfo {
	/**
	 * ��ü ���̵�.
	 */
	private String figureId;
	/**
	 * ��ü�� ���� ȭ���.
	 */
	private String windowName;
	/**
	 * ��ü ����.
	 */
	private int figureType;
	/**
	 * ȿ�� �м� ���� ��. ȿ�� ������ Ű�� ���´�.
	 */
	private Map<Integer, EffectAnalyzeInfo> effectAnalyzeMap;
	
	/**
	 * FigureAnalyzeInfo ������.<br/>
	 * - ������ ���̵�� Figure ���̵� �����Ѵ�.
	 * 
	 * @param figureId ȭ�� ��ü ���̵�.
	 */
	public FigureAnalyzeInfo(String figureId) {
		effectAnalyzeMap = new HashMap<Integer, EffectAnalyzeInfo>();
		setFigureId(figureId);
	}

	/**
	 * FigureAnalyzeInfo ���� ����ϴ� ��� ������ �Ұ��Ѵ�.<br/>
	 * - {@link #effectAnalyzeMap}�� ���� �Ұ�.
	 */
	public void clean() {
		Collection<EffectAnalyzeInfo> values = effectAnalyzeMap.values();
		for(EffectAnalyzeInfo value : values) {
			value.clean();
		}
		effectAnalyzeMap.clear();
	}
	
	/**
	 * ������ Figure ������ �м� ������ �����Ѵ�.<br/>
	 * - ��ü �⺻ ���� ���� �� ��ü ����(�̹���, ����, ��ũ��Ʈ��) ������ �����Ѵ�.<br/>
	 * - ��ü Ÿ�Ժ� �м� ������ �����Ѵ�.
	 * 
	 * @param figure ȭ�� ��ü ����.
	 */
	public void setFigure(Figure figure) {
		// 1. ��ü Ÿ�� Ȯ��.
		setFigureType(((HMIFigure) figure).getFigureType());
		
		// 2. ��ü �⺻ ���� ���� ���� ����.
		// * ���� ����� ����Ʈ Ȥ�� �ڵ�� �����Ѵ�.
		FigureInfoValidator.validate(figure);
		
		// TODO 3. Ÿ�� �� �м� ���� ����.(�±�, ��ũ��Ʈ)
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
	 * ������ Effect(ȿ��) ������ �м� ������ �����Ѵ�.<br/>
	 * - ��ü�� ���� ȿ�� ���� ���� �� ȿ�� �縳�� �м�, ��� �±� ������ �����Ѵ�.<br/>
	 * - ȿ���� �м� ������ �����Ѵ�.
	 * 
	 * @param effect ȭ�� ��ü ȿ�� ����.
	 */
	public void setEffect(Effect effect) {
		// TODO 1. ȿ�� �縳�� Ȯ��.
		// * �縳�� Ȯ�� ����� List Ȥ�� Code �� �����Ѵ�.
		// List<String> compatibilityResult = EffectCompatibilityValidator.validate(effect);
		EffectCompatibilityValidator.validate(effect);
		
		// TODO GroupEffect�� ���� ó�� �߰�.
		if(effect instanceof GroupEffects) {
			return;
		}
		// 2. ��� ȿ�� ������ Ȯ���Ͽ� ȿ�� �м� ���� ����.
		// ������ ȿ�� �м� ���� Map ���� ����.
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
