package analyzer.analysis.tag;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.naru.uclair.tag.DataTag;
import com.naru.uclair.tag.ItemDeviceAdapter;
import com.naru.uclair.tag.MapDeviceAdapter;
import com.naru.uclair.tag.Tag;

/************************************************
 * @date	: 2020. 5.07.
 * @책임자 : 지이삭
 * @설명  	: 동일한 디바이스의 주소값을 사용하는 태그에 대해 조사한 내용을 담고있는 클래스 이다.
 * @변경이력 	: 
 ************************************************/


public class PhysicalTagDepend {
	/**
	 * 노드 아이디
	 */
	private String nodeId = null;
	/**
	 * 디바이스 아이디
	 */
	private String deviceId = null;
	
	/**
	 * 주소정보 리스트
	 * key:주소
	 * value:태그 리스트
	 */
	private Map<String, List<Tag>> addressListMap = null;
	
	/**
	 * 노드 아이디와 디바이스 아이디를 기본 설정한다.
	 * @param nodeId 노드 아이디
	 * @param deviceId 디바이스 아이디
	 */
	public PhysicalTagDepend(String nodeId, String deviceId) {
		init();
		
		this.nodeId = nodeId;
		this.deviceId = deviceId;
	}
	
	/**
	 * 기본 생성자
	 */
	public PhysicalTagDepend() {
		init();
	}
	
	/**
	 * 정보를 초기화 한다.
	 */
	private void init() {
		addressListMap = new TreeMap<String, List<Tag>>();
	}
	
	/**
	 * 주어진 태그 정보를 가지고 초기화 한다.
	 * @param tag 
	 */
	public void analyzer(DataTag tag) {
		String address = "";
		if(tag.getDeviceAdapter() instanceof MapDeviceAdapter) {
			address = String.valueOf(((MapDeviceAdapter)tag.getDeviceAdapter()).getAddress());
		}
		if(tag.getDeviceAdapter() instanceof ItemDeviceAdapter) {
			address = ((ItemDeviceAdapter)tag.getDeviceAdapter()).getItemName();
		}
		
		List<Tag> tagList = addressListMap.get(address);
		if(null == tagList) {
			tagList = new ArrayList<Tag>();
			addressListMap.put(address, tagList);
		}
		
		tagList.add(tag);
	}
	 
	public void test() {
		Set<String> addressSet = addressListMap.keySet();
		for(String address : addressSet) {
			List<Tag> a = addressListMap.get(address);
			if(a.size() > 1) {
				System.out.println("*****중복된 주소가 사용된 태그입니다.*****");
				for(Tag tag : a) {
					System.out.println("주소값 : "+address + ",     태그키 : " + tag.getKey());
				}
			}
		}
	}
	
	public List<Tag> getTags() {
		Set<String> addressSet = addressListMap.keySet();
		List<Tag> tagList = new ArrayList<Tag>();
		for(String address : addressSet) {
			List<Tag> a = addressListMap.get(address);
			if(a.size() > 1) {
				for(Tag tag : a) {
					tagList.add(tag);
				}
			}
		}
		return tagList;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	
}
