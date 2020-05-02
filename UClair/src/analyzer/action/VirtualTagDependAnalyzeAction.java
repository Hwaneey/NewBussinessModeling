package analyzer.action;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import analyzer.Analyzer;

public class VirtualTagDependAnalyzeAction extends AbstractCommonAction implements PropertyChangeListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public VirtualTagDependAnalyzeAction(Analyzer alayzer) {
		super(alayzer);
		setEnabled(true);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		System.out.println("VIRTUAL_TAG_DEPENDENCY_ANALYSIS 출력");

	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub

	}

}
