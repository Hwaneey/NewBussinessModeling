package analyzer.ui.dangling;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

import analyzer.constants.AnalyzerConstants;

/**
*
* <pre>
* NAME   : com.naru.uclair.analyzer.ui.dangling.DanglingTagAnalzeDialog.java
* DESC   : 종속되지 않는 태그 분석 Dialog 클래스.
*
* references : 설계서 NARU-XXX-XXX-XXX
*
* Copyright 2012 NARU Technology All rights reserved
* <pre>
*
* @author US Laboratory naruteclab4
* @since 2012. 6. 20.
* @version 1.0
*
*/
public class DanglingTagAnalzeDialog extends JDialog implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String ANALYZE_ACTION_COMMAND = "dangling.analyze"; //$NON-NLS-1$
	private static final String CANCEL_ACTION_COMMAND = "dangling.cancel"; //$NON-NLS-1$
	
	/**
	 * Create the dialog.
	 */
	public DanglingTagAnalzeDialog(Frame frame) {
		super(frame, true);
		initialize();
	}
	
	private void initialize() {
		setTitle(AnalyzerConstants.getString("DanglingTagAnalzeDialog.Title")); //$NON-NLS-1$
		setSize(500, 180);
		setMinimumSize(new Dimension(500, 180));
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		//getContentPane().setLayout(new BorderLayout());
		getContentPane().add(createContentPanel(), BorderLayout.CENTER);
		getContentPane().add(createButtonPanel(), BorderLayout.SOUTH);
	}

	private JPanel createContentPanel() {
		JPanel contentPanel = new JPanel();
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{0, 0, 0, 0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		
		JLabel contentLabel = new JLabel(AnalyzerConstants.getString("DanglingTagAnalzeDialog.Label.Content")); //$NON-NLS-1$
		GridBagConstraints gbc_contentLabel = new GridBagConstraints();
		gbc_contentLabel.gridwidth = 4;
		gbc_contentLabel.insets = new Insets(10, 10, 10, 5);
		gbc_contentLabel.anchor = GridBagConstraints.WEST;
		gbc_contentLabel.gridx = 0;
		gbc_contentLabel.gridy = 0;
		contentPanel.add(contentLabel, gbc_contentLabel);
		
		JCheckBox allCheckBox = new JCheckBox(AnalyzerConstants.getString("DanglingTagAnalzeDialog.CheckBox.All")); //$NON-NLS-1$
		GridBagConstraints gbc_allCheckBox = new GridBagConstraints();
		gbc_allCheckBox.insets = new Insets(0, 10, 0, 5);
		gbc_allCheckBox.anchor = GridBagConstraints.WEST;
		gbc_allCheckBox.gridx = 0;
		gbc_allCheckBox.gridy = 1;
		contentPanel.add(allCheckBox, gbc_allCheckBox);
		
		JCheckBox eventDicCheckBox = new JCheckBox(AnalyzerConstants.getString("DanglingTagAnalzeDialog.CheckBox.EventDic")); //$NON-NLS-1$
		GridBagConstraints gbc_eventDicCheckBox = new GridBagConstraints();
		gbc_eventDicCheckBox.insets = new Insets(0, 0, 0, 5);
		gbc_eventDicCheckBox.anchor = GridBagConstraints.WEST;
		gbc_eventDicCheckBox.gridx = 1;
		gbc_eventDicCheckBox.gridy = 1;
		contentPanel.add(eventDicCheckBox, gbc_eventDicCheckBox);
		
		JCheckBox scriptDicCheckBox = new JCheckBox(AnalyzerConstants.getString("DanglingTagAnalzeDialog.CheckBox.ScriptDic")); //$NON-NLS-1$
		GridBagConstraints gbc_scriptDicCheckBox = new GridBagConstraints();
		gbc_scriptDicCheckBox.insets = new Insets(0, 0, 0, 5);
		gbc_scriptDicCheckBox.anchor = GridBagConstraints.WEST;
		gbc_scriptDicCheckBox.gridx = 2;
		gbc_scriptDicCheckBox.gridy = 1;
		contentPanel.add(scriptDicCheckBox, gbc_scriptDicCheckBox);
		
		JCheckBox windowCheckBox = new JCheckBox(AnalyzerConstants.getString("DanglingTagAnalzeDialog.CheckBox.Window")); //$NON-NLS-1$
		GridBagConstraints gbc_windowCheckBox = new GridBagConstraints();
		gbc_windowCheckBox.anchor = GridBagConstraints.WEST;
		gbc_windowCheckBox.gridx = 3;
		gbc_windowCheckBox.gridy = 1;
		contentPanel.add(windowCheckBox, gbc_windowCheckBox);
		
		return contentPanel;
	}
	
	private JPanel createButtonPanel() {
		JPanel buttonPane = new JPanel();
		buttonPane.setBorder(new MatteBorder(1, 0, 0, 0, Color.LIGHT_GRAY));
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		JButton analyzeButton = new JButton(AnalyzerConstants.getString("DanglingTagAnalzeDialog.Button.Analyze")); //$NON-NLS-1$
		analyzeButton.setPreferredSize(new Dimension(75, 22));
		analyzeButton.setActionCommand(ANALYZE_ACTION_COMMAND);
		analyzeButton.addActionListener(this);
		buttonPane.add(analyzeButton);
		//getRootPane().setDefaultButton(analyzeButton);
		
		JButton cancelButton = new JButton(AnalyzerConstants.getString("DanglingTagAnalzeDialog.Button.Cancel")); //$NON-NLS-1$
		cancelButton.setPreferredSize(new Dimension(75, 22));
		cancelButton.setActionCommand(CANCEL_ACTION_COMMAND);
		cancelButton.addActionListener(this);
		buttonPane.add(cancelButton);
		return buttonPane;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if(ANALYZE_ACTION_COMMAND.equals(cmd)) {
			
		}
		else if(CANCEL_ACTION_COMMAND.equals(cmd)) {
			dispose();
		}
	}
}
