package analyzer.action;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import analyzer.Analyzer;
import analyzer.AnalyzerEditorFactory;
import analyzer.analysis.event.EventDepend;
import analyzer.ui.event.EventDependAnalyzeDialog;
import analyzer.ui.event.EventDependAnalyzeResultView;
import com.naru.uclair.project.EventDictionary;
import com.naru.uclair.tag.Tag;
import com.naru.uclair.util.TagListWindowDialog;


/** @date	: 2020. 5.07.
* @책임자 : 지한별
* @설명  	: 이벤트 종속성 분석 Action 클래스.
* @변경이력 	: **/

public class EventDependAnalyzeAction extends AbstractProjectRelatedAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private EventDependAnalyzeDialog analyzeDialog;

	public EventDependAnalyzeAction(Analyzer dev) {
		super(dev);
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		Analyzer analyzer = getAnalyzer();
		if(null == analyzeDialog) {
			analyzeDialog = new EventDependAnalyzeDialog(analyzer);
		}
		analyzeDialog.setLocationRelativeTo(analyzer);
		analyzeDialog.setVisible(true);
		
		if(analyzeDialog.isAnalyzerCheck()) {
			EventDictionary eventDictionary = getAnalyzer().getProject().getEventDictionary();
			EventDepend depend = new EventDepend();
			depend.analyzer(eventDictionary);
			
			EventDependAnalyzeResultView editor = (EventDependAnalyzeResultView) AnalyzerEditorFactory.getFactory().getEditor(AnalyzerEditorFactory.EVENT_TAG_DEPENDENCY_RESULT_VIEW_KEY);
			if(null != editor) {
				((EventDependAnalyzeResultView) editor).setEventDepend(depend);
			}
			
			
			// TODO 분석/취소 확인후 표시
			analyzer.showWorkspace(AnalyzerEditorFactory.EVENT_TAG_DEPENDENCY_RESULT_VIEW_KEY);
		}
	}

	public List<Tag> getTagList(String sentence, String keyword) {
		ArrayList<Tag> tagList = new ArrayList<Tag>();
		while(true) {
			try {
				final char delimiter = ' ';
				int start, end;
				String subSentence;
				start = sentence.indexOf(keyword);
				if(start == -1) {
					break;
				}
				subSentence = sentence.substring(start);
				if (subSentence.length() == keyword.length())
					end = keyword.length();
				else {
					end = subSentence.indexOf(delimiter);
					if (end == -1)
						end = subSentence.length();
				}
				
				String returnString = sentence.substring(start, start + end);
				
				returnString = returnString.substring(returnString.indexOf("\"") + 1, returnString.length());
				returnString = returnString.substring(0, returnString.indexOf("\"") );
				Tag tag = TagListWindowDialog.getTag(returnString);
				if(!tagList.contains(tag)) {
					if(null != tag) {
						tagList.add(tag);
						System.out.println(tag.getKey());
					}
					else {
						System.out.println("태그사전에 스크립트에서 사용된 태그가 없습니다. " + returnString);
					}
				}
				if(sentence.length() <=7) {
					break;
				}
				else {
					if(sentence.length() > start + end) {
						sentence = sentence.substring(start + end,sentence.length());
					}
					else {
						break;
					}
				}
			}
			catch(Exception ex) {
				break;
			}
		}
		
		return tagList;
	}
}
