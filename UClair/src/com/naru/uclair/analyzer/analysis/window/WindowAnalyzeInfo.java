/**
 * 
 */
package com.naru.uclair.analyzer.analysis.window;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jhotdraw.draw.AttributeKeys;
import org.jhotdraw.draw.Figure;

import com.naru.uclair.analyzer.analysis.window.validator.WindowInfoValidator;
import com.naru.uclair.draw.HMIDrawing;
import com.naru.uclair.draw.HMIDrawingEffects;
import com.naru.uclair.draw.HMIDrawingScriptProperty;
import com.naru.uclair.draw.HMIDrawingWindowProperty;
import com.naru.uclair.draw.effect.Effect;

/**
 * ȭ�� �м� ������ ��� Ŭ����.<br/>
 * - �� ȭ���� �Ӽ� ������ ������.
 * 
 * @author ywpark
 *
 */
public class WindowAnalyzeInfo {
	
	private String windowName;
	private int pointX;
	private int pointY;
	private int width;
	private int height;
	/**
	 * HMIDrawing �⺻ ǥ�� ���� (������â / �˾�â)
	 * MODE_FRAME | MODE_POPUP
	 */
	private int windowType;
	private boolean useTitleBar;
	private String titleName;
	private int titleType;
	private Color backgroundColor;
	private boolean useBackgroundImage;
	private String imageFileName;
	private int imageArrangeType;
	private boolean useOpenScript;
	private int openScriptOperCycle;
	private String openScript;
	private boolean useCloseScript;
	private String closeScript;
	private String windowErrorCode;
	
	private Map<String, FigureAnalyzeInfo> figureAnalyzeInfoMap;
	
	/**
	 * WindowAnalyzeInfo ������.<br/>
	 * - ������ ȭ����� {@link HMIDrawing} ������ �м� ������ �����.
	 * 
	 * @param drawing HMI ȭ�� ����.
	 */
	public WindowAnalyzeInfo(String winName, HMIDrawing drawing) {
		this.windowName = winName;
		figureAnalyzeInfoMap = new HashMap<String, FigureAnalyzeInfo>();
		setHMIDrawing(drawing);
	}
	
	/**
	 * WindowAnalyzeInfo ���� ���Ǵ� ��� ������ �Ұ��Ѵ�.
	 * - {@link #figureAnalyzeInfoMap} ���� �Ұ�.
	 */
	public void clean() {
		Collection<FigureAnalyzeInfo> values = figureAnalyzeInfoMap.values();
		for(FigureAnalyzeInfo value : values) {
			value.clean();
		}
		figureAnalyzeInfoMap.clear();
	}

	/**
	 * ������ {@link HMIDrawing} ������ �м� ������ �����.<br/>
	 * - ȭ�� �⺻ �Ӽ��� �����Ѵ�.<br/>
	 * - ȭ�� ��ũ��Ʈ �Ӽ��� �����Ѵ�.
	 * 
	 * @param drawing HMI ȭ�� ����.
	 */
	public void setHMIDrawing(HMIDrawing drawing) {
		// 1. ȭ�� �Ӽ� Generate
		HMIDrawingWindowProperty windowProperty = drawing.getWindowProperty();
		Rectangle bounds = windowProperty.getBounds();
		setPointX(bounds.x);
		setPointY(bounds.y);
		setWidth(bounds.width);
		setHeight(bounds.height);
		
		setWindowType(windowProperty.getWindowMode());
		setUseTitleBar(windowProperty.isShowTitleBar());
		setTitleName(windowProperty.getTitle());
		// TODO Title Type Generator ����.
		// setTitleType(widnowProperty.getTitleBarProperty);
		
		setBackgroundColor(windowProperty.getBackgroundColor());
		String imageName = windowProperty.getImageName();
		setUseBackgroundImage(null != imageName 
				&& !imageName.isEmpty());
		setImageFileName(imageName);
		setImageArrangeType(windowProperty.getImageArrangeStyle());
		
		// 2. ��ũ��Ʈ �Ӽ� Generate
		HMIDrawingScriptProperty scriptProperty = drawing.getScriptProperty();
		if(null != scriptProperty) {
			if(scriptProperty.isUseOpenScript()) {
				setUseOpenScript(true);
				setOpenScriptOperCycle(scriptProperty.getOpenScriptCycle());
			}
			else {
				setUseOpenScript(false);
				setOpenScriptOperCycle(0);
			}
			setOpenScript(scriptProperty.getOpenScript());
			
			setUseCloseScript(scriptProperty.isUseCloseScript());
			setCloseScript(scriptProperty.getCloseScript());
		}
		
		// TODO 3. ȭ�� ���� ���� ����.(�⺻���� �׸� �̹���, ȭ�� ������)
		// * ����� �ڵ�� �����Ѵ�.
		WindowInfoValidator.validate(drawing);
		
		// TODO 4. ȭ�� ��ũ��Ʈ���� ����ϴ� ����� �Լ�, �±� ���� �̾Ƴ���.
		// * Script ���� API�� ����Ѵ�.
		// * �ش� ����� List Ȥ�� Map ���� �����Ѵ�.
		// windowScriptValidate();
		
		// 5. ȭ�� ��ü �Ӽ� Generate
		// 6. ȭ�� ��ü ȿ�� �Ӽ� Generate
		List<Figure> figureList = drawing.getChildren();
		HMIDrawingEffects effects = drawing.getDrawingEffects();
		
		String figureId = null;
		Effect effect = null;
		for(Figure figure : figureList) {
			figureId = AttributeKeys.ID.get(figure);
			
			FigureAnalyzeInfo figureAnalyzeInfo = new FigureAnalyzeInfo(figureId);
			figureAnalyzeInfo.setWindowName(windowName);
			figureAnalyzeInfo.setFigure(figure);
			effect = effects.get(figureId);
			if(null != effect) {
				figureAnalyzeInfo.setEffect(effects.get(figureId));
			}
			
			// ������ FigureAnalyze ������ Map���� �����Ѵ�.
			figureAnalyzeInfoMap.put(figureId, figureAnalyzeInfo);
		}
		
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
	 * @return the pointX
	 */
	public int getPointX() {
		return pointX;
	}

	/**
	 * @param pointX the pointX to set
	 */
	public void setPointX(int pointX) {
		this.pointX = pointX;
	}

	/**
	 * @return the pointY
	 */
	public int getPointY() {
		return pointY;
	}

	/**
	 * @param pointY the pointY to set
	 */
	public void setPointY(int pointY) {
		this.pointY = pointY;
	}

	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @param width the width to set
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @param height the height to set
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * @return the windowType
	 */
	public int getWindowType() {
		return windowType;
	}

	/**
	 * @param windowType the windowType to set
	 */
	public void setWindowType(int windowType) {
		this.windowType = windowType;
	}

	/**
	 * @return the useTitleBar
	 */
	public boolean isUseTitleBar() {
		return useTitleBar;
	}

	/**
	 * @param useTitleBar the useTitleBar to set
	 */
	public void setUseTitleBar(boolean useTitleBar) {
		this.useTitleBar = useTitleBar;
	}

	/**
	 * @return the titleName
	 */
	public String getTitleName() {
		return titleName;
	}

	/**
	 * @param titleName the titleName to set
	 */
	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}

