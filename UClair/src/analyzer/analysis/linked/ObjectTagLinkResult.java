package analyzer.analysis.linked;


/************************************************
 * @date	: 2020. 5.07.
 * @책임자 : 이승환
 * @설명  	: ObjectTagLinkResult
 * @변경이력 	: 
 ************************************************/

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
