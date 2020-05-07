package analyzer.ui.event;

import javax.swing.JComponent;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JScrollPane;
import java.awt.GridBagConstraints;
import javax.swing.JTable;

import com.jidesoft.document.DocumentComponent;
import com.jidesoft.document.DocumentPane;

import analyzer.analysis.event.EventDepend;
import analyzer.frame.AnalyzerMainFrame;

/** @date	: 2020. 5.07.
* @책임자 : 지한별
* @설명  	: 이벤트 종속성 분석 결과 표시 View.
* @변경이력 	: **/

public class EventDependAnalyzeResultView extends JPanel {

	/**
	 * 객체 직렬화 버전 아이디.
	 */
	private static final long serialVersionUID = 1L;
	private JTable resultTable;
	private EventDependAnalyzeResultTableModel tableModel;
	private static DocumentPane _workspacePane =  AnalyzerMainFrame._workspacePane;
	/**
	 * Create the panel.
	 */
	public EventDependAnalyzeResultView() {
		final DocumentComponent document = new DocumentComponent(initializeUi(), "이벤트 종속성 태그 분석");
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
		
		tableModel = new EventDependAnalyzeResultTableModel();
		resultTable = new JTable(tableModel);
		resultTableScrollPane.setViewportView(resultTable);
		
		return resultTableScrollPane;
	}
	
	public void setEventDepend(EventDepend depend) {
		tableModel.setEventDepend(depend);
	}
}
