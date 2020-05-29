package analyzer.views.projectinfo;

import java.awt.BorderLayout;
import java.awt.Font;
import java.util.Iterator;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.DefaultCaret;

import com.naru.uclair.alarm.AlarmGroup;
import com.naru.uclair.collection.AlarmDataCollection;
import com.naru.uclair.collection.ChangeDataCollection;
import com.naru.uclair.collection.CollectionList;
import com.naru.uclair.collection.OperationDataCollection;
import com.naru.uclair.collection.TrendDataCollection;
import com.naru.uclair.database.Database;
import com.naru.uclair.device.info.DeviceGroupList;
import com.naru.uclair.event.EventGroup;
import com.naru.uclair.event.EventGroupList;
import com.naru.uclair.event.IHMIEvent;
import com.naru.uclair.node.HMINode;
import com.naru.uclair.project.AlarmConfiguration;
import com.naru.uclair.project.DataCollectionConfiguration;
import com.naru.uclair.project.DatabaseConfiguration;
import com.naru.uclair.project.DeviceConfiguration;
import com.naru.uclair.project.EventDictionary;
import com.naru.uclair.project.NetworkConfiguration;
import com.naru.uclair.project.Project;
import com.naru.uclair.project.ProjectConfiguration;
import com.naru.uclair.project.ScriptDictionary;
import com.naru.uclair.project.SecurityConfiguration;
import com.naru.uclair.project.TagDictionary;
import com.naru.uclair.security.UserGroup;
import com.naru.uclair.tag.AnalogTag;
import com.naru.uclair.tag.DataTag;
import com.naru.uclair.tag.DigitalTag;
import com.naru.uclair.tag.GroupTag;
import com.naru.uclair.tag.StringTag;
import com.naru.uclair.tag.Tag;

import analyzer.constants.AnalyzerConstants;
import analyzer.util.OpenView;
/************************************************
 * @date	: 2020. 5.07.
 * @책임자 :  지한별
 * @설명  	:   IO 테스트 
 * @변경이력 	: 
 ************************************************/
