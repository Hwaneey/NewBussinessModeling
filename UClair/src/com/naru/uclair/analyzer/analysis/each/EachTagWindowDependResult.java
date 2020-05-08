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
 * �����̷� :
 *    �� ¥ 	  �� �� 		     ���� ����
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
 * references : ���輭 NARU-XXX-XXX-XXX
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
	 * �±� ���̵� ��ȯ�Ѵ�.<br/>
	 * 
	 * @return tagId �±� ���̵�.
	 */
	public String getTagId() {
		return tagId;
	}
	/**
	 * �±� ���̵� �����Ѵ�.<br/>
	 * 
	 * @param tagId �±� ���̵�.
	 */
	public void setTagId(String tagId) {
		this.tagId = tagId;
	}
	/**
	 * ȭ����� ��ȯ�Ѵ�.<br/>
	 * 
	 * @return windowName ȭ���.
	 */
	public String getWindowName() {
		return windowName;
	}
	/**
	 * ȭ����� �����Ѵ�.<br/>
	 * 
	 * @param windowName ȭ���.
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
