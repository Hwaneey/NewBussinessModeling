package com.naru.uclair.analyzer.analysis.each;

/**
 * 
 * <pre>
 * NAME   : com.naru.uclair.analyzer.analysis.each.EachTagDataCollectionDependResult.java
 * DESC   : 데이터 수집 모델 개별 태그 종속성 분석 결과 클래스.
 * 
 * references : 설계서 NARU-XXX-XXX-XXX
 * 
 * Copyright 2012 NARU Technology All rights reserved
 * 
 * <pre>
 * 
 * @author US Laboratory naruteclab4
 * @since 2012. 7. 4.
 * @version 1.0
 * 
 */
public class EachTagDataCollectionDependResult {
	/**
	 * 데이터 수집 모델 유형.<br/>
	 * - ICollectionConstants 에 정의된 유형.
	 */
	private int collectionType;
	/**
	 * 데이터 수집 모델명.
	 */
	private String collectionName;
	/**
	 * 대상 태그 아이디.
	 */
	private String tagId;
	/**
	 * 노드명.
	 */
	private String nodeName;

	/**
	 * 데이터 수집 유형을 반환한다.<br/>
	 * 
	 * @return collectionType 데이터 수집 유형.
	 */
	public int getCollectionType() {
		return collectionType;
	}

	/**
	 * 데이터 수집 유형을 설정한다.<br/>
	 * 
	 * @param collectionType
	 *            데이터 수집 유형.
	 */
	public void setCollectionType(int collectionType) {
		this.collectionType = collectionType;
	}

	/**
	 * 데이터 수집 모델 명을 반환한다.<br/>
	 * - 기본 수집 모델(데이터 변경, 경보, 가동)은 기본 이름을 반환한다.
	 * 
	 * @return collectionName 데이터 수집 모델명.
	 */
	public String getCollectionName() {
		return collectionName;
	}

	/**
	 * 데이터 수집 모델명을 설정한다.<br/>
	 * - 기본 수집 모델(데이터 변경, 경보, 가동)은 설정이 무시된다.
	 * 
	 * @param collectionName
	 *            데이터 수집 모델명.
	 */
	public void setCollectionName(String collectionName) {
		this.collectionName = collectionName;
	}

	/**
	 * 태그 아이디를 반환한다.<br/>
	 * 
	 * @return tagId 태그 아이디.
	 */
	public String getTagId() {
		return tagId;
	}

	/**
	 * 태그 아이디를 설정한다.<br/>
	 * 
	 * @param tagId
	 *            태그 아이디.
	 */
	public void setTagId(String tagId) {
		this.tagId = tagId;
	}

	/**
	 * 노드명을 반환한다.<br/>
	 * 
	 * @return nodeName 노드명.
	 */
	public String getNodeName() {
		return nodeName;
	}

	/**
	 * 노드명을 설정한다.<br/>
	 * 
	 * @param nodeName
	 *            노드명.
	 */
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

}
