package com.naru.uclair.analyzer.analysis.tag;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.naru.uclair.tag.DataTag;
import com.naru.uclair.tag.ItemDeviceAdapter;
import com.naru.uclair.tag.MapDeviceAdapter;
import com.naru.uclair.tag.Tag;

/**
 * 
 * ���:�����±� ���Ӽ� Ŭ����
 * ����:������ ����̽��� �ּҰ��� ����ϴ� �±׿� ���� ������ ������ ����ִ� Ŭ���� �̴�.
 * @author parksungsoo
 *
 */
public class PhysicalTagDepend {
	/**
	 * ��� ���̵�
	 */
	private String nodeId = null;
	/**
	 * ����̽� ���̵�
	 */
	private String deviceId = null;
	
	/**
	 * �ּ����� ����Ʈ
	 * key:�ּ�
	 * value:�±� ����Ʈ
	 */
	private Map<String, List<Tag>> addressListMap = null;
	
	/**
	 * ��� ���̵�� ����̽� ���̵� �⺻ �����Ѵ�.
	 * @param nodeId ��� ���̵�
	 * @param deviceId ����̽� ���̵�
	 */
	public PhysicalTagDepend(String nodeId, String deviceId) {
		init();
		
		this.nodeId = nodeId;
		this.deviceId = deviceId;
	}
	
	/**
	 * �⺻ ������
	 */
	public PhysicalTagDepend() {
		init();
	}
	
	/**
	 * ������ �ʱ�ȭ �Ѵ�.
	 */
	private void init() {
		addressListMap = new TreeMap<String, List<Tag>>();
	}
	
	/**
	 * �־��� �±� ������ ������ �ʱ�ȭ �Ѵ�.
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
				System.out.println("*****�ߺ��� �ּҰ� ���� �±��Դϴ�.*****");
				for(Tag tag : a) {
					System.out.println("�ּҰ� : "+address + ",     �±�Ű : " + tag.getKey());
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
