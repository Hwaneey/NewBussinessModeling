package analyzer.util;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.jidesoft.docking.DefaultDockableHolder;
import com.jidesoft.document.DocumentPane;
import com.jidesoft.document.IDocumentGroup;
import com.jidesoft.swing.JideTabbedPane;

import analyzer.frame.AnalyzerMainFrame;
/*******************************
 * @date	: 2020. 4. 28.
 * @설명 		: 워크스페이스 문서탭
 * @변경이력 	:
 *******************************/
public class DocumentTabs {
	protected static JFrame _frame =  AnalyzerMainFrame._frame ;
	protected static boolean  _autohideAll = false;
	private static byte[] _fullScreenLayout;			
	public static DocumentPane createDocumentTabs() {	
		DocumentPane documentPane = new DocumentPane() {
			/**
			 * 
			 */
			private static final long serialVersionUID = -1949764911397514628L;

			@Override
			 protected IDocumentGroup createDocumentGroup() {
                IDocumentGroup group = super.createDocumentGroup();
                if (group instanceof JideTabbedPane) {
                    ((JideTabbedPane) group).addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {		// 워크스페이스 문서탭 더블클릭 시 전체화면
                            if (SwingUtilities.isLeftMouseButton(e) && e.getClickCount() == 2) {
                                if (!_autohideAll) {
                                    _fullScreenLayout = ((DefaultDockableHolder) _frame).getDockingManager().getLayoutRawData();
                                    ((DefaultDockableHolder) _frame).getDockingManager().autohideAll();
                                    _autohideAll = true;
                                }
                                else {
                                    if (_fullScreenLayout != null) {
                                        ((DefaultDockableHolder) _frame).getDockingManager().setLayoutRawData(_fullScreenLayout);
                                    }
                                    _autohideAll = false;
                                }
//                                Component lastFocusedComponent = _workspacePane.getActiveDocument().getLastFocusedComponent();
//                                if (lastFocusedComponent != null) {
//                                    lastFocusedComponent.requestFocusInWindow();
//                                }
                            }
                        }
                    });
                }
                return group;
            }
		};
		documentPane.setTabPlacement(javax.swing.JTabbedPane.TOP);
		return documentPane;
	}
}
