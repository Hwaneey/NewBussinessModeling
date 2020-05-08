package com.naru.uclair.analyzer.views.projectinfo;

import java.awt.BorderLayout;
import java.util.Iterator;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.naru.uclair.alarm.AlarmGroup;
import com.naru.uclair.collection.AlarmDataCollection;
import com.naru.uclair.collection.ChangeDataCollection;
import com.naru.uclair.collection.CollectionList;
import com.naru.uclair.collection.OperationDataCollection;
import com.naru.uclair.collection.TrendDataCollection;
import com.naru.uclair.database.Database;
import com.naru.uclair.device.info.DeviceGroupList;
import com.naru.uclair.device.info.DeviceInfo;
import com.naru.uclair.event.EventGroup;
import com.naru.uclair.event.EventGroupList;
import com.naru.uclair.event.IHMIEvent;
import com.naru.uclair.node.HMINode;
import com.naru.uclair.project.AlarmConfiguration;
import com.naru.uclair.project.DataCollectionConfiguration;
import com.naru.uclair.project.DatabaseConfiguration;
import com.naru.uclair.project.DeviceConfiguration;
import com.naru.uclair.project.EventDictionary;
import com.naru.uclair.project.IProjectConstants;
import com.naru.uclair.project.NetworkConfiguration;
import com.naru.uclair.project.Project;
import com.naru.uclair.project.ProjectConfiguration;
import com.naru.uclair.project.ScriptDictionary;
import com.naru.uclair.project.SecurityConfiguration;
import com.naru.uclair.project.TagDictionary;
import com.naru.uclair.script.Script;
import com.naru.uclair.security.UserGroup;
import com.naru.uclair.tag.AnalogTag;
import com.naru.uclair.tag.DataTag;
import com.naru.uclair.tag.DigitalTag;
import com.naru.uclair.tag.GroupTag;
import com.naru.uclair.tag.StringTag;
import com.naru.uclair.tag.Tag;

public class ProjectInfoView extends JPanel {

	private static final long serialVersionUID = 1L;

	private JTextArea systemOutTextArea;

	private void initialize() {
		this.setLayout(new BorderLayout());
		systemOutTextArea = new JTextArea();
		systemOutTextArea.setEditable(false);

		this.add(new JScrollPane(systemOutTextArea), BorderLayout.CENTER);
	}

	public ProjectInfoView(String key) {
		initialize();
	}

	public void appendText(String message) {
		systemOutTextArea.append(message);
	}
	
	public void setText(String message) {
		systemOutTextArea.setText(message);
	}
	
	/**
	 * ������Ʈ�� �����ϸ鼭 ������Ʈ�� ���� ��� ������ �����ش�.
	 * @param p
	 */
	public void setProject(Project p) {
		ProjectConfiguration config = p.getProjectConfiguration();
		this.appendText("\n<< ������Ʈ ���� >>\n");
		this.appendText("\n������Ʈ �� : ");
		this.appendText(p.getProjectConfiguration().getName());
		this.appendText("\n");
		this.appendText("������Ʈ ��� : ");
		this.appendText(p.getProjectPath().toString());
		this.appendText("\n");
		this.appendText("ȭ�� �⺻ �ػ�  �ʺ� : " + config.getResolution().getWidth() + " ���� : " + config.getResolution().getHeight());
		this.appendText("\n");
		this.appendText("������Ʈ ���� : ");
		this.appendText(config.getDescription());
		this.appendText("\n");
		this.appendText("���� ��ġ : ");
		this.appendText((0 == config.getStoringType()) ? "FILE" : "DB");
		this.appendText("\n");
		this.appendText("���� ���� ��Ʈ : " + config.getPortInformation().getDemonPort());
		this.appendText("\n");
		this.appendText("������ ���� ��Ʈ : " + config.getPortInformation().getDataIntegrationServerPort());
		this.appendText("\n");
		this.appendText("IO���� ���� ��Ʈ : " + config.getPortInformation().getIoServerPort());
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
	 * �±׻��� ������ ǥ���Ѵ�.
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
		
		this.appendText("#####<�±׻��� ����>#####\n");
		this.appendText("�� �±� �� : " + totalCount);
		this.appendText(", ���� �±� �� : " + virtualCount);
		this.appendText(", ���� �±� �� : " + physicalCount);
		this.appendText("\n");
		this.appendText("������ �±� �� : " + digitalCount);
		this.appendText(", �Ƴ��α� �±� �� : " + analogCount);
		this.appendText(", ���ڿ� �±� �� : " + stringCount);
		this.appendText(", �׷� �±� �� : " + groupCount);
		this.appendText("\n");
	}
	
