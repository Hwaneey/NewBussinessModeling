/**
 * 
 */
package com.naru.uclair.analyzer.analysis.window;

import com.naru.uclair.draw.effect.Effect;

/**
 * 객체의 효과 분석 정보를 담는 클래스.<br/>
 * - 각 효과 속성 정보 및 분석 정보를 가진다.
 * 
 * @author ywpark
 *
 */
public class EffectAnalyzeInfo {
	/**
	 * 효과 유형.
	 */
	private int effectType;
	/**
	 * 효과가 속한 객체 아이디.
	 */
	private String figureId;
	/**
	 * 효과가 속한 객체가 속한 화면명.
	 */
	private String windowName;
	
	/**
	 * EffectAnalyzeInfo 생성자.<br/>
	 * - 지정된 효과 유형을 설정한다.
	 * 
	 * @param effectType 효과 유형.
	 */
	public EffectAnalyzeInfo(int effectType) {
		setEffectType(effectType);
	}
	
	public void clean() {
		// TODO 1. 태그 리스트 제거.
		// TODO 2. 스크립트 리스트 제거.
	}

	/**
	 * 지정된 Effect 정보로 효과 분석 정보를 생성 설정한다.<br/>
	 * - 태그 정보/효과 정보를 설정한다.
	 * 
	 * @param effect 객체 효과 정보.
	 */
	public void setEffect(Effect effect) {
		// TODO 1. 효과에서 사용하는 태그 정보 생성.
		// * 생성 결과는 List 혹은 Map 으로 관리한다.
		
		// TODO 2. 효과에서 사용하는 스크립트 정보 생성.
		// * 생성 결과는 List 혹은 Map 으로 관리한다.
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
