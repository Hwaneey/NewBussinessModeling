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
 * DESC   : ��ü ȿ�� �縳�� �м� ��� ǥ�� View.
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
public class EffectCompatibilityAnalyzeResultView extends JPanel {

	/**
	 * ��ü ����ȭ ���� ���̵�.
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
	 * �м� ����� ȭ�鿡 ǥ���ϱ� ���� ������ �����Ѵ�.<br/>
	 * - �޼ҵ��� ó�� ���� ���.
	 * 
	 * @param resultList �м� ���.
	 */
	public void setAnalyzeResult(List<EffectCompatibilityResult> resultList) {
		tableModel.setAnalyzeResult(resultList);
	}
}
