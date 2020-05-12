package analyzer.ui.physical;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.DropMode;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.table.TableRowSorter;

import com.jidesoft.document.DocumentComponent;
import com.jidesoft.document.DocumentPane;
import com.jidesoft.grid.AutoResizePopupMenuCustomizer;
import com.jidesoft.grid.DefaultUndoableTableModel;
import com.jidesoft.grid.JideTableTransferHandler;
import com.jidesoft.grid.SortableTable;
import com.jidesoft.grid.TableColumnChooserPopupMenuCustomizer;
import com.jidesoft.grid.TableHeaderPopupMenuInstaller;

import analyzer.analysis.tag.PhysicalDepend;
import analyzer.constants.AnalyzerConstants;
import analyzer.frame.AnalyzerMainFrame;
/************************************************
 * @date	: 2020. 5.07.
 * @책임자 : 지이삭
 * @설명  	: 물리주소 종속성 분석 결과 표시 
 * @변경이력 	: 
 ************************************************/

public class PhysicalTagDependAnalyzeResultView extends JPanel {


	private static final long serialVersionUID = 1L;
	private JTable resultTable;
	PhysicalTagDependAnalyzeResultTableModel tableModel = null;
	private TableRowSorter<PhysicalTagDependAnalyzeResultTableModel> sorter;
	public static final String PHYSICAL_ADRESS_DEPENDENCY_RESULT_VIEW_KEY = AnalyzerConstants
			.getString("AnalyzerEditorFactory.PysicalAddress.Key"); //$NON-NLS-1$
	private static DocumentPane _workspacePane =  AnalyzerMainFrame._workspacePane;
	/**
	 * Create the panel.
	 */
	public PhysicalTagDependAnalyzeResultView() {
		final DocumentComponent document = new DocumentComponent(new JScrollPane(initializeUi()), PHYSICAL_ADRESS_DEPENDENCY_RESULT_VIEW_KEY);		
        if (_workspacePane.getDocument(PHYSICAL_ADRESS_DEPENDENCY_RESULT_VIEW_KEY) != null) {	
			_workspacePane.setActiveDocument(PHYSICAL_ADRESS_DEPENDENCY_RESULT_VIEW_KEY);
		} else {	
	        _workspacePane.openDocument(document);
	        _workspacePane.setActiveDocument(PHYSICAL_ADRESS_DEPENDENCY_RESULT_VIEW_KEY);
		}
	}

	private JComponent initializeUi() {
		SortableTable _sortableTable = null;
	    DefaultUndoableTableModel _defaultModel;
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JScrollPane resultTableScrollPane = new JScrollPane();
		GridBagConstraints gbc_resultTableScrollPane = new GridBagConstraints();
		gbc_resultTableScrollPane.fill = GridBagConstraints.BOTH;
		gbc_resultTableScrollPane.gridx = 0;
		gbc_resultTableScrollPane.gridy = 0;
		add(resultTableScrollPane, gbc_resultTableScrollPane);
		
		tableModel = new PhysicalTagDependAnalyzeResultTableModel();
		sorter = new TableRowSorter<PhysicalTagDependAnalyzeResultTableModel>(tableModel);
		sorter.setModel(tableModel);
		resultTable = new JTable(tableModel);
		resultTable.setRowSorter(sorter);
		resultTableScrollPane.setViewportView(resultTable);
		
		
		
		_defaultModel = (DefaultUndoableTableModel) new DefaultUndoableTableModel();
		_sortableTable = new SortableTable(_defaultModel);
		InputMap map = _sortableTable.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        map.put(KeyStroke.getKeyStroke("control Z"), "undo");
        map.put(KeyStroke.getKeyStroke("control Y"), "redo");

        ((JideTableTransferHandler) _sortableTable.getTransferHandler()).setAcceptImport(true);
        _sortableTable.setNonContiguousCellSelection(false);		        
        _sortableTable.setDragEnabled(true);		        
        _sortableTable.setDropMode(DropMode.INSERT);
        
        
         _sortableTable.setClickCountToStart(2);		
        TableHeaderPopupMenuInstaller installer = new TableHeaderPopupMenuInstaller(_sortableTable);
        installer.addTableHeaderPopupMenuCustomizer(new AutoResizePopupMenuCustomizer());
        installer.addTableHeaderPopupMenuCustomizer(new TableColumnChooserPopupMenuCustomizer());
		
        
        
		return resultTableScrollPane;
	}

	public void setPhysicalDepend(PhysicalDepend depend) {
		tableModel.setPhysicalDepend(depend);
	}
}
