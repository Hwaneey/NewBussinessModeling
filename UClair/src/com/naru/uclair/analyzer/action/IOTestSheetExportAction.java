package com.naru.uclair.analyzer.action;

import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;

import com.naru.uclair.analyzer.Analyzer;
import com.naru.uclair.analyzer.analysis.io.excel.IOTest;
import com.naru.uclair.analyzer.constants.AnalyzerConstants;

public class IOTestSheetExportAction extends AbstractProjectRelatedAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public IOTestSheetExportAction(Analyzer dev) {
		super(dev);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		Analyzer analyzer = getAnalyzer();

		int returnType = JOptionPane.showConfirmDialog(analyzer,
				AnalyzerConstants
						.getString("IOTestSheetExportAction.confirm.message"),
				AnalyzerConstants
						.getString("IOTestSheetExportAction.confirm.title"),
				JOptionPane.YES_NO_OPTION);
		if (returnType == JOptionPane.YES_OPTION) {
			IOTest testSheet = new IOTest();
			testSheet.analyzer(analyzer.getProject().getTagDictionary());
			testSheet.exportExcel(analyzer);
		}
	}

}
