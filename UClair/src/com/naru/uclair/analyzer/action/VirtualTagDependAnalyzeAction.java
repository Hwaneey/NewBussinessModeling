package com.naru.uclair.analyzer.action;

import java.awt.event.ActionEvent;

import com.naru.uclair.analyzer.Analyzer;
import com.naru.uclair.analyzer.AnalyzerEditorFactory;
import com.naru.uclair.analyzer.analysis.event.EventDepend;
import com.naru.uclair.analyzer.ui.virtual.VirtualTagAnalyzeDialog;
import com.naru.uclair.analyzer.ui.virtual.VirtualTagAnalyzeResultView;

/**
 *
 * <pre>
 * NAME   : com.naru.uclair.analyzer.action.VirtualTagDependAnalyzeAction.java
 * DESC   : ���� �±� ���Ӽ� �м� Action Ŭ����.
 *
 * references : ���輭 NARU-XXX-XXX-XXX
 *
 * Copyright 2012 NARU Technology All rights reserved
 * <pre>
 *
 * @author US Laboratory ywpark
 * @since 2012. 6. 20.
 * @version 1.0
 *
 */
public class VirtualTagDependAnalyzeAction extends AbstractProjectRelatedAction {

	/**
	 * 
	 */
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
			VirtualTagAnalyzeResultView view = (VirtualTagAnalyzeResultView) AnalyzerEditorFactory.getFactory().getEditor(AnalyzerEditorFactory.VIRTUAL_TAG_DEPENDENCY_RESULT_VIEW_KEY);
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
