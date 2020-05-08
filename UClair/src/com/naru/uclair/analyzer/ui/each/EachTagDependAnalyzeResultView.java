package com.naru.uclair.analyzer.ui.each;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JScrollPane;
import java.awt.GridBagConstraints;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.TableRowSorter;

import com.naru.uclair.analyzer.analysis.each.EachTagDependResult;

/**
 *
 * <pre>
 * NAME   : com.naru.uclair.analyzer.ui.each.EachTagDependAnalyzeResultView.java
 * DESC   : 개별 태그 종속성 분석 결과 표시 View.
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
public class EachTagDependAnalyzeResultView extends JPanel {

	/**
	 * 객체 직렬화 버전 아이디.
	 */
	private static final long serialVersionUID = 1L;
	private EachTagDependAnalyzeResultTableModel tableModel;

	/**
	 * Create the panel.
	 */
	public EachTagDependAnalyzeResultView() {
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
		
		tableModel = new EachTagDependAnalyzeResultTableModel();
		JTable resultTable = new JTable(tableModel);
		TableRowSorter<EachTagDependAnalyzeResultTableModel> tableRowSorter = 
			new TableRowSorter<EachTagDependAnalyzeResultTableModel>(tableModel);
		resultTable.setRowSorter(tableRowSorter);
		resultTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		resultTableScrollPane.setViewportView(resultTable);
	}

	/**
	 * 
	 * 분석 결과를 화면에 표시하기 윈한 정보를 설정한다.<br/>
	 * - 메소드의 처리 절차 기술.
	 * 
	 * @param resultList 분석 결과.
	 */
	public void setAnalyzeResult(List<EachTagDependResult> resultList) {
		tableModel.setAnalyzeResult(resultList);
	}
}
