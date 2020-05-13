package analyzer.ui.script;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import analyzer.analysis.script.ScriptSyntaxAnalysisResult;
import analyzer.constants.AnalyzerConstants;
import analyzer.util.OpenView;

/************************************************
 * @date	: 2020. 5.07.
 * @책임자 : 장성현
 * @설명  	:  스크립트 검증 결과 표시 View.
 * @변경이력 	: 
 ************************************************/

public class ScriptSyntaxAnalyzeResultView extends JPanel {

	/**
	 * 객체 직렬화 버전 아이디.
	 */
	private static final long serialVersionUID = 1L;
	private JTable resultTable;
	private ScriptSyntaxAnalyzeResultTableModel tableModel;
	public static final String SCRIPT_SYNTAX_RESULT_VIEW_KEY = AnalyzerConstants
			.getString("AnalyzerEditorFactory.ScriptSyntax.Key"); //$NON-NLS-1$
	/**
	 * Create the panel.
	 */
	public ScriptSyntaxAnalyzeResultView() {
		OpenView resultview = new OpenView();
		resultview.ResultView(initializeUi(), SCRIPT_SYNTAX_RESULT_VIEW_KEY);
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
		
		tableModel = new ScriptSyntaxAnalyzeResultTableModel();
		resultTable = new JTable(tableModel);
		resultTableScrollPane.setViewportView(resultTable);
		return resultTableScrollPane;
	}
	
	/**
	 * 
	 * 분석 결과를 화면에 표시하기 윈한 정보를 설정한다.<br/>
	 * - 메소드의 처리 절차 기술.
	 * 
	 * @param resultList 분석 결과.
	 */
	public void setAnalyzeResult(List<ScriptSyntaxAnalysisResult> resultList) {
		tableModel.setAnalyzeResult(resultList);
	}
}
