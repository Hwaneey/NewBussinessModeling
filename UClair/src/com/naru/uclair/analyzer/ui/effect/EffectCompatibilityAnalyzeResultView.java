package com.naru.uclair.analyzer.ui.effect;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JScrollPane;
import java.awt.GridBagConstraints;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;

import com.naru.uclair.analyzer.analysis.effect.EffectCompatibilityResult;

/**
 *
 * <pre>
 * NAME   : com.naru.uclair.analyzer.ui.effect.EffectCompatibilityAnalyzeResultView.java
 * DESC   : 객체 효과 양립성 분석 결과 표시 View.
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
public class EffectCompatibilityAnalyzeResultView extends JPanel {

	/**
	 * 객체 직렬화 버전 아이디.
	 */
	private static final long serialVersionUID = 1L;
	private EffectCompatibilityAnalyzeResultTableModel tableModel;

	/**
	 * Create the panel.
	 */
	public EffectCompatibilityAnalyzeResultView() {
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
		
		tableModel = new EffectCompatibilityAnalyzeResultTableModel();
		JTable resultTable = new JTable(tableModel);
		TableRowSorter<EffectCompatibilityAnalyzeResultTableModel> tableRowSorter = 
			new TableRowSorter<EffectCompatibilityAnalyzeResultTableModel>(tableModel);
		resultTable.setRowSorter(tableRowSorter);
		resultTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		TableColumn column = resultTable.getColumnModel().getColumn(0);
		column.setPreferredWidth(150);
		
		column = resultTable.getColumnModel().getColumn(1);
		column.setPreferredWidth(80);
		
		column = resultTable.getColumnModel().getColumn(2);
		column.setPreferredWidth(80);
		
		column = resultTable.getColumnModel().getColumn(3);
		column.setPreferredWidth(150);
		
		for(int index = 4; index < tableModel.getColumnCount(); index++) {
			column = resultTable.getColumnModel().getColumn(index);
			column.setPreferredWidth(75);
		}
		
		resultTableScrollPane.setViewportView(resultTable);
	}

	/**
	 * 
	 * 분석 결과를 화면에 표시하기 윈한 정보를 설정한다.<br/>
	 * - 메소드의 처리 절차 기술.
	 * 
	 * @param resultList 분석 결과.
	 */
	public void setAnalyzeResult(List<EffectCompatibilityResult> resultList) {
		tableModel.setAnalyzeResult(resultList);
	}
}
