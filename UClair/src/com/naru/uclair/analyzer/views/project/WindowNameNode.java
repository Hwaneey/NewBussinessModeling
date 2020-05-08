package com.naru.uclair.analyzer.views.project;

import java.io.File;

import javax.swing.tree.DefaultMutableTreeNode;


/**
 * <p>
 * Title: iA-Canvas
 * </p>
 * <p>
 * Description: 모니터링 자동화 툴
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company: (주) 나루기술
 * </p>
 * 
 * @author 정해문
 * @version 1.0
 */

public class WindowNameNode extends DefaultMutableTreeNode {

	/**
     * 
     */
	private static final long serialVersionUID = 1L;
	private File windowFile = null;

	public WindowNameNode(final File f) {
		if (f != null) {
			setUserObject(f);
		}
	}

	@Override
	public File getUserObject() {
		return windowFile;
	}

	@Override
	public void setUserObject(final Object obj) {
		if ((obj != null) && (obj instanceof File)) {
			windowFile = (File) obj;
			super.setUserObject(windowFile.getName());
		}
	}
}
