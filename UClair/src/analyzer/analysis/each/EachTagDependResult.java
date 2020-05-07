package analyzer.analysis.each;

import java.util.LinkedList;
import java.util.List;

import com.naru.uclair.collection.ICollectionConstants;
import com.naru.uclair.event.IHMIEvent;
import com.naru.uclair.tag.DataTag;
import com.naru.uclair.tag.DeviceAdapter;
import com.naru.uclair.tag.Tag;

/************************************************
 * @date	: 2020. 5.07.
 * @책임자 : 이승환
 * @설명  	: EachTagDependResult
 * @변경이력 	: 
 ************************************************/

public class EachTagDependResult {
	
	private Tag targetTag;
	private List<EachTagEventDependResult> eventDependList;
	private List<EachTagDataCollectionDependResult> dataCollectionDependList;
	private List<EachTagScriptDependResult> scriptDependList;
	private List<EachTagWindowDependResult> windowDependList;
	
	public EachTagDependResult() {
		eventDependList = new LinkedList<EachTagEventDependResult>();
		dataCollectionDependList = new LinkedList<EachTagDataCollectionDependResult>();
		scriptDependList = new LinkedList<EachTagScriptDependResult>();
		windowDependList = new LinkedList<EachTagWindowDependResult>();
	}
	/**
	 * @return the tagId
	 */
	public String getTagId() {
		if(null == targetTag) {
			return null;
		}
		return targetTag.getKey();
	}

	/**
	 * @return the nodeName
	 */
	public String getNodeName() {
		if(null == targetTag) {
			return null;
		}
		return targetTag.getNode();
	}

	/**
	 * @return the dataType
	 */
	public int getDataType() {
		if(null == targetTag) {
			return -1;
		}
		return targetTag.getDataType();
	}

	/**
	 * @return the deviceName
	 */
	public String getDeviceName() {
		if(null == targetTag) return null;
		
		if(!(targetTag instanceof DataTag)) return null;
		
		DataTag dataTag = (DataTag) targetTag;
		DeviceAdapter deviceAdapter = dataTag.getDeviceAdapter();
		if(null == deviceAdapter) return null;
		
		return deviceAdapter.getDeviceName();
	}

	public boolean isUseAlarmCollection() {
		if(dataCollectionDependList.isEmpty()) return false;
		
		for(EachTagDataCollectionDependResult dependResult : dataCollectionDependList) {
			if(ICollectionConstants.TYPE_ALARM == dependResult.getCollectionType()) {
				return true;
			}
		}
		
		return false;
	}
	
	public boolean isUseOperationCollection() {
		if(dataCollectionDependList.isEmpty()) return false;
		
		for(EachTagDataCollectionDependResult dependResult : dataCollectionDependList) {
			if(ICollectionConstants.TYPE_OPERATION == dependResult.getCollectionType()) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isUseChangeCollection() {
		if(dataCollectionDependList.isEmpty()) return false;
		
		for(EachTagDataCollectionDependResult dependResult : dataCollectionDependList) {
			if(ICollectionConstants.TYPE_CHANGE == dependResult.getCollectionType()) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isUseMinTrendCollection() {
		if(dataCollectionDependList.isEmpty()) return false;
		
		for(EachTagDataCollectionDependResult dependResult : dataCollectionDependList) {
			if(ICollectionConstants.TYPE_MINUTE_TREND == dependResult.getCollectionType()) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isUseHourTrendCollection() {
		if(dataCollectionDependList.isEmpty()) return false;
		
		for(EachTagDataCollectionDependResult dependResult : dataCollectionDependList) {
			if(ICollectionConstants.TYPE_HOUR_TREND == dependResult.getCollectionType()) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isUseDayTrendCollection() {
		if(dataCollectionDependList.isEmpty()) return false;
		
		for(EachTagDataCollectionDependResult dependResult : dataCollectionDependList) {
			if(ICollectionConstants.TYPE_DAY_TREND == dependResult.getCollectionType()) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isUseTagEvent() {
		if(eventDependList.isEmpty()) return false;
		
		for(EachTagEventDependResult dependResult : eventDependList) {
			if(IHMIEvent.TYPE_DATACHANGE == dependResult.getEventType()) {
				return true;
			}
		}
		
		return false;
	}
	
	public boolean isUseAlarmEvent() {
		if(eventDependList.isEmpty()) return false;
		
		for(EachTagEventDependResult dependResult : eventDependList) {
			if(IHMIEvent.TYPE_ALARM == dependResult.getEventType()) {
				return true;
			}
		}
		
		return false;
	}

	public boolean isUseTimeEvent() {
		if(eventDependList.isEmpty()) return false;
		
		for(EachTagEventDependResult dependResult : eventDependList) {
			if(IHMIEvent.TYPE_TIME == dependResult.getEventType()) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isUseScriptFunction() {
		return !scriptDependList.isEmpty();
	}
	
	public boolean isUseWindow() {
		return !windowDependList.isEmpty();
	}
	
	public void setTag(Tag targetTag) {
		this.targetTag = targetTag;
	}
	
	public void addEachTagEventDependResult(EachTagEventDependResult dependResult) {
		eventDependList.add(dependResult);
	}
	
	public void addEachTagDataCollectionDependList(EachTagDataCollectionDependResult dependResult) {
		dataCollectionDependList.add(dependResult);
	}
	
	public void addEachTagScriptDependResult(EachTagScriptDependResult dependResult) {
		scriptDependList.add(dependResult);
	}
	
	public void addEachTagWindowDependResult(EachTagWindowDependResult dependResult) {
		windowDependList.add(dependResult);
	}
}
