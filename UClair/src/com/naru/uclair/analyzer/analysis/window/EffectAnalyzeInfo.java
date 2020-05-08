/**
 * 
 */
package com.naru.uclair.analyzer.analysis.window;

import com.naru.uclair.draw.effect.Effect;

/**
 * ��ü�� ȿ�� �м� ������ ��� Ŭ����.<br/>
 * - �� ȿ�� �Ӽ� ���� �� �м� ������ ������.
 * 
 * @author ywpark
 *
 */
public class EffectAnalyzeInfo {
	/**
	 * ȿ�� ����.
	 */
	private int effectType;
	/**
	 * ȿ���� ���� ��ü ���̵�.
	 */
	private String figureId;
	/**
	 * ȿ���� ���� ��ü�� ���� ȭ���.
	 */
	private String windowName;
	
	/**
	 * EffectAnalyzeInfo ������.<br/>
	 * - ������ ȿ�� ������ �����Ѵ�.
	 * 
	 * @param effectType ȿ�� ����.
	 */
	public EffectAnalyzeInfo(int effectType) {
		setEffectType(effectType);
	}
	
	public void clean() {
		// TODO 1. �±� ����Ʈ ����.
		// TODO 2. ��ũ��Ʈ ����Ʈ ����.
	}

	/**
	 * ������ Effect ������ ȿ�� �м� ������ ���� �����Ѵ�.<br/>
	 * - �±� ����/ȿ�� ������ �����Ѵ�.
	 * 
	 * @param effect ��ü ȿ�� ����.
	 */
	public void setEffect(Effect effect) {
		// TODO 1. ȿ������ ����ϴ� �±� ���� ����.
		// * ���� ����� List Ȥ�� Map ���� �����Ѵ�.
		
		// TODO 2. ȿ������ ����ϴ� ��ũ��Ʈ ���� ����.
		// * ���� ����� List Ȥ�� Map ���� �����Ѵ�.
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
	 * @return the effectType
	 */
	public int getEffectType() {
		return effectType;
	}

	/**
	 * @param effectType the effectType to set
	 */
	public void setEffectType(int effectType) {
		this.effectType = effectType;
	}
	
}
