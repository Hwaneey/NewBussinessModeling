package com.naru.uclair.analyzer.ui.event;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JScrollPane;
import java.awt.GridBagConstraints;
import javax.swing.JTable;

import com.naru.uclair.analyzer.analysis.event.EventDepend;

/**
 *
 * <pre>
 * NAME   : com.naru.uclair.analyzer.ui.event.EventDependAnalyzeResultView.java
 * DESC   : �̺�Ʈ ���Ӽ� �м� ��� ǥ�� View.
 *
 * references : ���輭 NARU-XXX-XXX-XXX
 *
 * Copyright 2012 NARU Technology All rights reserved
 * <pre>
 *
 * @author US Laboratory naruteclab4
 * @since 2012. 6. 21.
 * @version 1.0
 *
 */
public class EventDependAnalyzeResultView extends JPanel {

	/**
	 * ��ü ����ȭ ���� ���̵�.
	 */
	private static final long serialVersionUID = 1L;
	private JTable resultTable;
	private EventDependAnalyzeResultTableModel tableModel;
	
	/**
	 * Create the panel.
	 */
	public EventDependAnalyzeResultView() {
		initializeUi();
	}

	private void initializeUi() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JScrollPane resultTableScrollPane = new JScrollPane();
		GridBagConstraints gbc_resultTableScrollPane = new GridBagConstraints();
		gbc_resultTableScrollPane.fill = GridBagConstraints.BOTH;
		gbc_resultTableScrollPane.gridx = 0;
		gbc_resultTableScrollPane.gridy = 0;
		add(resultTableScrollPane, gbc_resultTableScrollPane);
		
		tableModel = new EventDependAnalyzeResultTableModel();
		resultTable = new JTable(tableModel);
		resultTableScrollPane.setViewportView(resultTable);
	}
	
	public void setEventDepend(EventDepend depend) {
		tableModel.setEventDepend(depend);
	}
}
