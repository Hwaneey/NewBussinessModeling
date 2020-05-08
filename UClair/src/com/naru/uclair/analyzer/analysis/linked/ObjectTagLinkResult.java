package com.naru.uclair.analyzer.analysis.linked;

/**
 *
 * <pre>
 * NAME   : 
 * DESC   : 
 *
 * references : ¼³°è¼­ NARU-XXX-XXX-XXX
 *
 * Copyright 2012 NARU Technology All rights reserved
 * <pre>
 *
 * @author US Laboratory naruteclab4
 * @since 2012. 6. 27.
 * @version 1.0
 *
 */
public interface ObjectTagLinkResult {
	
	public String getWindowName();
	
	public void setWindowName(String windowName);
	
	public String getFigureId();
	
	public void setFigureId(String figureid);
	
	public String getEmergeTag();
	
	public String getBlinkTag();
	
	public String getHorizontalMoveTag();
	
	public String getVerticalMoveTag();
	
	public String getHorizontalDragTag();
	
	public String getVerticalDragTag();
	
	public String getHorizontalFillTag();
	
	public String getVerticalFillTag();
	
	public String getColorChangeTag();
	
	public String getTagDisplayTag();
	
	public String getTouchTag();

}
