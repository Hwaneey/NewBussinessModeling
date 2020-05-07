package analyzer.testcase.window;

import com.naru.uclair.draw.effect.BlinkEffect;
import com.naru.uclair.draw.effect.ColorChangeEffect;
import com.naru.uclair.draw.effect.DragEffect;
import com.naru.uclair.draw.effect.Effect;
import com.naru.uclair.draw.effect.EmergeEffect;
import com.naru.uclair.draw.effect.FillEffect;
import com.naru.uclair.draw.effect.MoveEffect;
import com.naru.uclair.draw.effect.TagValueEffect;
import com.naru.uclair.draw.effect.TouchEffect;


/************************************************
 * @date	: 2020. 5.07.
 * @책임자 : 박보미
 * @설명  	: 화면 테스트 시트 생성 결과 클래스
 * @변경이력 	: 
 ************************************************/

public class WindowTestSheetGenerateResult {
	
	private String tagId;
	private String windowName;
	private String figureId;
	private Effect targetEffect;
	
	/**
	 * @return the tagId
	 */
	public String getTagId() {
		return tagId;
	}
	
	/**
	 * @param tagId the tagId to set
	 */
	public void setTagId(String tagId) {
		this.tagId = tagId;
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
	 * @return the targetEffect
	 */
	public Effect getEffect() {
		return targetEffect;
	}
	
	/**
	 * @param targetEffect the targetEffect to set
	 */
	public void setEffect(Effect targetEffect) {
		this.targetEffect = targetEffect;
	}
	
	public boolean isEmergeEffect() {
		if(null == targetEffect) return false;
		return (targetEffect instanceof EmergeEffect);
	}
	
	public boolean isBlinkEffect() {
		if(null == targetEffect) return false;
		return (targetEffect instanceof BlinkEffect);
	}
	
	public boolean isMoveEffect() {
		if(null == targetEffect) return false;
		return (targetEffect instanceof MoveEffect);
	}
	
	public boolean isDragEffect() {
		if(null == targetEffect) return false;
		return (targetEffect instanceof DragEffect);
	}
	
	public boolean isFillEffect() {
		if(null == targetEffect) return false;
		return (targetEffect instanceof FillEffect);
	}
	
	public boolean isColorChangeEffect() {
		if(null == targetEffect) return false;
		return (targetEffect instanceof ColorChangeEffect);
	}
	
	public boolean isTagDisplayEffect() {
		if(null == targetEffect) return false;
		return (targetEffect instanceof TagValueEffect);
	}
	
	public boolean isTouchEffect() {
		if(null == targetEffect) return false;
		return (targetEffect instanceof TouchEffect);
	}
}
