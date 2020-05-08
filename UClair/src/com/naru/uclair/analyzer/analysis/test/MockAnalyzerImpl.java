package com.naru.uclair.analyzer.analysis.test;

import com.naru.common.NaruAssert;
import com.naru.uclair.project.Project;
import com.naru.uclair.project.TagDictionary;

/**
 * 최초 개발을 하기 위한 테스트용 Mock 클래스
 * 기본적인 모든 분석에 대한 분석 실행자 클래스
 * 최초 생성치 프로젝트에 대한 전체정보를 가지고 있어야 한다.
 * 1. 프로젝트 정보 요약 
 * 2. 전체태그 비종속성 분석
 * 3. 개별태그 종속성 분석
 * 4. 가상태그 종속성 분석
 * 5. 물리주소 종속성 분석
 * 6. 객체 태그 연결 정보 분석
 * 7. 이벤트 종속성 분석
 * 8. 계산스크립트 검증 분석
 * 9. 객체 효과양립성 분석
 * @author 김기태
 */
public class MockAnalyzerImpl {
	/** 
	 * 프로젝트 정보
	 */
	private Project project;
	
	private TagDictionary tagDictionary;
	
	private static MockAnalyzerImpl analyzerImpl;
	/**
	 * 기본 생성자
	 */
	private MockAnalyzerImpl() {
		
	}
	
	public static MockAnalyzerImpl getInstance() {
		NaruAssert.isNotNull(analyzerImpl);
		if(analyzerImpl == null) {
			analyzerImpl = new MockAnalyzerImpl();
		}
		return analyzerImpl;
	}
	
	/**
	 * 프로젝트를 로딩할 때 다시 재설정을 한다. 
	 * @param project
	 */
	public void setProject(Project project) {
		this.project = project;
		tagDictionary = this.project.getTagDictionary();
	}
	
	/**
	 * 1. 객체 프로젝트 정보 요약 데이터를 문자열로 반환한다.
	 */
	public String getProjectAbstractResult() {
		String tempMessage = "Test Mock Project";
		return tempMessage;
	}
	
	/**
	 * 2. 전체태그 비종속성 분석을 실행한다.
	 * 옵션(0:전체태그, 1:이벤트사전, 2:사용자함수, 3:화면
	 * 결과 데이터는 Map 데이터 (Key : 태그명, Value : Tag)
	 * @param Option
	 * @return
	 */
	public Object allDanglingTagAnalysis(int Option) {
		return null;
	}
	
	/**
	 * 3. 개별태그가 전체 프로젝트에 사용되어진 부분의 종속성 검사를 실행한다.
	 * 결과 데이터는  List 데이터 List<EachTagDependuny>
	 * @param tagkey
	 * @return
	 */
	public Object eachTagDependunyAnalysis(String tagkey) {
		return null;
	}

	/**
	 * 4. 개별태그가 전체 프로젝트에 사용되어진 부분의 종속성 검사한 상태바에 표시할 개수를 반환하다.
	 * 결과 데이터는  List 데이터 List<int>
	 * @param tagkey
	 * @return
	 */
	public Object eachTagDependunyAnalysisCount(String tagkey) {
		return null;
	}
	
	/**
	 * 5. 가상태그에 존재하는 종속되는 물리태그를 분석하여 반환한다.
	 * 결과 데이터는 물리태그 종속결과를 리턴한다.
	 * 결과 -> Map 데이터 (Key : 태그명, Value : Tag)
	 * @param tagkey (선택된 가상태그명)
	 * @return
	 */
	public Object virtualTagDependunyAnalysis(String tagkey) {
		return null;
	}
	
	/**
	 * 6. 물리주소 종속성 태그조회
	 * 물리주소가 중복되는 태그를 분석하여 옵션에 따라 분석결과를 반환한다.
	 * 옵션 (0: 디바이스및 주소가 모두 같은 경우, 1: 주소만 같은경우)
	 * 결과 데이터 => Map 데이터 (Key : 태그주소, Value : List<Tag>) 
	 * @param option
	 * @return
	 */
	public Object physicalTagDependunyAnalysis(int option) {
		return null;
	}
	
	/**
	 * 7. 객체태그 연결정보 분석 
	 * 화면의 객체들이 사용한 효과의 태그목록을 검사하여 결과를 반환한다.
	 * 결과 데이터 => Map 데이터 (Key : Object명, Value : List<String>)
	 * @param windowName (화면명)
	 * @return
	 */
	public Object objectAndTagLinkAnalysis(String windowName) {
		return null;
	}
	
	/**
	 * 8. 이벤트 종속성 분석
	 * 가상태그에 영향을 주는 모든 실(물리)태그를 검사하여 결과를 반환한다.
	 * 결과데이터 => Map 데이터 (Key : 이벤트명, Value : List<String>)
	 * value => <이벤트명, 조건태그, 보류태그, 종속태그리스트>
	 * @return
	 */
	public Object eventDependunyAnalysis() {
		return null;
	}
	
	/**
	 * 9. 스크립트 구문 분석
	 * 모든 스크립트의 구문을 검증한다.
	 * 결과데이터 => Map 데이터 (Key : 스크립트명, Value : String[스크립트 결과 문자열])
	 * @return
	 */
	public Object scriptSyntaxAnalysis() {
		return null;
	}
	
	/**
	 * 10. 객체 이펙트 양립성 분석
	 * 객체 효과에 대한 양립할 수 없는 효과에대한 검증 결과를 반환한다.
	 * 결과데이터 => Map 데이터 (Key : 객체명, Value : List<String>[객체효과존재유무])
	 * @param windowName
	 * @return
	 */
	public Object objectEffectCompatibilityAnalysis(String windowName) {
		return null;
	}
}