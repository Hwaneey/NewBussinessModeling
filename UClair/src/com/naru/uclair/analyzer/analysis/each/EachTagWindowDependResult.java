/***************************************************************
 *	
 *			     BAD SMELL MORNITORING SYSTEM
 *
 *					NARU Technology Co., Ltd.
 *			      Ubiquitous System Laboratory
 *
 *		All rights reserved. No part of this publication may be
 *		reproduced, stored in a retrieval system or transmitted
 *		in any form or by any means - electronic, mechanical,
 *		photocopying, recording, or otherwise, without the prior
 *		written permission of NARU Technology Co., Ltd.
 *
 ****************************************************************
 * 수정이력 :
 *    날 짜 	  성 명 		     수정 내용
 * ---------	---------	--------------------------------
 * 2012. 7. 4.	naruteclab4		Initial Release
 * 
 ***************************************************************/
package com.naru.uclair.analyzer.analysis.each;

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
 * @since 2012. 7. 4.
 * @version 1.0
 *
 */
public class EachTagWindowDependResult {
	
	private String tagId;
	private String windowName;
	private int type;
	private String figureId;
	
	public static int TYPE_WINDOW = 0;
	public static int TYPE_FIGURE = 1;
	
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
	 * @param tagId 태그 아이디.
	 */
	public void setTagId(String tagId) {
		this.tagId = tagId;
	}
	/**
	 * 화면명을 반환한다.<br/>
	 * 
	 * @return windowName 화면명.
	 */
	public String getWindowName() {
		return windowName;
	}
	/**
	 * 화면명을 설정한다.<br/>
	 * 
	 * @param windowName 화면명.
	 */
	public void setWindowName(String windowName) {
		this.windowName = windowName;
	}
	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(int type) {
		this.type = type;
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

}
