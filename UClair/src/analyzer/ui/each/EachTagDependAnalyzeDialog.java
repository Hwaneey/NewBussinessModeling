package analyzer.ui.each;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JScrollPane;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTree;

import analyzer.constants.AnalyzerConstants;

/**
*
* <pre>
* NAME   : com.naru.uclair.analyzer.ui.each.EachTagDependAnalyzeDialog.java
* DESC   : 개별 태그 종속성 분석 Dialog 클래스.
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
public class EachTagDependAnalyzeDialog extends JDialog implements ActionListener {

	/**
	 * 객체 직렬화 버전 아이디.
	 */
	private static final long serialVersionUID = 1L;
	private static final String ANALYZE_ACTION_COMMAND = "each.tag.analyze"; //$NON-NLS-1$
	private static final String CANCEL_ACTION_COMMAND = "each.tag.cancel"; //$NON-NLS-1$

	/**
	 * Create the dialog.
	 */
	public EachTagDependAnalyzeDialog(Frame frame) {
		super(frame, true);
		initialize();
	}
	
	private void initialize() {
		setTitle(AnalyzerConstants.getString("EachTagDependAnalyzeDialog.Title")); //$NON-NLS-1$
		setSize(500, 300);
		setMinimumSize(new Dimension(500, 300));
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		//getContentPane().setLayout(new BorderLayout());
		getContentPane().add(createContentPanel(), BorderLayout.CENTER);
		getContentPane().add(createButtonPanel(), BorderLayout.SOUTH);
	}
	
	private JPanel createContentPanel() {
		JPanel contentPanel = new JPanel();
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		
		JLabel contentLabel = new JLabel(AnalyzerConstants.getString("EachTagDependAnalyzeDialog.Label.Content")); //$NON-NLS-1$
		GridBagConstraints gbc_contentLabel = new GridBagConstraints();
		gbc_contentLabel.anchor = GridBagConstraints.WEST;
		gbc_contentLabel.insets = new Insets(10, 10, 10, 0);
		gbc_contentLabel.gridx = 0;
		gbc_contentLabel.gridy = 0;
		contentPanel.add(contentLabel, gbc_contentLabel);
		
		JScrollPane tagListScrollPane = new JScrollPane();
		GridBagConstraints gbc_tagListScrollPane = new GridBagConstraints();
		gbc_tagListScrollPane.fill = GridBagConstraints.BOTH;
		gbc_tagListScrollPane.insets = new Insets(0, 0, 10, 0);
		gbc_tagListScrollPane.gridx = 0;
		gbc_tagListScrollPane.gridy = 1;
		contentPanel.add(tagListScrollPane, gbc_tagListScrollPane);
		
		JTree tagDicTree = new JTree();
		tagListScrollPane.setViewportView(tagDicTree);
		
		return contentPanel;
	}
	
	private JPanel createButtonPanel() {
		JPanel buttonPane = new JPanel();
		buttonPane.setBorder(new MatteBorder(1, 0, 0, 0, Color.LIGHT_GRAY));
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		JButton analyzeButton = new JButton(AnalyzerConstants.getString("EachTagDependAnalyzeDialog.Button.Analyze")); //$NON-NLS-1$
		analyzeButton.setPreferredSize(new Dimension(75, 22));
		analyzeButton.setActionCommand(ANALYZE_ACTION_COMMAND);
		analyzeButton.addActionListener(this);
		buttonPane.add(analyzeButton);
		//getRootPane().setDefaultButton(analyzeButton);
		
		JButton cancelButton = new JButton(AnalyzerConstants.getString("EachTagDependAnalyzeDialog.Button.Cancel")); //$NON-NLS-1$
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
