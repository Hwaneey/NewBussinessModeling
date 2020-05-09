package analyzer.analysis.io.excel;

import java.util.ArrayList;
import java.util.List;

import com.naru.uclair.tag.Tag;
/************************************************
 * @date	: 2020. 5.07.
 * @책임자 :  지이삭
 * @설명  	:   IO 테스트 
 * @변경이력 	: 
 ************************************************/
public class IOTestDevice {
	private String nodeName;
	private String deviceName;
	private List<Tag> tagList;
	
	public IOTestDevice() {
		
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public List<Tag> getTagList() {
		return tagList;
	}

	public void setTagList(List<Tag> tagList) {
		this.tagList = tagList;
	}
	
	public void addTag(Tag tag) {
		if(null == tagList) {
			tagList = new ArrayList<Tag>();
		}
		
		if(!tagList.contains(tag)) {
			tagList.add(tag);
		}
	}
	
	public void removeTag(Tag removeTag) {
		if(null != tagList) {
			tagList.remove(removeTag);
		}
	}
}
