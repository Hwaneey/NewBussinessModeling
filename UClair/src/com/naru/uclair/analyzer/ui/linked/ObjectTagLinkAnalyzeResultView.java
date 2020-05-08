package com.naru.uclair.analyzer.ui.linked;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JScrollPane;
import java.awt.GridBagConstraints;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;

import com.naru.uclair.analyzer.analysis.linked.ObjectTagLinkResult;

/**
 *
 * <pre>
 * NAME   : com.naru.uclair.analyzer.ui.linked.ObjectTagLinkAnalyzeResultView.java
 * DESC   : ��ü �±� ���� �м� ��� ǥ�� View.
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
public class ObjectTagLinkAnalyzeResultView extends JPanel {

	/**
	 * ��ü ����ȭ ���� ���̵�.
	 */
	private static final long serialVersionUID = 1L;
	private ObjectTagLinkAnalyzeResultTableModel tableModel;

	/**
	 * Create the panel.
	 */
	public ObjectTagLinkAnalyzeResultView() {
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
		
		tableModel = new ObjectTagLinkAnalyzeResultTableModel();
		JTable resultTable = new JTable(tableModel);
		TableRowSorter<ObjectTagLinkAnalyzeResultTableModel> tableRowSorter = 
			new TableRowSorter<ObjectTagLinkAnalyzeResultTableModel>(tableModel);
		resultTable.setRowSorter(tableRowSorter);
		resultTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		TableColumn column = resultTable.getColumnModel().getColumn(0);
		column.setPreferredWidth(150);
		
		column = resultTable.getColumnModel().getColumn(1);
		column.setPreferredWidth(80);
		
		for(int index = 2; index < tableModel.getColumnCount(); index++) {
			column = resultTable.getColumnModel().getColumn(index);
			column.setPreferredWidth(250);
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
	public void setAnalyzeResult(List<ObjectTagLinkResult> resultList) {
		tableModel.setAnalyzeResult(resultList);
	}

}
