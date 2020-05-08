package com.naru.uclair.analyzer.ui.physical;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JRadioButton;

import com.naru.uclair.analyzer.constants.AnalyzerConstants;

import java.awt.Insets;

/**
 *
 * <pre>
 * NAME   : com.naru.uclair.analyzer.ui.physical.PhysicalTagDependAnalyzeDialog.java
 * DESC   : 물리 주소 종속성 분석 Dialog 클래스.
 *
 * references : 설계서 NARU-XXX-XXX-XXX
 *
 * Copyright 2012 NARU Technology All rights reserved
 * <pre>
 *
 * @author US Laboratory naruteclab4
 * @since 2012. 6. 21.
 * @version 1.0
 *
 */
public class PhysicalTagDependAnalyzeDialog extends JDialog implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String ANALYZE_ACTION_COMMAND = "physical.analyze"; //$NON-NLS-1$
	private static final String CANCEL_ACTION_COMMAND = "physical.cancel"; //$NON-NLS-1$

	/**
	 * Create the dialog.
	 */
	public PhysicalTagDependAnalyzeDialog(Frame frame) {
		super(frame, true);
		initialize();
	}
	
	private void initialize() {
		setTitle(AnalyzerConstants.getString("PhysicalTagDependAnalyzeDialog.Title")); //$NON-NLS-1$
		setSize(520, 180);
		setMinimumSize(new Dimension(520, 180));
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		//getContentPane().setLayout(new BorderLayout());
		getContentPane().add(createContentPanel(), BorderLayout.CENTER);
		getContentPane().add(createButtonPanel(), BorderLayout.SOUTH);
	}
	
	private JPanel createContentPanel() {
		JPanel contentPanel = new JPanel();
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{0, 0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		
		JLabel contentLabel = new JLabel(AnalyzerConstants.getString("PhysicalTagDependAnalyzeDialog.Label.Content")); //$NON-NLS-1$
		GridBagConstraints gbc_contentLabel = new GridBagConstraints();
		gbc_contentLabel.gridwidth = 2;
		gbc_contentLabel.anchor = GridBagConstraints.WEST;
		gbc_contentLabel.insets = new Insets(10, 10, 10, 10);
		gbc_contentLabel.gridx = 0;
		gbc_contentLabel.gridy = 0;
		contentPanel.add(contentLabel, gbc_contentLabel);
		
		JRadioButton bothRadioButton = new JRadioButton(AnalyzerConstants.getString("PhysicalTagDependAnalyzeDialog.RadioButton.Both")); //$NON-NLS-1$
		GridBagConstraints gbc_bothRadioButton = new GridBagConstraints();
		gbc_bothRadioButton.insets = new Insets(0, 10, 0, 5);
		gbc_bothRadioButton.anchor = GridBagConstraints.WEST;
		gbc_bothRadioButton.gridx = 0;
		gbc_bothRadioButton.gridy = 1;
		contentPanel.add(bothRadioButton, gbc_bothRadioButton);
		
		JRadioButton addressRadioButton = new JRadioButton(AnalyzerConstants.getString("PhysicalTagDependAnalyzeDialog.RadioButton.Address")); //$NON-NLS-1$
		GridBagConstraints gbc_addressRadioButton = new GridBagConstraints();
		gbc_addressRadioButton.anchor = GridBagConstraints.WEST;
		gbc_addressRadioButton.gridx = 1;
		gbc_addressRadioButton.gridy = 1;
		contentPanel.add(addressRadioButton, gbc_addressRadioButton);
		
		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(bothRadioButton);
		buttonGroup.add(addressRadioButton);
		
		return contentPanel;
	}

	private JPanel createButtonPanel() {
		JPanel buttonPane = new JPanel();
		buttonPane.setBorder(new MatteBorder(1, 0, 0, 0, Color.LIGHT_GRAY));
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		JButton analyzeButton = new JButton(AnalyzerConstants.getString("PhysicalTagDependAnalyzeDialog.Button.Analyze")); //$NON-NLS-1$
		analyzeButton.setPreferredSize(new Dimension(75, 22));
		analyzeButton.setActionCommand(ANALYZE_ACTION_COMMAND);
		analyzeButton.addActionListener(this);
		buttonPane.add(analyzeButton);
		//getRootPane().setDefaultButton(analyzeButton);
		
		JButton cancelButton = new JButton(AnalyzerConstants.getString("PhysicalTagDependAnalyzeDialog.Button.Cancel")); //$NON-NLS-1$
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
