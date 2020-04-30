package analyzer.panel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.DropMode;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;

import com.jidesoft.grid.AbstractUndoableTableModel;
import com.jidesoft.grid.AutoResizePopupMenuCustomizer;
import com.jidesoft.grid.DefaultUndoableTableModel;
import com.jidesoft.grid.JideTableTransferHandler;
import com.jidesoft.grid.SortableTable;
import com.jidesoft.grid.TableColumnChooserPopupMenuCustomizer;
import com.jidesoft.grid.TableHeaderPopupMenuInstaller;
import com.jidesoft.swing.JideTitledBorder;
import com.jidesoft.swing.PartialEtchedBorder;
import com.jidesoft.swing.PartialSide;

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


