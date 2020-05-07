package analyzer.analysis.effect;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jhotdraw.draw.AttributeKeys;
import org.jhotdraw.draw.Figure;

import com.naru.uclair.draw.HMIDrawing;
import com.naru.uclair.draw.HMIDrawingEffects;
import com.naru.uclair.draw.common.HMIDrawingFileFilter;
import com.naru.uclair.draw.effect.Effect;
import com.naru.uclair.draw.effect.EffectList;
import com.naru.uclair.draw.effect.TouchEffect;
import com.naru.uclair.draw.figure.HMIFigure;
import com.naru.uclair.draw.figure.HMIGroupFigure;
import com.naru.uclair.project.Project;
import com.naru.uclair.util.HmiVMOptions;

/**
 *
 * <pre>
 * NAME   : 
 * DESC   : 
 *
 * references : ���輭 NARU-XXX-XXX-XXX
 *
 * Copyright 2012 NARU Technology All rights reserved
 * <pre>
 *
 * @author US Laboratory naruteclab4
 * @since 2012. 6. 28.
 * @version 1.0
 *
 */
public class EffectCompatibilityAnalyzer {
	
	private Project targetProject;
	
	private static EffectCompatibilityAnalyzer effectCompatibilityAnalyzer;
	
	private PropertyChangeSupport eventSupport;
	
	public static final String PROPERTY_NAME = "efffect.compatibility.anlyzer";
	
	/**
	 * singleton �� ���� ������.
	 */
	private EffectCompatibilityAnalyzer() {
		eventSupport = new PropertyChangeSupport(this);
	}
	
	/**
	 * 
	 * ObjectTagLinkAnalyzer �ν��Ͻ� ������.<br/>
	 * - �м��� ������ �����Ѵ�.<br/>
	 * - ���� ������ �ִ� ��� ��� �Ұ��Ѵ�.
	 * 
	 * @param project �м� ��� ������Ʈ ����.
	 * @return
	 */
	public static EffectCompatibilityAnalyzer getInstance(Project project) {
		if(null == effectCompatibilityAnalyzer) {
			effectCompatibilityAnalyzer = new EffectCompatibilityAnalyzer();
		}
		else {
			effectCompatibilityAnalyzer.clean();
		}
		effectCompatibilityAnalyzer.setProject(project);
		return effectCompatibilityAnalyzer;
	}
	
