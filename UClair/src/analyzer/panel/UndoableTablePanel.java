package analyzer.panel;

import javax.swing.JPanel;

public class UndoableTablePanel extends JPanel {
//	private SortableTable _sortableTable;
//    private DefaultUndoableTableModel _defaultModel;
//    
//    public UndoableTablePanel() {
//        super(new BorderLayout(2, 2));
//        String header[] = {"A", "B", "C", "D"};
//		String contents[][] = {
//				{"1", "2", "3", "4"},
//				{"5", "6", "7", "8"},
//				{"9", "10", "11", "12"},
//				{"13", "14", "15", "16"}
//		};
//		
//        _defaultModel = (DefaultUndoableTableModel) new DefaultUndoableTableModel(contents, header);
//        _sortableTable = new SortableTable(_defaultModel);
//        //_sortableTable.setPreferredScrollableViewportSize(new Dimension(600, 500));
//        InputMap map = _sortableTable.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
//        map.put(KeyStroke.getKeyStroke("control Z"), "undo");
//        map.put(KeyStroke.getKeyStroke("control Y"), "redo");
//        _sortableTable.setNonContiguousCellSelection(false);
//        ((JideTableTransferHandler) _sortableTable.getTransferHandler()).setAcceptImport(true);
//        _sortableTable.setClickCountToStart(2);
//        _sortableTable.setDragEnabled(true);
//        _sortableTable.setDropMode(DropMode.INSERT_ROWS);
// 
//        TableHeaderPopupMenuInstaller installer = new TableHeaderPopupMenuInstaller(_sortableTable);
//        installer.addTableHeaderPopupMenuCustomizer(new AutoResizePopupMenuCustomizer());
//        installer.addTableHeaderPopupMenuCustomizer(new TableColumnChooserPopupMenuCustomizer());
//        this.add(new JScrollPane(_sortableTable));
//    }
}


