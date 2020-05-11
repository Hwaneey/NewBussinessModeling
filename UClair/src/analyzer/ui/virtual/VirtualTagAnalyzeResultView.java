package analyzer.ui.virtual;

import java.awt.GridBagConstraints;

import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.jidesoft.document.DocumentComponent;
import com.jidesoft.document.DocumentPane;

import analyzer.constants.AnalyzerConstants;
import analyzer.frame.AnalyzerMainFrame;
import analyzer.analysis.virtual.VirtualDepend;


/************************************************
 * @date	: 2020. 5.07.
 * @책임자 : 박보미
 * @설명  	: 가상태그 종속성 분석 결과 표시 View.
 * @변경이력 	: 
 ************************************************/

public class VirtualTagAnalyzeResultView extends JPanel {


	private static final long serialVersionUID = 1L;
	private JTable resultTable;
	private VirtualTagAnalyzeResultTableModel tableModel;
	private JLabel tagNameLabel;
	public static final String VIRTUAL_TAG_DEPENDENCY_RESULT_VIEW_KEY = AnalyzerConstants
			.getString("AnalyzerEditorFactory.VirtualTag.Key"); //$NON-NLS-1$
	private static DocumentPane _workspacePane =  AnalyzerMainFrame._workspacePane;

	/**
	 * Create the panel.
	 */
	public VirtualTagAnalyzeResultView() {
		
		initializeUi();
		final DocumentComponent document = new DocumentComponent(initializeUi(), VIRTUAL_TAG_DEPENDENCY_RESULT_VIEW_KEY);
	       if (_workspacePane.getDocument(VIRTUAL_TAG_DEPENDENCY_RESULT_VIEW_KEY) != null) {	
				_workspacePane.setActiveDocument(VIRTUAL_TAG_DEPENDENCY_RESULT_VIEW_KEY);
			} else {	
		        _workspacePane.openDocument(document);
		        _workspacePane.setActiveDocument(VIRTUAL_TAG_DEPENDENCY_RESULT_VIEW_KEY);
			}
		
	}
	
	private JTable initializeUi() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		
		JLabel tagTitleLabel = new JLabel(AnalyzerConstants.getString("VirtualTagAnalyzeResultView.Label.SelectedTag")); //$NON-NLS-1$
		GridBagConstraints gbc_tagTitleLabel = new GridBagConstraints();
		gbc_tagTitleLabel.anchor = GridBagConstraints.WEST;
		gbc_tagTitleLabel.insets = new Insets(0, 0, 5, 5);
		gbc_tagTitleLabel.gridx = 0;
		gbc_tagTitleLabel.gridy = 0;
		add(tagTitleLabel, gbc_tagTitleLabel);
		
		JLabel lineLabel = new JLabel("-----"); //$NON-NLS-1$
		GridBagConstraints gbc_lineLabel = new GridBagConstraints();
		gbc_lineLabel.anchor = GridBagConstraints.WEST;
		gbc_lineLabel.insets = new Insets(0, 0, 5, 0);
		gbc_lineLabel.gridx = 1;
		gbc_lineLabel.gridy = 0;
		add(lineLabel, gbc_lineLabel);
		
		tagNameLabel = new JLabel(AnalyzerConstants.getString("VirtualTagAnalyzeResultView.Label.TagName")); //$NON-NLS-1$
		GridBagConstraints gbc_tagNameLabel = new GridBagConstraints();
		gbc_tagNameLabel.fill = GridBagConstraints.HORIZONTAL;
		gbc_tagNameLabel.gridwidth = 3;
		gbc_tagNameLabel.insets = new Insets(0, 10, 10, 10);
		gbc_tagNameLabel.gridx = 0;
		gbc_tagNameLabel.gridy = 1;
		add(tagNameLabel, gbc_tagNameLabel);
		
		JLabel resultTitleLabel = new JLabel(AnalyzerConstants.getString("VirtualTagAnalyzeResultView.Label.AnalyzeResult")); //$NON-NLS-1$
		GridBagConstraints gbc_resultTitleLabel = new GridBagConstraints();
		gbc_resultTitleLabel.anchor = GridBagConstraints.WEST;
		gbc_resultTitleLabel.insets = new Insets(0, 0, 5, 5);
		gbc_resultTitleLabel.gridx = 0;
		gbc_resultTitleLabel.gridy = 2;
		add(resultTitleLabel, gbc_resultTitleLabel);
		
		JLabel lineLabel2 = new JLabel("-----"); //$NON-NLS-1$
		GridBagConstraints gbc_lineLabel2 = new GridBagConstraints();
		gbc_lineLabel2.anchor = GridBagConstraints.WEST;
		gbc_lineLabel2.insets = new Insets(0, 0, 5, 0);
		gbc_lineLabel2.gridx = 1;
		gbc_lineLabel2.gridy = 2;
		add(lineLabel2, gbc_lineLabel2);
		
		JScrollPane resultTableScrollPane = new JScrollPane();
		GridBagConstraints gbc_resultTableScrollPane = new GridBagConstraints();
		gbc_resultTableScrollPane.gridwidth = 3;
		gbc_resultTableScrollPane.fill = GridBagConstraints.BOTH;
		gbc_resultTableScrollPane.gridx = 0;
		gbc_resultTableScrollPane.gridy = 3;
		add(resultTableScrollPane, gbc_resultTableScrollPane);
		
		tableModel = new VirtualTagAnalyzeResultTableModel();
		resultTable = new JTable(tableModel);
		resultTableScrollPane.setViewportView(resultTable);
		
		return resultTable;
	}
	
	public void setVirtualTagDepends(List<VirtualDepend> tagList, String tagKey) {
		tagNameLabel.setText(tagKey);
		tableModel.setVirtualDepends(tagList);
	}

}
