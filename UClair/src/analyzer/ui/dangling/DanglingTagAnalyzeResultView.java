package analyzer.ui.dangling;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;

import com.jidesoft.document.DocumentComponent;
import com.jidesoft.document.DocumentPane;

import analyzer.analysis.dangling.DanglingTagResult;
import analyzer.frame.AnalyzerMainFrame;


/**
 *
 * <pre>
 * NAME   : com.naru.uclair.analyzer.ui.dangling.DanglingTagAnalyzeResultView.java
 * DESC   : �������� �ʴ� �±� �м� ��� ǥ�� View.
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
public class DanglingTagAnalyzeResultView extends JPanel {

	/**
	 * ��ü ����ȭ ���� ���̵�.
	 */
	private static final long serialVersionUID = 1L;
	private DanglingTagAnalyzeResultTableModel tableModel;

	private static DocumentPane _workspacePane =  AnalyzerMainFrame._workspacePane;
	/**
	 * Create the panel.
	 */
	public DanglingTagAnalyzeResultView() {
		initializeUi();
		final DocumentComponent document = new DocumentComponent(initializeUi(), "존재하지 않는 태그 분석");
        _workspacePane.openDocument(document);
        
	}
	
	private JComponent initializeUi() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 0, 0, 0);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		add(scrollPane, gbc_scrollPane);
		
		tableModel = new DanglingTagAnalyzeResultTableModel();
		JTable resultTable = new JTable(tableModel);
		TableRowSorter<DanglingTagAnalyzeResultTableModel> tableRowSorter = new TableRowSorter<DanglingTagAnalyzeResultTableModel>(tableModel);
		resultTable.setRowSorter(tableRowSorter);
		resultTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		TableColumn column = resultTable.getColumnModel().getColumn(0);
		column.setPreferredWidth(250);
		
		column = resultTable.getColumnModel().getColumn(1);
		column.setPreferredWidth(150);
		
		column = resultTable.getColumnModel().getColumn(2);
		column.setPreferredWidth(800);
		
		scrollPane.setViewportView(resultTable);
		
		JLabel statusLabel = new JLabel("전체태그 : XX개, 실 태그 : XX개, 가상태그 : XX개, 아날로그 태그 : XX개, 문자열 태그 : XX개");
		GridBagConstraints gbc_statusLabel = new GridBagConstraints();
		gbc_statusLabel.anchor = GridBagConstraints.WEST;
		gbc_statusLabel.gridx = 0;
		gbc_statusLabel.gridy = 1;
		add(statusLabel, gbc_statusLabel);
		statusLabel.setVisible(false);
		
		return scrollPane;
	}
	
	/**
	 * 
	 * �м� ����� ȭ�鿡 ǥ���ϱ� ���� ������ �����Ѵ�.<br/>
	 * - �޼ҵ��� ó�� ���� ���.
	 * 
	 * @param resultList �м� ���.
	 */
	public void setAnalyzeResult(List<DanglingTagResult> resultList) {
		tableModel.setAnalyzeResult(resultList);
	}

}
