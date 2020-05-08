package com.naru.uclair.analyzer.testcase.window;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.FileOutputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jhotdraw.draw.AttributeKeys;
import org.jhotdraw.draw.Figure;

import com.naru.uclair.analyzer.analysis.each.ScriptDictionaryAnalyzer;
import com.naru.uclair.analyzer.constants.AnalyzerConstants;
import com.naru.uclair.draw.HMIDrawing;
import com.naru.uclair.draw.HMIDrawingEffects;
import com.naru.uclair.draw.effect.Effect;
import com.naru.uclair.draw.effect.EffectList;
import com.naru.uclair.draw.effect.TouchEffect;
import com.naru.uclair.draw.figure.HMIFigure;
import com.naru.uclair.draw.figure.HMIGroupFigure;
import com.naru.uclair.project.Project;

/**
 *
 * <pre>
 * NAME   : 
 * DESC   : 
 *
 * references : 설계서 NARU-XXX-XXX-XXX
 *
 * Copyright 2012 NARU Technology All rights reserved
 * <pre>
 *
 * @author US Laboratory naruteclab4
 * @since 2012. 7. 5.
 * @version 1.0
 *
 */
public class WindowTestSheetGenerator {
	
	private Project targetProject;
	
	private static WindowTestSheetGenerator windowTestSheetGenerator;
	
	private PropertyChangeSupport eventSupport;
	
	public static final String PROPERTY_NAME = "window.test.sheet.generate"; //$NON-NLS-1$
	private static final String TEMPLATE_FILE_PATH = "/resources/excel/template/window.xls"; //$NON-NLS-1$
	private static final int CELL_START_INDEX = 7;
	private static final String SAVE_FILE_NAME = "%1s-TEST_SHEET.xls"; //$NON-NLS-1$

	private WindowTestSheetGenerator() {
		eventSupport = new PropertyChangeSupport(this);
	}
	
	public static WindowTestSheetGenerator getInstance(Project project) {
		if(null == windowTestSheetGenerator) {
			windowTestSheetGenerator = new WindowTestSheetGenerator();
		}
		else {
			windowTestSheetGenerator.clean();
		}
		windowTestSheetGenerator.setProject(project);
		return windowTestSheetGenerator;
	}
	
	/**
	 * 
	 * 분석 대상 프로젝트 정보를 설정한다.<br/>
	 * 
	 * @param project 프로젝트 정보.
	 */
	private void setProject(Project project) {
		targetProject = project;
	}
	
	private void clean() {
		PropertyChangeListener[] listeners = eventSupport.getPropertyChangeListeners();
		for(PropertyChangeListener l : listeners) {
			eventSupport.removePropertyChangeListener(l);
		}
	}
	
	public void addPropertyChangeListeners(PropertyChangeListener listener) {
		eventSupport.addPropertyChangeListener(listener);
	}
	
	public void generate(String windowName, File saveDir) throws Exception {
		// 1. 해당 화면 파일을 가져온다.
		// 정보를 분석한다.
		if(null == targetProject) {
			// TODO 대상 프로젝트 없음. 분석 대상 없음 메시지 로깅.
			eventSupport.firePropertyChange(PROPERTY_NAME, "generate complete", 100); //$NON-NLS-1$
			throw new Exception(AnalyzerConstants.getString("WindowTestSheetGenerate.Exception.ProjectNull")); //$NON-NLS-1$
		}
		
		// 2. 화면 파일을 로드한다.
		File windowFile = new File(targetProject.getWindowResourcePath());
		windowFile = new File(windowFile, windowName);
		
		HMIDrawing hmiDrawing = HMIDrawing.load(windowFile);
		List<String> figureIdList = figureIdGenerate(hmiDrawing);
		eventSupport.firePropertyChange(PROPERTY_NAME, "Window resource analyze...", 10); //$NON-NLS-1$
		if(figureIdList.isEmpty()) {
			// TODO 객체 없음 로깅.
			eventSupport.firePropertyChange(PROPERTY_NAME, "generate complete", 100); //$NON-NLS-1$
			throw new Exception(AnalyzerConstants.getString("WindowTestSheetGenerate.Exception.FigureEmpty")); //$NON-NLS-1$
		}
		
		// Tag 확인을 위한 effect 정보.
		HMIDrawingEffects drawingEffects = hmiDrawing.getDrawingEffects();
		if(null == drawingEffects) {
			// TODO 이펙트 널 로깅.
			eventSupport.firePropertyChange(PROPERTY_NAME, "generate complete", 100); //$NON-NLS-1$
			throw new Exception(AnalyzerConstants.getString("WindowTestSheetGenerate.Exception.EffectEmpty")); //$NON-NLS-1$
		}
		
		// 전체 생성 결과 리스트.
		LinkedList<WindowTestSheetGenerateResult> generateResultList = new LinkedList<WindowTestSheetGenerateResult>();
		
		// 3. 화면 객체의 효과를 분석한다.
		eventSupport.firePropertyChange(PROPERTY_NAME, "Figure analyze...", 20); //$NON-NLS-1$
		List<WindowTestSheetGenerateResult> analyzeResultList = null;
		Effect effect;
		int tempIndex = 0;
		int tempSize = figureIdList.size();
		for(String figureId : figureIdList) {
			effect = drawingEffects.get(figureId);
			if(null == effect) continue;
			
			if(!(effect instanceof EffectList)) continue;
			
			EffectList effectList = (EffectList) effect;
			if(effectList.isEmpty()) continue;
			
			analyzeResultList = effectListAnalyze(effectList, windowName, figureId);
			if(null != analyzeResultList
					&& !analyzeResultList.isEmpty()) {
				generateResultList.addAll(analyzeResultList);
			}
			eventSupport.firePropertyChange(PROPERTY_NAME, "Figure analyze...[" + figureId + "]", (20 + (60 / tempSize * tempIndex))); //$NON-NLS-1$ //$NON-NLS-2$
			tempIndex++;
		}
		
		if(generateResultList.isEmpty()) {
			eventSupport.firePropertyChange(PROPERTY_NAME, "generate complete", 100); //$NON-NLS-1$
			throw new Exception(AnalyzerConstants.getString("WindowTestSheetGenerate.Exception.GenerateResultEmpty")); //$NON-NLS-1$
		}
		
		// 4. 생성된 정보로 excel 파일을 생성 저장한다.
		try {
			saveTestSheet(saveDir, windowName, generateResultList);
			eventSupport.firePropertyChange(PROPERTY_NAME, "generate complete", 100); //$NON-NLS-1$
		} catch (Exception e) {
			e.printStackTrace();
			eventSupport.firePropertyChange(PROPERTY_NAME, "generate complete", 100); //$NON-NLS-1$
			throw new Exception(AnalyzerConstants.getString("WindowTestSheetGenerate.Exception.ExcelSaveFail")); //$NON-NLS-1$
		}
	}
	
