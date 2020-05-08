package com.naru.uclair.analyzer.analysis.tag;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.naru.uclair.project.TagDictionary;
import com.naru.uclair.tag.DataTag;
import com.naru.uclair.tag.Tag;

public class PhysicalDepend {
	/**
	 * 물리주소 종속성 노드별 Map 정보
	 * key:노드명
	 * value:물리주소 노드별 정보
	 */
	private Map<String, PhysicalTagDepend> physicalMap;
	
	public PhysicalDepend() {
		physicalMap = new TreeMap<String, PhysicalTagDepend>();
	}
	
	public List<Tag> getPhysicalTagDepends() {
		Iterator<PhysicalTagDepend> it = physicalMap.values().iterator();
		List<Tag> tagList = new LinkedList<Tag>();
		
		while(it.hasNext()) {
			PhysicalTagDepend physicalTagDepend = it.next();
			for(Tag tag : physicalTagDepend.getTags()) {
				tagList.add(tag);
			}
			
		}
		return tagList;
	}

	public void analyzer(TagDictionary tagdic) {
		List<DataTag> tagList = tagdic.getAllNoneSystemDataTags();
		for(DataTag tag : tagList) {
			if(tag.isHardwareTag()) {
//				System.out.println("디바이스 태그입니다. - 태그키 : " + tag.getKey());
				PhysicalTagDepend physicalTagDepend = physicalMap.get(tag.getNode());
				if(null == physicalTagDepend) {
					physicalTagDepend = new PhysicalTagDepend(tag.getNode(), tag.getDeviceAdapter().getDeviceName());
					physicalMap.put(tag.getNode(), physicalTagDepend);
				}
				physicalTagDepend.analyzer(tag);
			}
//			else {
//				System.out.println("가상 태그 또는 그룹태그 입니다. - 태그키 : " + tag.getKey());
//			}
			
		}
//		System.out.println("");
//		System.out.println("");
//		System.out.println("");
//		
//		
//		Set<String> keySet = physicalMap.keySet();
//		for(String nodeId : keySet) {
//			PhysicalTagDepend physicalTagDepend = physicalMap.get(nodeId);
//			physicalTagDepend.test();
//		}
	}
}
