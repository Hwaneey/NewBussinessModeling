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
 * DESC   : 가상 태그 종속성 분석 Action 클래스.
 *
 * references : 설계서 NARU-XXX-XXX-XXX
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
//		// TODO 분석/최소 확인하여 결과 표시.
////		AnalyzerEditorFactory.getFactory().getEditor(key);
//		analyzer.showWorkspace(AnalyzerEditorFactory.VIRTUAL_TAG_DEPENDENCY_RESULT_VIEW_KEY);
		
		
		if(null != analyzeDialog.getSelectedTag()) {
			VirtualTagAnalyzeResultView view = (VirtualTagAnalyzeResultView) AnalyzerEditorFactory.getFactory().getEditor(AnalyzerEditorFactory.VIRTUAL_TAG_DEPENDENCY_RESULT_VIEW_KEY);
			EventDepend eventDepend = new EventDepend();
			eventDepend.analyzer(analyzer.getProject().getEventDictionary());
			
			view.setVirtualTagDepends(eventDepend.getVirtualTagEventDepend(analyzeDialog.getSelectedTag()), analyzeDialog.getSelectedTag());
			/**
			 * 물리주소 종속성 정보를 확인하여 정보를 표시한다.
			 * 기존 옵션정보를 무시하고, 정보를 분석하여 편집기에 바로 표시한다.
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
			// 이벤트 사전의 태그를 검사하여 결과를 리스트 모델로 보낸다.
//		}
		
		
		
	}

}