	private void saveTestSheet(File saveDir, String windowName, List<WindowTestSheetGenerateResult> resultList) throws Exception {
		// 1. template 파일 가져오기.
		HSSFWorkbook workbook = new HSSFWorkbook(getClass().getResourceAsStream(TEMPLATE_FILE_PATH));
		HSSFSheet workSheet = workbook.getSheetAt(0);
		
		// 2. 데이터 입력.
		HSSFRow hssfRow = null;
		for(int index = 0; index < resultList.size(); index++) {
			hssfRow = workSheet.getRow(CELL_START_INDEX + index);
			if(null == hssfRow) {
				hssfRow = workSheet.createRow(CELL_START_INDEX + index);
			}
			
			setCellValue(hssfRow, 0, (index + 1) + ""); //$NON-NLS-1$
			setCellValue(hssfRow, 1, resultList.get(index).getWindowName());
			setCellValue(hssfRow, 2, resultList.get(index).getTagId());
			setCellValue(hssfRow, 3, resultList.get(index).getFigureId());
			setCellValue(hssfRow, 4, resultList.get(index).isEmergeEffect() ? "Y" : ""); //$NON-NLS-1$ //$NON-NLS-2$
			setCellValue(hssfRow, 5, resultList.get(index).isBlinkEffect() ? "Y" : ""); //$NON-NLS-1$ //$NON-NLS-2$
			setCellValue(hssfRow, 6, resultList.get(index).isMoveEffect() ? "Y" : ""); //$NON-NLS-1$ //$NON-NLS-2$
			setCellValue(hssfRow, 7, resultList.get(index).isDragEffect() ? "Y" : ""); //$NON-NLS-1$ //$NON-NLS-2$
			setCellValue(hssfRow, 8, resultList.get(index).isFillEffect() ? "Y" : ""); //$NON-NLS-1$ //$NON-NLS-2$
			setCellValue(hssfRow, 9, resultList.get(index).isColorChangeEffect() ? "Y" : ""); //$NON-NLS-1$ //$NON-NLS-2$
			setCellValue(hssfRow, 10, resultList.get(index).isTagDisplayEffect() ? "Y" : ""); //$NON-NLS-1$ //$NON-NLS-2$
			setCellValue(hssfRow, 11, resultList.get(index).isTouchEffect() ? "Y" : ""); //$NON-NLS-1$ //$NON-NLS-2$
		}
		
		// 3. 파일 저장.
		FileOutputStream fileOutputStream = new FileOutputStream(new File(saveDir, String.format(SAVE_FILE_NAME, windowName)));
		workbook.write(fileOutputStream);
		fileOutputStream.close();
	}
	
	private void setCellValue(HSSFRow hssfRow, int index, String value) {
		HSSFCell hssfCell = hssfRow.getCell(index);
		if(null == hssfCell) {
			hssfCell = hssfRow.createCell(index);
		}
		hssfCell.setCellValue(new HSSFRichTextString(value));
	}
	
	private List<WindowTestSheetGenerateResult> effectListAnalyze(EffectList effectList, 
			String windowName, String figureId) {
		LinkedList<WindowTestSheetGenerateResult> generateResultList = new LinkedList<WindowTestSheetGenerateResult>();
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
				for(String tagId : linkTags) {
					// 결과 객체 생성 및 추가.
					WindowTestSheetGenerateResult generateResult = new WindowTestSheetGenerateResult();
					generateResult.setTagId(tagId);
					generateResult.setWindowName(windowName);
					generateResult.setFigureId(figureId);
					generateResult.setEffect(effect);
					
					generateResultList.add(generateResult);
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
					// 결과 객체 생성 및 추가.
					WindowTestSheetGenerateResult generateResult = new WindowTestSheetGenerateResult();
					generateResult.setTagId(tagId);
					generateResult.setWindowName(windowName);
					generateResult.setFigureId(figureId);
					generateResult.setEffect(effect);
					
					generateResultList.add(generateResult);
				}
			}
		}
		return generateResultList;
	}
	
	private List<String> figureIdGenerate(HMIDrawing drawing) {
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
}
