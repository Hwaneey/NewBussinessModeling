package analyzer.action;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.Action;

import analyzer.Analyzer;

public class ProjectCloseAction  extends AbstractCommonAction implements PropertyChangeListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public ProjectCloseAction(Analyzer alayzer) {
		super(alayzer);
		setEnabled(true);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		System.out.println("ProjectCloseAction �������");
		getAnalyzer().closeProject();
		System.out.println("ProjectCloseAction ���᳡");
	}


	@Override
	public void propertyChange(PropertyChangeEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
