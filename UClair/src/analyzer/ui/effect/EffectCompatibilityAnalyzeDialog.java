package analyzer.ui.effect;

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
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

import analyzer.constants.AnalyzerConstants;

/************************************************
 * @date	: 2020. 5.07.
 * @책임자 : 이승환
 * @설명  	: 객체 효과 양립성 분석 Dialog 클래스.
 * @변경이력 	: 
 ************************************************/

public class EffectCompatibilityAnalyzeDialog extends JDialog implements ActionListener {

	/**
	 * 객체 직렬화 버전 아이디.
	 */
	private static final long serialVersionUID = 1L;
	private static final String ANALYZE_ACTION_COMMAND = "effect.compatibility.analyze"; //$NON-NLS-1$
	private static final String CANCEL_ACTION_COMMAND = "effect.compatibility.cancel"; //$NON-NLS-1$

	/**
	 * Create the dialog.
	 */
	public EffectCompatibilityAnalyzeDialog(Frame frame) {
		super(frame, true);
		initialize();
	}
	
	private void initialize() {
		setTitle(AnalyzerConstants.getString("EffectCompatibilityAnalyzeDialog.Title")); //$NON-NLS-1$
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
		gbl_contentPanel.columnWidths = new int[]{0, 0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		
		JLabel contentLabel = new JLabel(AnalyzerConstants.getString("EffectCompatibilityAnalyzeDialog.Label.Content")); //$NON-NLS-1$
		GridBagConstraints gbc_contentLabel = new GridBagConstraints();
		gbc_contentLabel.gridwidth = 2;
		gbc_contentLabel.anchor = GridBagConstraints.WEST;
		gbc_contentLabel.insets = new Insets(10, 10, 10, 0);
		gbc_contentLabel.gridx = 0;
		gbc_contentLabel.gridy = 0;
		contentPanel.add(contentLabel, gbc_contentLabel);
		
		JLabel windowLabel = new JLabel(AnalyzerConstants.getString("EffectCompatibilityAnalyzeDialog.Label.Window")); //$NON-NLS-1$
		GridBagConstraints gbc_windowLabel = new GridBagConstraints();
		gbc_windowLabel.insets = new Insets(0, 10, 0, 5);
		gbc_windowLabel.anchor = GridBagConstraints.EAST;
		gbc_windowLabel.gridx = 0;
		gbc_windowLabel.gridy = 1;
		contentPanel.add(windowLabel, gbc_windowLabel);
		
		JComboBox comboBox = new JComboBox();
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.insets = new Insets(0, 0, 0, 5);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 1;
		gbc_comboBox.gridy = 1;
		contentPanel.add(comboBox, gbc_comboBox);
		
		return contentPanel;
	}

	private JPanel createButtonPanel() {
		JPanel buttonPane = new JPanel();
		buttonPane.setBorder(new MatteBorder(1, 0, 0, 0, Color.LIGHT_GRAY));
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		JButton analyzeButton = new JButton(AnalyzerConstants.getString("EffectCompatibilityAnalyzeDialog.Button.Analyze")); //$NON-NLS-1$
		analyzeButton.setPreferredSize(new Dimension(75, 22));
		analyzeButton.setActionCommand(ANALYZE_ACTION_COMMAND);
		analyzeButton.addActionListener(this);
		buttonPane.add(analyzeButton);
		//getRootPane().setDefaultButton(analyzeButton);
		
		JButton cancelButton = new JButton(AnalyzerConstants.getString("EffectCompatibilityAnalyzeDialog.Button.Cancel")); //$NON-NLS-1$
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
