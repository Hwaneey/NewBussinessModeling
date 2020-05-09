package analyzer.ui.virtual;

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
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;

import com.jidesoft.swing.SearchableUtils;
import analyzer.constants.AnalyzerConstants;
import com.naru.uclair.project.TagDictionary;
import com.naru.uclair.tag.DataTag;
import com.naru.uclair.tag.ITagConstants;
import com.naru.uclair.tag.Tag;


/************************************************
 * @date	: 2020. 5.07.
 * @책임자 : 박보미
 * @설명  	: Virtual 태그 종속성 분석 Dialog 클래스
 * @변경이력 	: 
 ************************************************/

public class VirtualTagAnalyzeDialog extends JDialog implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String ANALYZE_ACTION_COMMAND = "virtual.analyze"; //$NON-NLS-1$
	private static final String CANCEL_ACTION_COMMAND = "virtual.cancel"; //$NON-NLS-1$
	
	private static final String ALL_CHECKBOX_ACTION_COMMAND = "virtual.all"; //$NON-NLS-1$
	private static final String DIGITAL_CHECKBOX_COMMAND = "virtual.digital"; //$NON-NLS-1$
	private static final String ANALOG_CHECKBOX_COMMAND = "virtual.analog"; //$NON-NLS-1$
	private static final String STRING_CHECKBOX_COMMAND = "virtual.string"; //$NON-NLS-1$
	
	private List<Tag> tagList = null;
	private DefaultListModel model;
	private JList virtualTagList;
	private JCheckBox allCheckBox = null;
	private JCheckBox digitalTagCheckBox = null;
	private JCheckBox analogTagCheckBox = null;
	private JCheckBox stringTagCheckBox = null;
	private String selectedTag = null;

	/**
	 * Create the dialog.
	 */
	public VirtualTagAnalyzeDialog(Frame frame) {
		super(frame, true);
		tagList = new ArrayList<Tag>();
		initialize();
	}
	
	private void initialize() {
		setTitle(AnalyzerConstants.getString("VirtualTagAnalyzeDialog.Title")); //$NON-NLS-1$
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
		gbl_contentPanel.columnWidths = new int[]{0, 0, 0, 0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		
		JLabel contentLabel = new JLabel(AnalyzerConstants.getString("VirtualTagAnalyzeDialog.Label.Content")); //$NON-NLS-1$
		GridBagConstraints gbc_contentLabel = new GridBagConstraints();
		gbc_contentLabel.anchor = GridBagConstraints.WEST;
		gbc_contentLabel.gridwidth = 4;
		gbc_contentLabel.insets = new Insets(10, 10, 10, 0);
		gbc_contentLabel.gridx = 0;
		gbc_contentLabel.gridy = 0;
		contentPanel.add(contentLabel, gbc_contentLabel);
		
		allCheckBox = new JCheckBox(AnalyzerConstants.getString("VirtualTagAnalyzeDialog.CheckBox.All")); //$NON-NLS-1$
		allCheckBox.setActionCommand(ALL_CHECKBOX_ACTION_COMMAND);
		allCheckBox.setSelected(true);
		allCheckBox.addActionListener(this);
		GridBagConstraints gbc_allCheckBox = new GridBagConstraints();
		gbc_allCheckBox.insets = new Insets(0, 10, 5, 5);
		gbc_allCheckBox.anchor = GridBagConstraints.WEST;
		gbc_allCheckBox.gridx = 0;
		gbc_allCheckBox.gridy = 1;
		contentPanel.add(allCheckBox, gbc_allCheckBox);
		
		analogTagCheckBox = new JCheckBox(AnalyzerConstants.getString("VirtualTagAnalyzeDialog.CheckBox.AnalogTag")); //$NON-NLS-1$
		analogTagCheckBox.setActionCommand(ANALOG_CHECKBOX_COMMAND);
		analogTagCheckBox.addActionListener(this);
		GridBagConstraints gbc_analogTagCheckBox = new GridBagConstraints();
		gbc_analogTagCheckBox.insets = new Insets(0, 0, 5, 5);
		gbc_analogTagCheckBox.anchor = GridBagConstraints.WEST;
		gbc_analogTagCheckBox.gridx = 1;
		gbc_analogTagCheckBox.gridy = 1;
		contentPanel.add(analogTagCheckBox, gbc_analogTagCheckBox);
		
		digitalTagCheckBox = new JCheckBox(AnalyzerConstants.getString("VirtualTagAnalyzeDialog.CheckBox.DigitalTag")); //$NON-NLS-1$
		digitalTagCheckBox.setActionCommand(DIGITAL_CHECKBOX_COMMAND);
		digitalTagCheckBox.addActionListener(this);
		GridBagConstraints gbc_digitalTagCheckBox = new GridBagConstraints();
		gbc_digitalTagCheckBox.insets = new Insets(0, 0, 5, 5);
		gbc_digitalTagCheckBox.anchor = GridBagConstraints.WEST;
		gbc_digitalTagCheckBox.gridx = 2;
		gbc_digitalTagCheckBox.gridy = 1;
		contentPanel.add(digitalTagCheckBox, gbc_digitalTagCheckBox);
		
		stringTagCheckBox = new JCheckBox(AnalyzerConstants.getString("VirtualTagAnalyzeDialog.CheckBox.StringTag")); //$NON-NLS-1$
		stringTagCheckBox.setActionCommand(STRING_CHECKBOX_COMMAND);
		stringTagCheckBox.addActionListener(this);
		GridBagConstraints gbc_stringTagCheckBox = new GridBagConstraints();
		gbc_stringTagCheckBox.insets = new Insets(0, 0, 5, 0);
		gbc_stringTagCheckBox.anchor = GridBagConstraints.WEST;
		gbc_stringTagCheckBox.gridx = 3;
		gbc_stringTagCheckBox.gridy = 1;
		contentPanel.add(stringTagCheckBox, gbc_stringTagCheckBox);
		
		JScrollPane virtualTagListScrollPane = new JScrollPane();
		virtualTagListScrollPane.setBorder(new TitledBorder(null, AnalyzerConstants.getString("VirtualTagAnalyzeDialog.Title.VirtualTagList"), TitledBorder.LEADING, TitledBorder.TOP, null, null)); //$NON-NLS-1$
		//virtualTagListScrollPane.setViewportBorder(new TitledBorder(null, "\uAC00\uC0C1\uD0DC\uADF8\uBAA9\uB85D", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_virtualTagListScrollPane = new GridBagConstraints();
		gbc_virtualTagListScrollPane.gridwidth = 4;
		gbc_virtualTagListScrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_virtualTagListScrollPane.fill = GridBagConstraints.BOTH;
		gbc_virtualTagListScrollPane.gridx = 0;
		gbc_virtualTagListScrollPane.gridy = 2;
		contentPanel.add(virtualTagListScrollPane, gbc_virtualTagListScrollPane);
		
		virtualTagList = new JList();
		model = new DefaultListModel();
		SearchableUtils.installSearchable(virtualTagList);
		virtualTagList.setModel(model);
		virtualTagListScrollPane.setViewportView(virtualTagList);
			
		return contentPanel;
	}

	private JPanel createButtonPanel() {
		JPanel buttonPane = new JPanel();
		buttonPane.setBorder(new MatteBorder(1, 0, 0, 0, Color.LIGHT_GRAY));
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		JButton analyzeButton = new JButton(AnalyzerConstants.getString("VirtualTagAnalyzeDialog.Button.Analyze")); //$NON-NLS-1$
		analyzeButton.setPreferredSize(new Dimension(75, 22));
		analyzeButton.setActionCommand(ANALYZE_ACTION_COMMAND);
		analyzeButton.addActionListener(this);
		buttonPane.add(analyzeButton);
		//getRootPane().setDefaultButton(analyzeButton);
		
		JButton cancelButton = new JButton(AnalyzerConstants.getString("VirtualTagAnalyzeDialog.Button.Cancel")); //$NON-NLS-1$
		cancelButton.setPreferredSize(new Dimension(75, 22));
		cancelButton.setActionCommand(CANCEL_ACTION_COMMAND);
		cancelButton.addActionListener(this);
		buttonPane.add(cancelButton);
		return buttonPane;
	}
	
	private void setSelectedCheck(boolean check) {
		digitalTagCheckBox.setSelected(check);
		analogTagCheckBox.setSelected(check);
		stringTagCheckBox.setSelected(check);
	}
	
	public void setTagDictionary(TagDictionary dic) {
		// üũ�ڽ� Ȱ��ȭ
		setSelectedCheck(true);
		
		List<DataTag> list = dic.getAllNoneSystemDataTags();
		for(Tag tag : list) {
			if(!tag.isHardwareTag()) {
				tagList.add(tag);
				model.addElement(tag.getKey());
			}
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if(ANALYZE_ACTION_COMMAND.equals(cmd)) {
			if(null != virtualTagList.getSelectedValue()) {
				selectedTag = (String) virtualTagList.getSelectedValue();
			}
			
			if(null == selectedTag) {
				JOptionPane.showMessageDialog(this, "선택된 태그가 없습니다.");
			}
			else {
				dispose();
			}
		}
		else if(CANCEL_ACTION_COMMAND.equals(cmd)) {
			selectedTag = null;
			dispose();
		}
		else if(ALL_CHECKBOX_ACTION_COMMAND.equals(cmd)) {
			setSelectedCheck(allCheckBox.isSelected());
			setSelectedTag(ITagConstants.ALL_TYPE, !allCheckBox.isSelected());
		}
		else if(DIGITAL_CHECKBOX_COMMAND.equals(cmd)) {
			setSelectedTag(ITagConstants.DIGITAL, false);
		}
		else if(ANALOG_CHECKBOX_COMMAND.equals(cmd)) {
			setSelectedTag(ITagConstants.ANALOG, false);
		}
		else if(STRING_CHECKBOX_COMMAND.equals(cmd)) {
			setSelectedTag(ITagConstants.STRING, false);
		}
	}
	
	private void setSelectedTag(int type, boolean init) {
		model.clear();
		
		if(type != ITagConstants.ALL_TYPE) {
			if(digitalTagCheckBox.isSelected() && analogTagCheckBox.isSelected() && stringTagCheckBox.isSelected()) {
				allCheckBox.setSelected(true);
			}
			else {
				if(allCheckBox.isSelected()) {
					allCheckBox.setSelected(false);
				}
			}
		}
		
		if(!init) {
			for(Tag tag : tagList) {
				if(type == ITagConstants.ALL_TYPE) {
					model.addElement(tag.getKey());
				}
				else {
					if(digitalTagCheckBox.isSelected()) {
						if(tag.getTagType() == ITagConstants.DIGITAL) {
							model.addElement(tag.getKey());
						}
					}
					if(analogTagCheckBox.isSelected()) {
						if(tag.getTagType() == ITagConstants.ANALOG) {
							model.addElement(tag.getKey());
						}
					}
					if(stringTagCheckBox.isSelected()) {
						if(tag.getTagType() == ITagConstants.STRING) {
							model.addElement(tag.getKey());
						}
					}
				}
			}
		}
	}
	
	public String getSelectedTag() {
		return selectedTag;
	}
}
