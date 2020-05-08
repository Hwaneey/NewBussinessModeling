package com.naru.uclair.analyzer.analysis.effect;

import com.naru.uclair.draw.effect.Effect;
import com.naru.uclair.draw.effect.EffectList;

/**
 *
 * <pre>
 * NAME   : com.naru.uclair.analyzer.analysis.effect.FigureEffectCompatibilityResult.java
 * DESC   : 화면 객체 효과 양립석 분석 결과 클래스.
 *
 * references : 설계서 NARU-XXX-XXX-XXX
 *
 * Copyright 2012 NARU Technology All rights reserved
 * <pre>
 *
 * @author US Laboratory naruteclab4
 * @since 2012. 7. 2.
 * @version 1.0
 *
 */
public class FigureEffectCompatibilityResult implements
		EffectCompatibilityResult {

	/**
	 * 양립성 분석용 Effect 정보.
	 */
	private EffectList effectList;
	/**
	 * 화면명.
	 */
	private String windowName;
	/**
	 * 객체 아이디.
	 */
	private String figureId;
	
	/**
	 * 양립성 분석 등급.
	 */
	private int priority;
	/**
	 * 양립성 유형.
	 */
	private int type;

	/* (non-Javadoc)
	 * @see com.naru.uclair.analyzer.analysis.effect.EffectCompatibilityResult#getWindowName()
	 */
	@Override
	public String getWindowName() {
		return windowName;
	}

	/* (non-Javadoc)
	 * @see com.naru.uclair.analyzer.analysis.effect.EffectCompatibilityResult#setWindowName(java.lang.String)
	 */
	@Override
	public void setWindowName(String windowName) {
		this.windowName = windowName;
	}

	/* (non-Javadoc)
	 * @see com.naru.uclair.analyzer.analysis.effect.EffectCompatibilityResult#getFigureId()
	 */
	@Override
	public String getFigureId() {
		return figureId;
	}

	/* (non-Javadoc)
	 * @see com.naru.uclair.analyzer.analysis.effect.EffectCompatibilityResult#setFigureId(java.lang.String)
	 */
	@Override
	public void setFigureId(String id) {
		this.figureId = id;
	}

	/* (non-Javadoc)
	 * @see com.naru.uclair.analyzer.analysis.effect.EffectCompatibilityResult#getPriority()
	 */
	@Override
	public int getPriority() {
		return this.priority;
	}

	/* (non-Javadoc)
	 * @see com.naru.uclair.analyzer.analysis.effect.EffectCompatibilityResult#setPriority(int)
	 */
	@Override
	public void setPriority(int priorty) {
		this.priority = priorty;
	}

	/* (non-Javadoc)
	 * @see com.naru.uclair.analyzer.analysis.effect.EffectCompatibilityResult#getCompatibilityType()
	 */
	@Override
	public int getCompatibilityType() {
		return type;
	}

	/* (non-Javadoc)
	 * @see com.naru.uclair.analyzer.analysis.effect.EffectCompatibilityResult#setCompatibilityType(int)
	 */
	@Override
	public void setCompatibilityType(int type) {
		this.type = type;
	}

	/* (non-Javadoc)
	 * @see com.naru.uclair.analyzer.analysis.effect.EffectCompatibilityResult#useEmerge()
	 */
	@Override
	public boolean useEmerge() {
		if(null == effectList) return false;
		return (null != effectList.getEffect(Effect.EMERGE_IDX));
	}

	/* (non-Javadoc)
	 * @see com.naru.uclair.analyzer.analysis.effect.EffectCompatibilityResult#useBlink()
	 */
	@Override
	public boolean useBlink() {
		if(null == effectList) return false;
		return (null != effectList.getEffect(Effect.BLINK_IDX));
	}

	/* (non-Javadoc)
	 * @see com.naru.uclair.analyzer.analysis.effect.EffectCompatibilityResult#useMove()
	 */
	@Override
	public boolean useMove() {
		if(null == effectList) return false;
		return (null != effectList.getEffect(Effect.MOVE_IDX));
	}

	/* (non-Javadoc)
	 * @see com.naru.uclair.analyzer.analysis.effect.EffectCompatibilityResult#useDrag()
	 */
	@Override
	public boolean useDrag() {
		if(null == effectList) return false;
		return (null != effectList.getEffect(Effect.DRAG_IDX));
	}

	/* (non-Javadoc)
	 * @see com.naru.uclair.analyzer.analysis.effect.EffectCompatibilityResult#useTagDisplay()
	 */
	@Override
	public boolean useTagDisplay() {
		if(null == effectList) return false;
		return (null != effectList.getEffect(Effect.TAGDISPLAY_IDX));
	}

	/* (non-Javadoc)
	 * @see com.naru.uclair.analyzer.analysis.effect.EffectCompatibilityResult#useTouch()
	 */
	@Override
	public boolean useTouch() {
		if(null == effectList) return false;
		return (null != effectList.getEffect(Effect.TOUCH_IDX));
	}
	
	/**
	 * 
	 * 효과 리스트를 설정한다.<br/>
	 * 
	 * @param effectList 효과 리스트.
	 */
	public void setEffectList(EffectList effectList) {
		this.effectList = effectList;
	}

}