	/**
	 * 
	 * �м� ��� ������Ʈ ������ �����Ѵ�.<br/>
	 * 
	 * @param project ������Ʈ ����.
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
	
	public List<EffectCompatibilityResult> analyze() {
		eventSupport.firePropertyChange(PROPERTY_NAME, "Analyze start", 0);
		ArrayList<EffectCompatibilityResult> totalResultList = new ArrayList<EffectCompatibilityResult>();
		if(null == targetProject) {
			// TODO �м� ������Ʈ ���� �α�.
			eventSupport.firePropertyChange(PROPERTY_NAME, "Analyze complete", 100);
			return null;
		}
		
		// 1. ȭ�� �ε�.
		eventSupport.firePropertyChange(PROPERTY_NAME, "Window resource loading..", 20);
		Map<String, HMIDrawing> windowMap = loadFileWindow(targetProject.getWindowResourcePath());
		if(windowMap.isEmpty()) {
			// TODO �м� ȭ�� ���� �α�.
			eventSupport.firePropertyChange(PROPERTY_NAME, 0, 100);
			return null;
		}
		
		// 2. ȭ�麰 ��ü ����Ʈ Ȯ��
		Set<String> windowNameSet = windowMap.keySet();
		int totalSize = windowNameSet.size();
		int tempProgress = 20;
		List<EffectCompatibilityResult> effectResultList;
		for(String windowName : windowNameSet) {
			effectResultList = effectAnalyze(windowName, windowMap.get(windowName));
			tempProgress += (80 / totalSize);
			eventSupport.firePropertyChange(PROPERTY_NAME, "Analyzing.. - " + windowName, tempProgress);
			if(null != effectResultList
					&& !effectResultList.isEmpty()) {
				totalResultList.addAll(effectResultList);
			}
		}
		eventSupport.firePropertyChange(PROPERTY_NAME, "Analyze complete", 100);
		
		return totalResultList;
	}
	
	private List<EffectCompatibilityResult> effectAnalyze(String windowName, HMIDrawing drawing) {
		if(null == drawing) {
			// TODO ȭ�� ���� ���� �α�.
			return null;
		}
		
		// 1. ��ü ���̵� ����Ʈ ����.
		List<String> figureIdList = figureIdGenerate(drawing);
		
		if(figureIdList.isEmpty()) {
			// TODO ��ü ���� �α�.
			return null;
		}
		
		// ��� ����Ʈ.
		LinkedList<EffectCompatibilityResult> compatibilityAnalyzeList = new LinkedList<EffectCompatibilityResult>();
		
		// 2. ��ü�� ���� Effect Ȯ��.
		HMIDrawingEffects drawingEffects = drawing.getDrawingEffects();
		for(String figureId : figureIdList) {
			Effect effect = drawingEffects.get(figureId);
			
			if(null == effect) continue;
			
			if(!(effect instanceof EffectList)) continue;
			
			EffectList effectList = (EffectList) effect;
			if(effectList.isEmpty()) continue;
			
			compatibilityAnalyzeList.addAll(compatibilityValidator(effectList, windowName, figureId));
		}
		
		return compatibilityAnalyzeList;
	}
	
	private List<EffectCompatibilityResult> compatibilityValidator(EffectList effectList, 
			String windowName, String figureId) {
		// ��� ����Ʈ.
		LinkedList<EffectCompatibilityResult> compatibilityList = new LinkedList<EffectCompatibilityResult>();
		
		// 1. effectList�� count�� ����.
		int count = 0;
		boolean useTouchEffect = false;
		Effect[] effects = effectList.getEffects();
		for(Effect effect : effects) {
			if(null == effect) continue;
			count++;
			
			if(!useTouchEffect) {
				useTouchEffect = (effect instanceof TouchEffect);
			}
		}
		// 2. count �� 2�� �̻��̸� ȿ���� �縳�� �˻�.
		if(count >= 2) {
			// �縳�� �˻�.
			boolean useEmerge = (null != effectList.getEffect(Effect.EMERGE_IDX));
			boolean useBlink = (null != effectList.getEffect(Effect.BLINK_IDX));
			boolean useMove = (null != effectList.getEffect(Effect.MOVE_IDX));
			boolean useDrag = (null != effectList.getEffect(Effect.DRAG_IDX));
			boolean useFill = (null != effectList.getEffect(Effect.FILL_IDX));
			boolean useColorChange = (null != effectList.getEffect(Effect.COLORCHANGE_IDX));
			boolean useTagDisplay = (null != effectList.getEffect(Effect.TAGDISPLAY_IDX));
			boolean useTouch = (null != effectList.getEffect(Effect.TOUCH_IDX));
			
			// ��� warning.
			if(useEmerge && (useBlink 
					|| useMove 
					|| useDrag 
					|| useFill 
					|| useColorChange 
					|| useTagDisplay 
					|| useTouch)) {
				FigureEffectCompatibilityResult compatibilityResult = new FigureEffectCompatibilityResult();
				compatibilityResult.setWindowName(windowName);
				compatibilityResult.setFigureId(figureId);
				compatibilityResult.setEffectList(effectList);
				compatibilityResult.setPriority(EffectCompatibilityResult.PRIORITY_WARNING);
				compatibilityResult.setCompatibilityType(EffectCompatibilityResult.TYPE_USED_WITH_EMERGE);
				compatibilityList.add(compatibilityResult);
			}
			
			// �̵� + ���� error.
			if(useMove && useDrag) {
				FigureEffectCompatibilityResult compatibilityResult = new FigureEffectCompatibilityResult();
				compatibilityResult.setWindowName(windowName);
				compatibilityResult.setFigureId(figureId);
				compatibilityResult.setEffectList(effectList);
				compatibilityResult.setPriority(EffectCompatibilityResult.PRIORITY_ERROR);
				compatibilityResult.setCompatibilityType(EffectCompatibilityResult.TYPE_DUPLICATION_DRAG_AND_MOVE);
				compatibilityList.add(compatibilityResult);
			}
			
			// ���� + ��ġ error.
			if(useDrag && useTouch) {
				FigureEffectCompatibilityResult compatibilityResult = new FigureEffectCompatibilityResult();
				compatibilityResult.setWindowName(windowName);
				compatibilityResult.setFigureId(figureId);
				compatibilityResult.setEffectList(effectList);
				compatibilityResult.setPriority(EffectCompatibilityResult.PRIORITY_ERROR);
				compatibilityResult.setCompatibilityType(EffectCompatibilityResult.TYPE_DUPLICATION_DRAG_AND_TOUCH);
				compatibilityList.add(compatibilityResult);
			}
		}
		
		// 3. ��ġ ȿ���� ���� ȿ�� �縳�� �˻�.
		if(useTouchEffect) {
			// ��ġ ���� ȿ�� �縳�� �˻�.
			TouchEffect effect = (TouchEffect) effectList.getEffect(Effect.TOUCH_IDX);
			
			boolean useOpenScreen = (TouchEffect.SCREEN_OPEN & effect.getType()) > 0;
			boolean useCloseScreen = (TouchEffect.SCREEN_CLOSE & effect.getType()) > 0;
			boolean useControl = (TouchEffect.TAG_CONTROL & effect.getType()) > 0;
			boolean useScriptExecute = (TouchEffect.SCRIPT_EXECUTE & effect.getType()) > 0;
			boolean usePopupMenu = (TouchEffect.POPUP_MENU & effect.getType()) > 0;
			
			if(useOpenScreen && (useCloseScreen
					|| useControl 
					|| useScriptExecute 
					|| usePopupMenu)) {
				FigureEffectCompatibilityResult compatibilityResult = new FigureEffectCompatibilityResult();
				compatibilityResult.setWindowName(windowName);
				compatibilityResult.setFigureId(figureId);
				compatibilityResult.setEffectList(effectList);
				compatibilityResult.setPriority(EffectCompatibilityResult.PRIORITY_WARNING);
				compatibilityResult.setCompatibilityType(EffectCompatibilityResult.TYPE_TOUCH_COMPATIBILITY);
				compatibilityList.add(compatibilityResult);
			}
			
			if(useCloseScreen && (useControl
					|| useScriptExecute
					|| usePopupMenu)) {
				FigureEffectCompatibilityResult compatibilityResult = new FigureEffectCompatibilityResult();
				compatibilityResult.setWindowName(windowName);
				compatibilityResult.setFigureId(figureId);
				compatibilityResult.setEffectList(effectList);
				compatibilityResult.setPriority(EffectCompatibilityResult.PRIORITY_WARNING);
				compatibilityResult.setCompatibilityType(EffectCompatibilityResult.TYPE_TOUCH_COMPATIBILITY);
				compatibilityList.add(compatibilityResult);
			}
			
			if(useControl && useScriptExecute) {
				FigureEffectCompatibilityResult compatibilityResult = new FigureEffectCompatibilityResult();
				compatibilityResult.setWindowName(windowName);
				compatibilityResult.setFigureId(figureId);
				compatibilityResult.setEffectList(effectList);
				compatibilityResult.setPriority(EffectCompatibilityResult.PRIORITY_WARNING);
				compatibilityResult.setCompatibilityType(EffectCompatibilityResult.TYPE_TOUCH_COMPATIBILITY);
				compatibilityList.add(compatibilityResult);
			}
		}
		return compatibilityList;
	}
	
	private List<String> figureIdGenerate(HMIDrawing drawing) {
		LinkedList<String> figureIdList = new LinkedList<String>();
		
		List<Figure> figureList = drawing.getChildren();
		for(Figure figure : figureList) {
			if(!(figure instanceof HMIFigure)) continue;
			
			HMIFigure hmiFigure = (HMIFigure) figure;
			
			int figureType = hmiFigure.getFigureType();
			if(HMIFigure.TYPE_BEANS == figureType) {
				// ������Ʈ�� ó������ �ʴ´�.
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
	 * �м� ��� ������Ʈ�� ȭ�� ���ҽ� ������ ��� ȭ���� �ε��Ͽ� ��ȯ�Ѵ�.<br/>
	 * - ȭ�� �ε� ���н� ���� �α׸� ����� ��� �ε��Ѵ�.<br/>
	 * 
	 * @return drawingMap �ε��� ȭ�� ��.
	 */
	private Map<String, HMIDrawing> loadFileWindow(URI windowResourcePath) {
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