public class ProjectInfoView extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextArea systemOutTextArea;
	public static final String PROJECT_INFO_EDITOR_KEY = AnalyzerConstants
			.getString("AnalyzerEditorFactory.ProjectInfo.key"); //$NON-NLS-1$
	
	private JComponent initialize() {
		
		this.setLayout(new BorderLayout());
		systemOutTextArea = new JTextArea();		
		systemOutTextArea.setEditable(false);
		
		systemOutTextArea.setFont(new Font("Fixedsys", Font.BOLD, 17));
		
		DefaultCaret caret = (DefaultCaret)
				systemOutTextArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
		
		JScrollPane resultTableScrollPane = new JScrollPane();
		resultTableScrollPane.setViewportView(systemOutTextArea);
		
		return resultTableScrollPane;
	}

	public ProjectInfoView(String key) {
		OpenView resultview = new OpenView();
		resultview.ResultView(initialize(), PROJECT_INFO_EDITOR_KEY);
	}

	public void appendText(String message) {
		systemOutTextArea.append(message);
	}
	
	public void setText(String message) {
		systemOutTextArea.setText(message);
	}
	
	/**
	 * 프로젝트를 설정하면서 프로젝트에 대한 요약 정보를 보여준다.
	 * @param p
	 */
	public void setProject(Project p) {
		ProjectConfiguration config = p.getProjectConfiguration();
		this.appendText("<< 프로젝트 정보 >>\n");
		this.appendText("\n프로젝트 명 : ");
		this.appendText(p.getProjectConfiguration().getName());
		this.appendText("\n");
		this.appendText("프로젝트 경로 : ");
		this.appendText(p.getProjectPath().getPath().substring(1));
		this.appendText("\n");
		this.appendText("화면 기본 해상도  너비 : " + config.getResolution().getWidth() + " 높이 : " + config.getResolution().getHeight());
		this.appendText("\n");
		this.appendText("프로젝트 설명 : ");
		this.appendText(config.getDescription());
		this.appendText("\n");
		this.appendText("저장 위치 : ");
		this.appendText((0 == config.getStoringType()) ? "FILE" : "DB");
		this.appendText("\n");
		this.appendText("데몬 서비스 포트 : " + config.getPortInformation().getDemonPort());
		this.appendText("\n");
		this.appendText("데이터 서비스 포트 : " + config.getPortInformation().getDataIntegrationServerPort());
		this.appendText("\n");
		this.appendText("IO서버 서비스 포트 : " + config.getPortInformation().getIoServerPort());
		this.appendText("\n");
		
		this.appendText("\n\n");
		initDatabase(p);
		this.appendText("\n\n");
		initNetwork(p);
		this.appendText("\n");
		initAlarmGroup(p);
		this.appendText("\n");
		initTagDictionary(p);
		this.appendText("\n\n");
		initDevice(p);
		this.appendText("\n\n");
		initDataCollection(p);
		this.appendText("\n");
		initEvent(p);
		this.appendText("\n");
		initScriptDictionary(p);
		this.appendText("\n\n");
		initUser(p);
	}
	
	/**
	 * 태그사전 정보를 표시한다.
	 * @param p
	 */
	private void initTagDictionary(Project p) {
		TagDictionary tagDictionary = p.getTagDictionary();
		int digitalCount = 0;
		int totalCount = 0;
		int analogCount = 0;
		int stringCount = 0;
		int groupCount = 0;
		int virtualCount = 0;
		int physicalCount = 0;
		
		Iterator<Tag> tagIt = tagDictionary.getAllTags();
		while(tagIt.hasNext()) {
			totalCount = totalCount + 1;
			Tag tag = tagIt.next();
			if(tag instanceof DataTag) {
				if(tag instanceof GroupTag) {
				}
				else {
					physicalCount = physicalCount + 1;
				}
			}
			else {
				virtualCount = virtualCount + 1;
			}
			
			if(tag instanceof DigitalTag) {
				digitalCount = digitalCount + 1;
			}
			else if(tag instanceof AnalogTag) {
				analogCount = analogCount + 1;
			}
			else if(tag instanceof StringTag) {
				stringCount = stringCount + 1;
			}
			else if(tag instanceof GroupTag) {
				groupCount = groupCount + 1;
			}
		}
		
		this.appendText("#####<태그사전 정보>#####\n");
		this.appendText("\n");
		this.appendText("총 태그 수 : " + totalCount);
		this.appendText(", 가상 태그 수 : " + virtualCount);
		this.appendText(", 물리 태그 수 : " + physicalCount);
		this.appendText("\n");
		this.appendText("디지털 태그 수 : " + digitalCount);
		this.appendText(", 아날로그 태그 수 : " + analogCount);
		this.appendText(", 문자열 태그 수 : " + stringCount);
		this.appendText(", 그룹 태그 수 : " + groupCount);
		this.appendText("\n");
	}
	
	private void initDatabase(Project p) {
		DatabaseConfiguration configuration = p.getDatabaseConfiguration();
		this.appendText("####<데이터베이스 정보>####\n");
		this.appendText("\n");
		this.appendText("마스터 저장 데이터베이스 : " + ((null == configuration.getMasterDatabase()) ? "없음" : configuration.getMasterDatabase()));
		this.appendText("\n");
		Iterator<Database> dbIt = configuration.getAllDatabases();
		while(dbIt.hasNext()) {
			Database db = dbIt.next();
			this.appendText("데이터베이스 명 : " + db.getName());
			this.appendText("\n");
			this.appendText("DB SID : " + db.getConnectionInfo().getDatabaseName());
			this.appendText("\n");
			this.appendText("DB 주소 : " + db.getConnectionInfo().getAddress());
			this.appendText("\n");
		}
	}
	
	
	private void initNetwork(Project p) {
		NetworkConfiguration configuration = p.getNetworkConfiguration();
		Iterator<HMINode> ntIt = configuration.getAllNodes();
		this.appendText("#####<네트워크 정보>#####\n");
		this.appendText("\n");
		while(ntIt.hasNext()) {
			HMINode node = ntIt.next();
			this.appendText("노드 명 : " + node.getName());
			this.appendText("\n");
			this.appendText("노드 주소 : " + node.getNetworkInfo().getAddress());
			this.appendText("\n");
			this.appendText("역할 : ");
			this.appendText(((node.existWebServerRole()) ? "웝세버, " : ""));
			this.appendText(((node.existAppServerRole()) ? "웝 어플리케이션 서버, " : ""));
			this.appendText(((node.existDISRole()) ? "데이터 서버, " : ""));
			this.appendText(((node.existIORole()) ? "I/O 서버\n\n" : "\n\n"));
		}
	}
	
	private void initAlarmGroup(Project p) {
		AlarmConfiguration configuration = p.getAlarmConfiguration();
		this.appendText("#####<경보그룹 정보>#####\n");
		this.appendText("\n");
		Iterator<AlarmGroup> alarmGroupIt = configuration.getAllAlarmGroups().iterator();
		while(alarmGroupIt.hasNext()) {
			AlarmGroup alarmGroup = alarmGroupIt.next();
			this.appendText("경보 명 : " + alarmGroup.getName());
			this.appendText("\n");
			this.appendText("경보 설명 : " + alarmGroup.getName());
			this.appendText("\n\n");
		}
	}
	
	private void initDevice(Project p) {
		DeviceConfiguration configuration = p.getDeviceConfiguration();
		this.appendText("#####<디바이스 정보>#####\n");
		Iterator<DeviceGroupList> dgIt = configuration.getAllDeviceList();
		while(dgIt.hasNext()) {
			DeviceGroupList groupList = dgIt.next();
			this.appendText("\n");
			this.appendText("디바이스 노드 : " + groupList.getName());
			this.appendText("\n");
			this.appendText("노드 디바이스 수 : " + groupList.getAllDevices().size());
			this.appendText("\n");
//			for(DeviceInfo device : groupList.getAllDevices()) {
//				this.appendText("디바이스 명 : " + device.getDeviceID());
//				this.appendText("\n");
//				this.appendText("디바이스 설명 : " + device.getDescription());
//				this.appendText("\n");
//				this.appendText("장비 명 : " + device.getDriverEquipName());
//				this.appendText("\n");
//				this.appendText("장비 타입 : " + device.getDriverEquipType());
//				this.appendText("\n");
//				this.appendText("프로토콜 명 : " + device.getMasterConnectionInfo().getProtocolID());
//				this.appendText("\n\n");
//			}
		}
	}
	
	private void initDataCollection(Project p) {
		DataCollectionConfiguration configuration = p.getDataCollectionConfiguration();
		Iterator<CollectionList> clistIt  = configuration.getAllCollectionList().iterator();
		this.appendText("####<데이터 수집환경 정보>####\n");
		this.appendText("\n");
		while(clistIt.hasNext()) {
			CollectionList collectionList = clistIt.next();
			this.appendText("데이터 수집환경 노드 명 : " + collectionList.getName());
			this.appendText("\n");
			AlarmDataCollection alarmDataCollection = collectionList.getAlarmCollection();
			this.appendText("경보 데이터 수 : " + alarmDataCollection.getCollectionList().size());
			this.appendText("\n");
			OperationDataCollection operationDataCollection = collectionList.getOperationCollection();
			this.appendText("가동 데이터 수 : " + operationDataCollection.getCollectionList().size());
			this.appendText("\n");
			ChangeDataCollection changeDataCollection = collectionList.getChangeCollection();
			this.appendText("값변경 데이터 수 : " + changeDataCollection.getCollectionList().size());
			this.appendText("\n");
			int count = 0;
			for(TrendDataCollection tCollection : collectionList.getMinuteTrendCollections()) {
				count = count + tCollection.getCollectionList().size();
			}
			this.appendText("분주기 데이터 수 : " + count);
			this.appendText("\n");
			count = 0;
			for(TrendDataCollection tCollection : collectionList.getHourTrendCollections()) {
				count = count + tCollection.getCollectionList().size();
			}
			this.appendText("시주기 데이터 수 : " + count);
			this.appendText("\n");
			count = 0;
			for(TrendDataCollection tCollection : collectionList.getDayTrendCollections()) {
				count = count + tCollection.getCollectionList().size();
			}
			this.appendText("일주기 데이터 수 : " + count);
			this.appendText("\n\n");
		}
	}
	
	private void initEvent(Project p) {
		EventDictionary eventDictionary = p.getEventDictionary();
		Iterator<EventGroupList> egIt = eventDictionary.getEventGroupLists().iterator();
		this.appendText("####<이벤트 사전 정보>####\n");
		this.appendText("\n");
		while(egIt.hasNext()) {
			EventGroupList eGroupList = egIt.next();
			this.appendText("이벤트 노드 : " + eGroupList.getName());
			this.appendText("\n");
			EventGroup eventGroup = eGroupList.getEventGroup(IHMIEvent.TYPE_DATACHANGE);
			this.appendText("태그값 변경 이벤트 수 : " + eventGroup.getEvents().toArray().length);
			this.appendText("\n");
			eventGroup = eGroupList.getEventGroup(IHMIEvent.TYPE_ALARM);
			this.appendText("경보 이벤트 수 : " + eventGroup.getEvents().toArray().length);
			this.appendText("\n");
			eventGroup = eGroupList.getEventGroup(IHMIEvent.TYPE_TIME);
			this.appendText("시간 이벤트 수 : " + eventGroup.getEvents().toArray().length);
			this.appendText("\n\n");
		}
	}
	
	private void initScriptDictionary(Project p) {
		ScriptDictionary scriptDictionary = p.getScriptDictionary();
		this.appendText("####<사용자 함수 사전 정보>####\n");
		this.appendText("\n");
		this.appendText("사용자 함수 수 : " + scriptDictionary.getAllScripts().toArray().length);
		this.appendText("\n");
	}

	private void initUser(Project p) {
		SecurityConfiguration configuration = p.getSecurityConfiguration();
		Iterator<UserGroup> userGroupIt = configuration.getUserGroupList().iterator();
		this.appendText("####<사용자 환경 정보>####\n");
		this.appendText("\n");
		while(userGroupIt.hasNext()) {
			UserGroup userGroup = userGroupIt.next();
			this.appendText("사용자 노드 : " + userGroup.getName());
			this.appendText("\n");
			this.appendText("사용자 수: " + userGroup.getAllUsers().size());
			this.appendText("\n\n");
		}
	}
}