	/**
	 * @return the titleType
	 */
	public int getTitleType() {
		return titleType;
	}

	/**
	 * @param titleType the titleType to set
	 */
	public void setTitleType(int titleType) {
		this.titleType = titleType;
	}

	/**
	 * @return the backgroundColor
	 */
	public Color getBackgroundColor() {
		return backgroundColor;
	}

	/**
	 * @param backgroundColor the backgroundColor to set
	 */
	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	/**
	 * @return the useBackgroundImage
	 */
	public boolean isUseBackgroundImage() {
		return useBackgroundImage;
	}

	/**
	 * @param useBackgroundImage the useBackgroundImage to set
	 */
	public void setUseBackgroundImage(boolean useBackgroundImage) {
		this.useBackgroundImage = useBackgroundImage;
	}

	/**
	 * @return the imageFileName
	 */
	public String getImageFileName() {
		return imageFileName;
	}

	/**
	 * @param imageFileName the imageFileName to set
	 */
	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}
	
	/**
	 * @return the imageArrangeType
	 */
	public int getImageArrangeType() {
		return imageArrangeType;
	}

	/**
	 * @param imageArrangeType the imageArrangeType to set
	 */
	public void setImageArrangeType(int imageArrangeType) {
		this.imageArrangeType = imageArrangeType;
	}

	/**
	 * @return the useOpenScript
	 */
	public boolean isUseOpenScript() {
		return useOpenScript;
	}

	/**
	 * @param useOpenScript the useOpenScript to set
	 */
	public void setUseOpenScript(boolean useOpenScript) {
		this.useOpenScript = useOpenScript;
	}

	/**
	 * @return the openScriptOperCycle
	 */
	public int getOpenScriptOperCycle() {
		return openScriptOperCycle;
	}

	/**
	 * @param openScriptOperCycle the openScriptOperCycle to set
	 */
	public void setOpenScriptOperCycle(int openScriptOperCycle) {
		this.openScriptOperCycle = openScriptOperCycle;
	}

	/**
	 * @return the openScript
	 */
	public String getOpenScript() {
		return openScript;
	}

	/**
	 * @param openScript the openScript to set
	 */
	public void setOpenScript(String openScript) {
		this.openScript = openScript;
	}

	/**
	 * @return the useCloseScript
	 */
	public boolean isUseCloseScript() {
		return useCloseScript;
	}

	/**
	 * @param useCloseScript the useCloseScript to set
	 */
	public void setUseCloseScript(boolean useCloseScript) {
		this.useCloseScript = useCloseScript;
	}

	/**
	 * @return the closeScript
	 */
	public String getCloseScript() {
		return closeScript;
	}

	/**
	 * @param closeScript the closeScript to set
	 */
	public void setCloseScript(String closeScript) {
		this.closeScript = closeScript;
	}

	/**
	 * @return the windowErrorCode
	 */
	public String getWindowErrorCode() {
		return windowErrorCode;
	}

	/**
	 * @param windowErrorCode the windowErrorCode to set
	 */
	public void setWindowErrorCode(String windowErrorCode) {
		this.windowErrorCode = windowErrorCode;
	}

}