	private void initDatabase(Project p) {
		DatabaseConfiguration configuration = p.getDatabaseConfiguration();
		this.appendText("####<�����ͺ��̽� ����>####\n");
		this.appendText("������ ���� �����ͺ��̽� : " + ((null == configuration.getMasterDatabase()) ? "����" : configuration.getMasterDatabase()));
		this.appendText("\n");
		Iterator<Database> dbIt = configuration.getAllDatabases();
		while(dbIt.hasNext()) {
			Database db = dbIt.next();
			this.appendText("�����ͺ��̽� �� : " + db.getName());
			this.appendText("\n");
			this.appendText("DB SID : " + db.getConnectionInfo().getDatabaseName());
			this.appendText("\n");
			this.appendText("DB �ּ� : " + db.getConnectionInfo().getAddress());
			this.appendText("\n");
		}
	}
	
	
	private void initNetwork(Project p) {
		NetworkConfiguration configuration = p.getNetworkConfiguration();
		Iterator<HMINode> ntIt = configuration.getAllNodes();
		this.appendText("#####<��Ʈ��ũ ����>#####\n");
		this.appendText("\n");
		while(ntIt.hasNext()) {
			HMINode node = ntIt.next();
			this.appendText("��� �� : " + node.getName());
			this.appendText("\n");
			this.appendText("��� �ּ� : " + node.getNetworkInfo().getAddress());
			this.appendText("\n");
			this.appendText("���� : ");
			this.appendText(((node.existWebServerRole()) ? "������, " : ""));
			this.appendText(((node.existAppServerRole()) ? "�� ���ø����̼� ����, " : ""));
			this.appendText(((node.existDISRole()) ? "������ ����, " : ""));
			this.appendText(((node.existIORole()) ? "I/O ����\n\n" : "\n\n"));
		}
	}
	
	private void initAlarmGroup(Project p) {
		AlarmConfiguration configuration = p.getAlarmConfiguration();
		this.appendText("#####<�溸�׷� ����>#####\n");
		Iterator<AlarmGroup> alarmGroupIt = configuration.getAllAlarmGroups().iterator();
		while(alarmGroupIt.hasNext()) {
			AlarmGroup alarmGroup = alarmGroupIt.next();
			this.appendText("�溸 �� : " + alarmGroup.getName());
			this.appendText("\n");
			this.appendText("�溸 ���� : " + alarmGroup.getName());
			this.appendText("\n\n");
		}
	}
	
	private void initDevice(Project p) {
		DeviceConfiguration configuration = p.getDeviceConfiguration();
		this.appendText("#####<����̽� ����>#####\n");
		Iterator<DeviceGroupList> dgIt = configuration.getAllDeviceList();
		while(dgIt.hasNext()) {
			DeviceGroupList groupList = dgIt.next();
			this.appendText("\n");
			this.appendText("����̽� ��� : " + groupList.getName());
			this.appendText("\n");
			this.appendText("��� ����̽� �� : " + groupList.getAllDevices().size());
			this.appendText("\n");
//			for(DeviceInfo device : groupList.getAllDevices()) {
//				this.appendText("����̽� �� : " + device.getDeviceID());
//				this.appendText("\n");
//				this.appendText("����̽� ���� : " + device.getDescription());
//				this.appendText("\n");
//				this.appendText("��� �� : " + device.getDriverEquipName());
//				this.appendText("\n");
//				this.appendText("��� Ÿ�� : " + device.getDriverEquipType());
//				this.appendText("\n");
//				this.appendText("�������� �� : " + device.getMasterConnectionInfo().getProtocolID());
//				this.appendText("\n\n");
//			}
		}
	}
	
