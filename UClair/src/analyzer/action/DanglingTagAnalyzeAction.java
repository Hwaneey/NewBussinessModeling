package analyzer.action;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import analyzer.Analyzer;

public class DanglingTagAnalyzeAction extends AbstractCommonAction implements PropertyChangeListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DanglingTagAnalyzeAction(Analyzer alayzer) {
		super(alayzer);
		setEnabled(true);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("DanglingTagAnalyzeAction 출력");
	}

	@Override
	public void propertyChange(PropertyChangeEvent arg0) {
		// TODO Auto-generated method stub

	}

}
