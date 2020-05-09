package analyzer.analysis.virtual;

import com.naru.uclair.tag.Tag;
/************************************************
 * @date	: 2020. 5.07.
 * @책임자 : 박보미
 * @설명  	: 가상태그 종속성 분석 
 * @변경이력 	: 
 ************************************************/
public class VirtualDepend {

	private Tag tag;
	private String eventName;
	
	public VirtualDepend(String eventName, Tag tag) {
		this.eventName = eventName;
		this.tag = tag;
	}

	public Tag getTag() {
		return tag;
	}

	public void setTag(Tag tag) {
		this.tag = tag;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	
	

}
