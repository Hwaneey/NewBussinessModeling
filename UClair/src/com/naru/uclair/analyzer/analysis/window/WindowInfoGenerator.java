package com.naru.uclair.analyzer.analysis.window;

import java.io.File;
import java.io.FileFilter;
import java.net.URI;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.naru.uclair.draw.HMIDrawing;
import com.naru.uclair.draw.common.HMIDrawingFileFilter;
import com.naru.uclair.draw.io.HMIDrawingLoader;
import com.naru.uclair.exception.ProjectNotLoadedException;
import com.naru.uclair.project.Project;

/**
 * ��ü �±� ���� ���� �м��� ���� ȭ�� ���� ������ ����� Ŭ����.<br/>
 * - ������Ʈ ������ ��� ȭ�� ������ �ε��Ͽ�, ���� ������ �����Ѵ�.
 * 
 * @author ywpark
 * 
 */
public class WindowInfoGenerator {

	/**
	 * ȭ�� ���� �м��� �ν��Ͻ�.
	 */
	private static WindowInfoGenerator analyzer;
	/**
	 * ȭ�� ���� �м��� ���� Uclair ������Ʈ ����.
	 */
	private Project project;
	/**
	 * ȭ�� ���� ��ġ ����.
	 */
	private URI windowResourcePath;
	/**
	 * ȭ�� ������ �����ϱ� ���� ���� ����.
	 */
	private FileFilter winFileFilter;
	/**
	 * �м� ��� ������Ʈ�� ��� ȭ�� ����.
	 */
	private Map<String, HMIDrawing> drawingMap;
	private Map<String, WindowAnalyzeInfo> windowAnalyzeInfoMap;

	/**
	 * WindowInfoGenerator ������.<br/>
	 * - ���� ������ �� ������ �Ѵ�.
	 * 
	 */
	private WindowInfoGenerator() {
		winFileFilter = new HMIDrawingFileFilter();
		drawingMap = new HashMap<String, HMIDrawing>();
		windowAnalyzeInfoMap = new HashMap<String, WindowAnalyzeInfo>();
	}

	/**
	 * WindowInfoAnalyzer �ν��Ͻ��� �����Ͽ� ��ȯ�Ѵ�.<br/>
	 * -
	 * 
	 * @return ȭ�� ���� �м���.
	 */
	public static WindowInfoGenerator createInstance() {
		if (null == analyzer) {
			analyzer = new WindowInfoGenerator();
		}
		return analyzer;
	}
	
	/**
	 * WindowInfoGenerator�� ���� ��� ���� �� �м� ��� ������ �Ұ��Ѵ�.<br/>
	 * - {@link #drawingMap} ���� �Ұ�.<br/>
	 * - {@link #windowAnalyzeInfoMap} ���� �Ұ�.
	 */
	public void clean() {
		// 1. ȭ�� ���� ��� �Ұ�.
		Collection<HMIDrawing> drawings = drawingMap.values();
		for(HMIDrawing drawing : drawings) {
			drawing.dispose();
		}
		drawingMap.clear();
		
		// 2. ȭ�� �м��� ���� ��� �Ұ�.
		Collection<WindowAnalyzeInfo> values = windowAnalyzeInfoMap.values();
		for(WindowAnalyzeInfo value : values) {
			value.clean();
		}
		windowAnalyzeInfoMap.clear();
		
		windowResourcePath = null;
		project = null;
	}

	/**
	 * ������Ʈ�� ȭ�� ������ ���� �м� �����͸� �����Ѵ�.<br/>
	 * - 1. ȭ�� ������ �ε��Ѵ�.<br/>
	 * - 2. �ε�� ȭ�� ������ ���� �м� �����͸� �����Ѵ�.
	 * 
	 * @param project �м� ��� ������Ʈ ����.
	 */
	public void generate(Project project) {
		// �м� ��� ���� �� ���� ���� Ŭ��.
		clean();

		this.project = project;
		this.windowResourcePath = project.getWindowResourcePath();

		// 1. window File load.
		// * ȭ�� ���� �ε�� ���� ȣ��Ǵ� ��ġ�� ����� �� �ִ�.
		loadFileWindow();

		// 2. generate window file
		Set<String> windowNames = drawingMap.keySet();
		for(String winName : windowNames) {
			// Generate �� ���� Map ���� ����.
			WindowAnalyzeInfo analyzeInfo = new WindowAnalyzeInfo(winName, drawingMap.get(winName));
			windowAnalyzeInfoMap.put(analyzeInfo.getWindowName(), analyzeInfo);
		}
	}

	/**
	 * �м� ��� ������Ʈ�� ȭ�� ���ҽ� ������ ��� ȭ���� �ε��Ͽ� �����Ѵ�.<br/>
	 * - ȭ�� �ε� ���н� ���� �α׸� ����� ��� �ε��Ѵ�.<br/>
	 * - �ε�� ȭ���� {@link #drawingMap}�� �����Ѵ�.
	 * 
	 */
	protected void loadFileWindow() {
		File windowDir = new File(windowResourcePath);
		File[] windowFiles = windowDir.listFiles(winFileFilter);

		HMIDrawing drawing = null;
		for (File wf : windowFiles) {
			System.out.println("File name : " + wf.getName());
			System.out.println("File absolutePath : " + wf.getAbsolutePath());

			if (null == drawing) {
				drawing = HMIDrawingLoader.loadSerialize(wf);
			}
			if (null == drawing) {
				drawing = HMIDrawingLoader.loadJavolutionXml(wf);
			}
			if (null == drawing) {
				drawing = HMIDrawingLoader.loadXml(wf);
			}

			if (null == drawing) {
				System.out.println("[ " + wf.getName() + " ] window file load fail.");
			} else {
				drawingMap.put(wf.getName(), drawing);
				System.out.println("[ " + wf.getName() + " ] window file load success.");
			}
		}
	}

	public static void main(String[] args) {
		try {
			Project project = new Project(new File("C:/temp/Test").toURI());
			WindowInfoGenerator.createInstance().generate(project);
			System.out.println("Generate end.");

		} catch (ProjectNotLoadedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
