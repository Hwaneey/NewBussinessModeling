package com.naru.uclair.analyzer.ui.physical;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JScrollPane;
import java.awt.GridBagConstraints;
import javax.swing.JTable;
import javax.swing.table.TableRowSorter;

import com.naru.uclair.analyzer.analysis.tag.PhysicalDepend;

/**
 *
 * <pre>
 * NAME   : com.naru.uclair.analyzer.ui.physical.PhysicalTagDependAnalyzeResultView.java
 * DESC   : 물리 주소 종속성 분석 결과 표시 View.
 *
 * references : 설계서 NARU-XXX-XXX-XXX
 *
 * Copyright 2012 NARU Technology All rights reserved
 * <pre>
 *
 * @author US Laboratory naruteclab4
 * @since 2012. 6. 21.
 * @version 1.0
 *
 */
public class PhysicalTagDependAnalyzeResultView extends JPanel {

	/**
	 * 객체 직렬화 버전 아이디.
	 */
	private static final long serialVersionUID = 1L;
	private JTable resultTable;
	PhysicalTagDependAnalyzeResultTableModel tableModel = null;
	private TableRowSorter<PhysicalTagDependAnalyzeResultTableModel> sorter;
	
	/**
	 * Create the panel.
	 */
	public PhysicalTagDependAnalyzeResultView() {
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
		
		tableModel = new PhysicalTagDependAnalyzeResultTableModel();
		sorter = new TableRowSorter<PhysicalTagDependAnalyzeResultTableModel>(tableModel);
		sorter.setModel(tableModel);
		resultTable = new JTable(tableModel);
		resultTable.setRowSorter(sorter);
		resultTableScrollPane.setViewportView(resultTable);
	}

	public void setPhysicalDepend(PhysicalDepend depend) {
		tableModel.setPhysicalDepend(depend);
	}
}
