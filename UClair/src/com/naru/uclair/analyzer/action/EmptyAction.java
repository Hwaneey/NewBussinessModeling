package com.naru.uclair.analyzer.action;

import java.awt.event.ActionEvent;

import javax.swing.Action;
import javax.swing.JOptionPane;

import com.naru.uclair.analyzer.Analyzer;

public class EmptyAction extends AbstractCommonAction {

	private static final long serialVersionUID = 1L;

	public EmptyAction(final Analyzer analyzer) {
		super(analyzer);

		this.setEnabled(true);
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		JOptionPane.showMessageDialog(getAnalyzer(), getValue(Action.NAME));
	}

}
