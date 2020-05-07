package analyzer.ui.physical;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableRowSorter;

import com.jidesoft.document.DocumentComponent;
import com.jidesoft.document.DocumentPane;

import analyzer.analysis.tag.PhysicalDepend;
import analyzer.frame.AnalyzerMainFrame;

/**
 *
 * <pre>
 * NAME   : com.naru.uclair.analyzer.ui.physical.PhysicalTagDependAnalyzeResultView.java
 * DESC   : ���� �ּ� ���Ӽ� �м� ��� ǥ�� View.
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
public class PhysicalTagDependAnalyzeResultView extends JPanel {

	/**
	 * ��ü ����ȭ ���� ���̵�.
	 */
	private static final long serialVersionUID = 1L;
	private JTable resultTable;
	PhysicalTagDependAnalyzeResultTableModel tableModel = null;
	private TableRowSorter<PhysicalTagDependAnalyzeResultTableModel> sorter;
	
	private static DocumentPane _workspacePane =  AnalyzerMainFrame._workspacePane;
	/**
	 * Create the panel.
	 */
	public PhysicalTagDependAnalyzeResultView() {
		initializeUi();
		final DocumentComponent document = new DocumentComponent(initializeUi(), "물리주소 종속성 분석");
        _workspacePane.openDocument(document);
	}

	private JComponent initializeUi() {
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
		return resultTableScrollPane;
	}

	public void setPhysicalDepend(PhysicalDepend depend) {
		tableModel.setPhysicalDepend(depend);
	}
}
