package analyzer.action;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JOptionPane;

import com.naru.common.ui.NormalProgressWindow;
import analyzer.action.AbstractProjectRelatedAction;
import analyzer.ui.virtual.VirtualTagAnalyzeDialog;

import analyzer.analysis.dangling.DanglingTagAnalyzer;
import analyzer.ui.dangling.DanglingTagAnalyzeResultView;

import analyzer.AnalyzerEditorFactory;
import analyzer.analysis.virtual.VirtualDepend;
import analyzer.analysis.event.EventDepend;
import analyzer.ui.linked.ObjectTagLinkAnalyzeResultView;
import analyzer.ui.virtual.VirtualTagAnalyzeResultView;
import analyzer.Analyzer;
import analyzer.constants.AnalyzerConstants;

/************************************************
 * @date	: 2020. 5.07.
 * @책임자 : 박보미
 * @설명  	: Virtual 태그 종속성 분석 Action 클래스
 * @변경이력 	: 
 ************************************************/

public class VirtualTagDependAnalyzeAction extends AbstractProjectRelatedAction {


	private static final long serialVersionUID = 1L;
	private VirtualTagAnalyzeDialog analyzeDialog;

	public VirtualTagDependAnalyzeAction(Analyzer dev) {
		super(dev);
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		Analyzer analyzer = getAnalyzer();
//		if(null == analyzeDialog) {
			analyzeDialog = new VirtualTagAnalyzeDialog(analyzer);
//		}
		analyzeDialog.setLocationRelativeTo(analyzer);
		//
		//
		analyzeDialog.setTagDictionary(analyzer.getProject().getTagDictionary());
		analyzeDialog.setVisible(true);
//		
//		// TODO �м�/�ּ� Ȯ���Ͽ� ��� ǥ��.
////		AnalyzerEditorFactory.getFactory().getEditor(key);
//		analyzer.showWorkspace(AnalyzerEditorFactory.VIRTUAL_TAG_DEPENDENCY_RESULT_VIEW_KEY);
		
		
		if(null != analyzeDialog.getSelectedTag()) {
			VirtualTagAnalyzeResultView view = (VirtualTagAnalyzeResultView) AnalyzerEditorFactory.getFactory().
					getEditor(AnalyzerEditorFactory.VIRTUAL_TAG_DEPENDENCY_RESULT_VIEW_KEY);
			EventDepend eventDepend = new EventDepend();
			eventDepend.analyzer(analyzer.getProject().getEventDictionary());
			
			view.setVirtualTagDepends(eventDepend.getVirtualTagEventDepend(analyzeDialog.getSelectedTag()), analyzeDialog.getSelectedTag());
			/**
			 * �����ּ� ���Ӽ� ������ Ȯ���Ͽ� ������ ǥ���Ѵ�.
			 * ���� �ɼ������� �����ϰ�, ������ �м��Ͽ� �����⿡ �ٷ� ǥ���Ѵ�.
			 */
			analyzer.showWorkspace(AnalyzerEditorFactory.VIRTUAL_TAG_DEPENDENCY_RESULT_VIEW_KEY);
		}
		else {
//			view.setVirtualTagDepends(new ArrayList<VirtualDepend>(), "");
		}
//		String selectedTag = analyzeDialog.getSelectedTag();
//		List<String> tagList = new ArrayList<String>();
//		if(null != selectedTag) {
//			TagDictionary dictionary = analyzer.getProject().getTagDictionary();
//			for(Tag tag : dictionary.getAllNoneSystemDataTags()) {
//				if(tag.isHardwareTag()) {
//					tagList.add(tag.getKey());
//				}
//			}
			// �̺�Ʈ ������ �±׸� �˻��Ͽ� ����� ����Ʈ �𵨷� ������.
//		}
		
		
		
	}

}
