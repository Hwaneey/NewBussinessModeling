package analyzer.analysis.effect;

/************************************************
 * @date	: 2020. 5.07.
 * @책임자 : 이승환
 * @설명  	: 효과 양립성 분석 결과 인터페이스.
 * @변경이력 	: 
 ************************************************/

public interface EffectCompatibilityResult {
	/**
	 * 양립성 오류 등급.
	 */
	public static final int PRIORITY_ERROR = 0;
	/**
	 * 양립성 주의 등급.
	 */
	public static final int PRIORITY_WARNING = 1;
	public static final String[] PRIORITY_NAMES = {"ERROR", "WARNING"};
	
	public static final int TYPE_USED_WITH_EMERGE = 0;
	public static final int TYPE_DUPLICATION_DRAG_AND_MOVE = 1;
	public static final int TYPE_DUPLICATION_DRAG_AND_TOUCH = 2;
	public static final int TYPE_TOUCH_COMPATIBILITY = 3;
	public static final String[] TYPE_NAMES = {"Used with emerge", 
		"Duplication of move and drag", 
		"Duplication of drag and touch", 
		"Touch compatibility"};
	
	/**
	 * 
	 * 화면명을 반환한다.<br/>
	 * 
	 * @return 화면명.
	 */
	public String getWindowName();
	/**
	 * 
	 * 화면명을 설정한다.<br/>
	 * 
	 * @param windowName 화면명.
	 */
	public void setWindowName(String windowName);
	/**
	 * 
	 * 화면 객체 아이디를 반환한다.<br/>
	 * 
	 * @return 화면 객체 아이디.
	 */
	public String getFigureId();
	/**
	 * 
	 * 화면 객체 아이디를 설정한다.<br/>
	 * 
	 * @param id 화면 객체 아이디.
	 */
	public void setFigureId(String id);
	/**
	 * 
	 * 양립성 등급을 반환한다.<br/>
	 * 
	 * @return 양립성 등급.(EffectCompatibilityResult.PRIORITY_XXXX)
	 */
	public int getPriority();
	/**
	 * 
	 * 양립성 등급을 설정한다.<br/>
	 * 
	 * @param priorty 양립성 등급.(EffectCompatibilityResult.PRIORITY_XXXX)
	 */
	public void setPriority(int priorty);
	/**
	 * 
	 * 효과 양립 유형을 반환한다.<br/>
	 * 
	 * @return 효과 양립 유형.
	 */
	public int getCompatibilityType();
	/**
	 * 
	 * 효과 양립 유형을 설정한다.<br/>
	 * 
	 * @param type 효과 양립 유형.
	 */
	public void setCompatibilityType(int type);
	
	/**
	 * 
	 * 출몰 사용 여부를 반환한다.<br/>
	 * 
	 * @return 사용 여부.
	 */
	public boolean useEmerge();
	/**
	 * 
	 * 깜박임 사용 여부를 반환한다.<br/>
	 * 
	 * @return 사용 여부.
	 */
	public boolean useBlink();
	/**
	 * 
	 * 이동 사용 여부를 반환한다.<br/>
	 * 
	 * @return 사용 여부.
	 */
	public boolean useMove();
	/**
	 * 
	 * 끌기 사용 여부를 반환한다.<br/>
	 * 
	 * @return 사용 여부.
	 */
	public boolean useDrag();
	/**
	 * 
	 * 태그 표시 사용 여부를 반환한다.<br/>
	 * 
	 * @return 사용 여부.
	 */
	public boolean useTagDisplay();
	/**
	 * 
	 * 터치 사용 여부를 반환한다.<br/>
	 * 
	 * @return 사용 여부.
	 */
	public boolean useTouch();
}
