package analyzer.analysis.virtual;

import com.naru.uclair.tag.Tag;

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
