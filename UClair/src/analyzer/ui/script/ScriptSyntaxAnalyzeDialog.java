package analyzer.ui.script;

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
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

import analyzer.constants.AnalyzerConstants;

/************************************************
 * @date	: 2020. 5.07.
 * @책임자 : 장성현
 * @설명  	:  스크립트 검증 Dialog 클래스.
 * @변경이력 	: 
 ************************************************/

public class ScriptSyntaxAnalyzeDialog extends JDialog implements ActionListener {

	/**
	 * 객체 직렬화 버전 아이디.
	 */
	private static final long serialVersionUID = 1L;
	private static final String ANALYZE_ACTION_COMMAND = "script.syntax.analyze"; //$NON-NLS-1$
	private static final String CANCEL_ACTION_COMMAND = "script.syntax.cancel"; //$NON-NLS-1$

	/**
	 * Create the dialog.
	 */
	public ScriptSyntaxAnalyzeDialog(Frame frame) {
		super(frame, true);
		initialize();
	}
	
	private void initialize() {
		setTitle(AnalyzerConstants.getString("ScriptSyntaxAnalyzeDialog.Title")); //$NON-NLS-1$
		setSize(500, 150);
		setMinimumSize(new Dimension(500, 150));
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		//getContentPane().setLayout(new BorderLayout());
		getContentPane().add(createContentPanel(), BorderLayout.CENTER);
		getContentPane().add(createButtonPanel(), BorderLayout.SOUTH);
	}
	
	private JPanel createContentPanel() {
		JPanel contentPanel = new JPanel();
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		
		JLabel contentLabel = new JLabel(AnalyzerConstants.getString("ScriptSyntaxAnalyzeDialog.Label.Content")); //$NON-NLS-1$
		GridBagConstraints gbc_contentLabel = new GridBagConstraints();
		gbc_contentLabel.insets = new Insets(10, 10, 10, 0);
		gbc_contentLabel.anchor = GridBagConstraints.WEST;
		gbc_contentLabel.gridx = 0;
		gbc_contentLabel.gridy = 0;
		contentPanel.add(contentLabel, gbc_contentLabel);
		
		return contentPanel;
	}
	
	private JPanel createButtonPanel() {
		JPanel buttonPane = new JPanel();
		buttonPane.setBorder(new MatteBorder(1, 0, 0, 0, Color.LIGHT_GRAY));
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		JButton analyzeButton = new JButton(AnalyzerConstants.getString("ScriptSyntaxAnalyzeDialog.Button.Analyze")); //$NON-NLS-1$
		analyzeButton.setPreferredSize(new Dimension(75, 22));
		analyzeButton.setActionCommand(ANALYZE_ACTION_COMMAND);
		analyzeButton.addActionListener(this);
		buttonPane.add(analyzeButton);
		//getRootPane().setDefaultButton(analyzeButton);
		
		JButton cancelButton = new JButton(AnalyzerConstants.getString("ScriptSyntaxAnalyzeDialog.Button.Cancel")); //$NON-NLS-1$
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