	private void initDataCollection(Project p) {
		DataCollectionConfiguration configuration = p.getDataCollectionConfiguration();
		Iterator<CollectionList> clistIt  = configuration.getAllCollectionList().iterator();
		this.appendText("####<������ ����ȯ�� ����>####\n");
		this.appendText("\n");
		while(clistIt.hasNext()) {
			CollectionList collectionList = clistIt.next();
			this.appendText("������ ����ȯ�� ��� �� : " + collectionList.getName());
			this.appendText("\n");
			AlarmDataCollection alarmDataCollection = collectionList.getAlarmCollection();
			this.appendText("�溸 ������ �� : " + alarmDataCollection.getCollectionList().size());
			this.appendText("\n");
			OperationDataCollection operationDataCollection = collectionList.getOperationCollection();
			this.appendText("���� ������ �� : " + operationDataCollection.getCollectionList().size());
			this.appendText("\n");
			ChangeDataCollection changeDataCollection = collectionList.getChangeCollection();
			this.appendText("������ ������ �� : " + changeDataCollection.getCollectionList().size());
			this.appendText("\n");
			int count = 0;
			for(TrendDataCollection tCollection : collectionList.getMinuteTrendCollections()) {
				count = count + tCollection.getCollectionList().size();
			}
			this.appendText("���ֱ� ������ �� : " + count);
			this.appendText("\n");
			count = 0;
			for(TrendDataCollection tCollection : collectionList.getHourTrendCollections()) {
				count = count + tCollection.getCollectionList().size();
			}
			this.appendText("���ֱ� ������ �� : " + count);
			this.appendText("\n");
			count = 0;
			for(TrendDataCollection tCollection : collectionList.getDayTrendCollections()) {
				count = count + tCollection.getCollectionList().size();
			}
			this.appendText("���ֱ� ������ �� : " + count);
			this.appendText("\n\n");
		}
	}
	
	private void initEvent(Project p) {
		EventDictionary eventDictionary = p.getEventDictionary();
		Iterator<EventGroupList> egIt = eventDictionary.getEventGroupLists().iterator();
		this.appendText("####<�̺�Ʈ ���� ����>####\n");
		this.appendText("\n");
		while(egIt.hasNext()) {
			EventGroupList eGroupList = egIt.next();
			this.appendText("�̺�Ʈ ��� : " + eGroupList.getName());
			this.appendText("\n");
			EventGroup eventGroup = eGroupList.getEventGroup(IHMIEvent.TYPE_DATACHANGE);
			this.appendText("�±װ� ���� �̺�Ʈ �� : " + eventGroup.getEvents().toArray().length);
			this.appendText("\n");
			eventGroup = eGroupList.getEventGroup(IHMIEvent.TYPE_ALARM);
			this.appendText("�溸 �̺�Ʈ �� : " + eventGroup.getEvents().toArray().length);
			this.appendText("\n");
			eventGroup = eGroupList.getEventGroup(IHMIEvent.TYPE_TIME);
			this.appendText("�ð� �̺�Ʈ �� : " + eventGroup.getEvents().toArray().length);
			this.appendText("\n\n");
		}
	}
	
	private void initScriptDictionary(Project p) {
		ScriptDictionary scriptDictionary = p.getScriptDictionary();
		this.appendText("####<����� �Լ� ���� ����>####\n");
		this.appendText("\n");
		this.appendText("����� �Լ� �� : " + scriptDictionary.getAllScripts().toArray().length);
		this.appendText("\n");
	}

	private void initUser(Project p) {
		SecurityConfiguration configuration = p.getSecurityConfiguration();
		Iterator<UserGroup> userGroupIt = configuration.getUserGroupList().iterator();
		this.appendText("####<����� ȯ�� ����>####\n");
		this.appendText("\n");
		while(userGroupIt.hasNext()) {
			UserGroup userGroup = userGroupIt.next();
			this.appendText("����� ��� : " + userGroup.getName());
			this.appendText("\n");
			this.appendText("����� ��: " + userGroup.getAllUsers().size());
			this.appendText("\n\n");
		}
	}
}
