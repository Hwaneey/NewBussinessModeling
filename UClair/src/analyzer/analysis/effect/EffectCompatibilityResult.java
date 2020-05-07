package analyzer.analysis.effect;

/**
 *
 * <pre>
 * NAME   : com.naru.uclair.analyzer.analysis.effect.EffectCompatibilityResult.java
 * DESC   : ȿ�� �縳�� �м� ��� �������̽�.
 *
 * references : ���輭 NARU-XXX-XXX-XXX
 *
 * Copyright 2012 NARU Technology All rights reserved
 * <pre>
 *
 * @author US Laboratory naruteclab4
 * @since 2012. 6. 29.
 * @version 1.0
 *
 */
public interface EffectCompatibilityResult {
	/**
	 * �縳�� ���� ���.
	 */
	public static final int PRIORITY_ERROR = 0;
	/**
	 * �縳�� ���� ���.
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
	 * ȭ����� ��ȯ�Ѵ�.<br/>
	 * 
	 * @return ȭ���.
	 */
	public String getWindowName();
	/**
	 * 
	 * ȭ����� �����Ѵ�.<br/>
	 * 
	 * @param windowName ȭ���.
	 */
	public void setWindowName(String windowName);
	/**
	 * 
	 * ȭ�� ��ü ���̵� ��ȯ�Ѵ�.<br/>
	 * 
	 * @return ȭ�� ��ü ���̵�.
	 */
	public String getFigureId();
	/**
	 * 
	 * ȭ�� ��ü ���̵� �����Ѵ�.<br/>
	 * 
	 * @param id ȭ�� ��ü ���̵�.
	 */
	public void setFigureId(String id);
	/**
	 * 
	 * �縳�� ����� ��ȯ�Ѵ�.<br/>
	 * 
	 * @return �縳�� ���.(EffectCompatibilityResult.PRIORITY_XXXX)
	 */
	public int getPriority();
	/**
	 * 
	 * �縳�� ����� �����Ѵ�.<br/>
	 * 
	 * @param priorty �縳�� ���.(EffectCompatibilityResult.PRIORITY_XXXX)
	 */
	public void setPriority(int priorty);
	/**
	 * 
	 * ȿ�� �縳 ������ ��ȯ�Ѵ�.<br/>
	 * 
	 * @return ȿ�� �縳 ����.
	 */
	public int getCompatibilityType();
	/**
	 * 
	 * ȿ�� �縳 ������ �����Ѵ�.<br/>
	 * 
	 * @param type ȿ�� �縳 ����.
	 */
	public void setCompatibilityType(int type);
	
	/**
	 * 
	 * ��� ��� ���θ� ��ȯ�Ѵ�.<br/>
	 * 
	 * @return ��� ����.
	 */
	public boolean useEmerge();
	/**
	 * 
	 * ������ ��� ���θ� ��ȯ�Ѵ�.<br/>
	 * 
	 * @return ��� ����.
	 */
	public boolean useBlink();
	/**
	 * 
	 * �̵� ��� ���θ� ��ȯ�Ѵ�.<br/>
	 * 
	 * @return ��� ����.
	 */
	public boolean useMove();
	/**
	 * 
	 * ���� ��� ���θ� ��ȯ�Ѵ�.<br/>
	 * 
	 * @return ��� ����.
	 */
	public boolean useDrag();
	/**
	 * 
	 * �±� ǥ�� ��� ���θ� ��ȯ�Ѵ�.<br/>
	 * 
	 * @return ��� ����.
	 */
	public boolean useTagDisplay();
	/**
	 * 
	 * ��ġ ��� ���θ� ��ȯ�Ѵ�.<br/>
	 * 
	 * @return ��� ����.
	 */
	public boolean useTouch();
}
