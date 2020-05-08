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
 * 객체 태그 연결 정보 분석을 위한 화면 관련 정보를 만드는 클래스.<br/>
 * - 프로젝트 정보의 모든 화면 파일을 로드하여, 관련 정보를 생성한다.
 * 
 * @author ywpark
 * 
 */
public class WindowInfoGenerator {

	/**
	 * 화면 정보 분석기 인스턴스.
	 */
	private static WindowInfoGenerator analyzer;
	/**
	 * 화면 정보 분석을 위한 Uclair 프로젝트 정보.
	 */
	private Project project;
	/**
	 * 화면 파일 위치 정보.
	 */
	private URI windowResourcePath;
	/**
	 * 화면 파일을 구분하기 위한 파일 필터.
	 */
	private FileFilter winFileFilter;
	/**
	 * 분석 대상 프로젝트의 모든 화면 정보.
	 */
	private Map<String, HMIDrawing> drawingMap;
	private Map<String, WindowAnalyzeInfo> windowAnalyzeInfoMap;

	/**
	 * WindowInfoGenerator 생성자.<br/>
	 * - 직접 생성할 수 없도록 한다.
	 * 
	 */
	private WindowInfoGenerator() {
		winFileFilter = new HMIDrawingFileFilter();
		drawingMap = new HashMap<String, HMIDrawing>();
		windowAnalyzeInfoMap = new HashMap<String, WindowAnalyzeInfo>();
	}

	/**
	 * WindowInfoAnalyzer 인스턴스를 생성하여 반환한다.<br/>
	 * -
	 * 
	 * @return 화면 정보 분석기.
	 */
	public static WindowInfoGenerator createInstance() {
		if (null == analyzer) {
			analyzer = new WindowInfoGenerator();
		}
		return analyzer;
	}
	
	/**
	 * WindowInfoGenerator에 사용된 모든 정보 및 분석 대상 정보를 소거한다.<br/>
	 * - {@link #drawingMap} 정보 소거.<br/>
	 * - {@link #windowAnalyzeInfoMap} 정보 소거.
	 */
	public void clean() {
		// 1. 화면 정보 모두 소거.
		Collection<HMIDrawing> drawings = drawingMap.values();
		for(HMIDrawing drawing : drawings) {
			drawing.dispose();
		}
		drawingMap.clear();
		
		// 2. 화면 분석용 정보 모두 소거.
		Collection<WindowAnalyzeInfo> values = windowAnalyzeInfoMap.values();
		for(WindowAnalyzeInfo value : values) {
			value.clean();
		}
		windowAnalyzeInfoMap.clear();
		
		windowResourcePath = null;
		project = null;
	}

	/**
	 * 프로젝트의 화면 정보에 대한 분석 데이터를 생성한다.<br/>
	 * - 1. 화면 정보를 로드한다.<br/>
	 * - 2. 로드된 화면 정보에 대한 분석 데이터를 생성한다.
	 * 
	 * @param project 분석 대상 프로젝트 정보.
	 */
	public void generate(Project project) {
		// 분석 대상 정보 및 기존 정보 클린.
		clean();

		this.project = project;
		this.windowResourcePath = project.getWindowResourcePath();

		// 1. window File load.
		// * 화면 파일 로드는 차후 호출되는 위치가 변경될 수 있다.
		loadFileWindow();

		// 2. generate window file
		Set<String> windowNames = drawingMap.keySet();
		for(String winName : windowNames) {
			// Generate 된 정보 Map 으로 관리.
			WindowAnalyzeInfo analyzeInfo = new WindowAnalyzeInfo(winName, drawingMap.get(winName));
			windowAnalyzeInfoMap.put(analyzeInfo.getWindowName(), analyzeInfo);
		}
	}

	/**
	 * 분석 대상 프로젝트의 화면 리소스 폴더의 모든 화면을 로드하여 저장한다.<br/>
	 * - 화면 로드 실패시 실패 로그를 남기고 계속 로드한다.<br/>
	 * - 로드된 화면은 {@link #drawingMap}에 저장한다.
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
