package com.naru.uclair.analyzer.listener;

import com.naru.uclair.project.Project;

public interface AnalyzerEventListener {

	/**
	 * Developer���� �����Ǵ� Project�� New/Open/Close�� ���� ����� ��� �߻��Ǵ� �̺�Ʈ.
	 * 
	 * @param newProject
	 */
	public void projectChanged(Project newProject);
}
