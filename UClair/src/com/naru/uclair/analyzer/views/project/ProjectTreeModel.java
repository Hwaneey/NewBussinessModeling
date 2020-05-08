package com.naru.uclair.analyzer.views.project;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.net.URI;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;

import javax.swing.event.EventListenerList;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import com.naru.uclair.analyzer.constants.AnalyzerConstants;
import com.naru.uclair.database.Database;
import com.naru.uclair.listeners.DatabaseConfigurationChangeListener;
import com.naru.uclair.listeners.NetworkConfigurationChangeListener;
import com.naru.uclair.node.HMINode;
import com.naru.uclair.project.DatabaseConfiguration;
import com.naru.uclair.project.IProjectConstants;
import com.naru.uclair.project.NetworkConfiguration;
import com.naru.uclair.project.Project;


/**
 * <p>Title: iA-Canvas</p>
 * <p>Description: 모니터링 자동화 툴</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: (주) 나루기술</p>
 * @author 정해문
 * @version 1.0
 */

public class ProjectTreeModel 
		implements TreeModel, NetworkConfigurationChangeListener, 
				PropertyChangeListener, DatabaseConfigurationChangeListener {

    public static final String PROJECT_CONFIG = AnalyzerConstants.getString("ProjectTreeModel.projectinfo.node.name"); //$NON-NLS-1$
    public static final String NETWORK_CONFIG = AnalyzerConstants.getString("ProjectTreeModel.network.node.name"); //$NON-NLS-1$
    public static final String ALARM_CONFIG = AnalyzerConstants.getString("ProjectTreeModel.alarm.node.name"); //$NON-NLS-1$
    public static final String TAG_CONFIG = AnalyzerConstants.getString("ProjectTreeModel.tag.node.name"); //$NON-NLS-1$
    public static final String DEVICE_CONFIG = AnalyzerConstants.getString("ProjectTreeModel.device.node.name"); //$NON-NLS-1$
    public static final String DATA_COLLECT_CONFIG = AnalyzerConstants.getString("ProjectTreeModel.datacollect.node.name"); //$NON-NLS-1$
    public static final String EVENT_CONFIG = AnalyzerConstants.getString("ProjectTreeModel.event.node.name"); //$NON-NLS-1$
    public static final String DATABASE_CONFIG = AnalyzerConstants.getString("ProjectTreeModel.database.node.name"); //$NON-NLS-1$
    public static final String USER_CONFIG = AnalyzerConstants.getString("ProjectTreeModel.user.node.name"); //$NON-NLS-1$
    public static final String SCRIPT_CONFIG = AnalyzerConstants.getString("ProjectTreeModel.script.node.name"); //$NON-NLS-1$
    public static final String WINDOW_CONFIG = AnalyzerConstants.getString("ProjectTreeModel.window.node.name"); //$NON-NLS-1$
    
    public static final String PROTOCOL_CONFIG = AnalyzerConstants.getString("ProjectTreeModel.protocol.node.name"); //$NON-NLS-1$
    public static final String GIS_CONFIG = AnalyzerConstants.getString("ProjectTreeModel.GIS.node.name");
    
    public static final String ALARM_GROUP = AnalyzerConstants.getString("ProjectTreeModel.alarm.childnode.AlarmGroup"); //$NON-NLS-1$
    public static final String UMS = "UMS"; //$NON-NLS-1$
    public static final String POPUP = AnalyzerConstants.getString("ProjectTreeModel.alarm.childnode.Popup"); //$NON-NLS-1$
    public static final String SOUND = AnalyzerConstants.getString("ProjectTreeModel.alarm.childnode.Sound"); //$NON-NLS-1$

    protected EventListenerList listenerList = new EventListenerList();

    private DefaultMutableTreeNode root = null;

    private Hashtable<String, MutableTreeNode> configurationNodeTable;
    
    private Project project = null;

    private URI projectPath = null;

    private NetworkConfiguration networkConfiguration = null;
    
    private DatabaseConfiguration databaseConfiguration = null;

//    private WindowEditor windowEditor = null;

    public ProjectTreeModel() {
    	this(null);
    }
    public ProjectTreeModel(Project p) {
    	configurationNodeTable = new Hashtable<String, MutableTreeNode> ();
    	
        initNode();
        setProject(p);
    }

    /**
     * 기본 프로젝트 트리의 root를 생성한다. (null)
     */
    private void initNode() {
    	root = null;
    }
    /**
     * 지정된 프로젝트의 트리를 생성한다.
     */
    private void initProjectNodes() {
    	DefaultMutableTreeNode projectRoot = 
    		new DefaultMutableTreeNode(project.getProjectConfiguration().getName(), true);
    	
    	// 프로젝트 정보.
    	MutableTreeNode node = new DefaultMutableTreeNode(PROJECT_CONFIG, false);
    	configurationNodeTable.put(PROJECT_CONFIG, node);
    	projectRoot.add(node);
    	
    	// 데이터 베이스 환경 노드.
    	node = new DefaultMutableTreeNode(DATABASE_CONFIG, true);
    	configurationNodeTable.put(DATABASE_CONFIG, node);
    	projectRoot.add(node);
    	// DATABASE_CONFIG 노드의 하위 노드 구성.
    	Iterator<Database> databaseNodelist = project.getDatabaseConfiguration().getAllDatabases();
    	while (databaseNodelist.hasNext()) {
    		DatabaseTreeNode child = new DatabaseTreeNode(databaseNodelist.next());
    		if (null != child) {
    			((DefaultMutableTreeNode) node).add(child);
    			
    			// Node명 변경시 화면 업데이트를 위하여 리슨너 등록
    			child.getDatabase().addPropertyChangeListener(this);
    		}
    	}
    	
    	// 네트워크 노드.
    	node = new DefaultMutableTreeNode(NETWORK_CONFIG, true);
    	configurationNodeTable.put(NETWORK_CONFIG, node);
    	projectRoot.add(node);
    	// NETWORK_CONFIG 노드의 하위 노드 구성.
    	Iterator<HMINode> nodelist = project.getNetworkConfiguration().getAllNodes();
    	while (nodelist.hasNext()) {
    		HMINodeTreeNode child = new HMINodeTreeNode(nodelist.next());
    		if (null != child) {
    			((DefaultMutableTreeNode) node).add(child);
    			
    			// Node명 변경시 화면 업데이트를 위하여 리슨너 등록
    			child.getNode().addPropertyChangeListener(this);
    		}
    	}
    	
    	// 경보 환경 노드.
    	node = new DefaultMutableTreeNode(ALARM_CONFIG, true);
    	configurationNodeTable.put(ALARM_CONFIG, node);
    	projectRoot.add(node);
    	((DefaultMutableTreeNode) node).add(new DefaultMutableTreeNode(ALARM_GROUP, false));
    	((DefaultMutableTreeNode) node).add(new DefaultMutableTreeNode(UMS, false));
//    	((DefaultMutableTreeNode) node).add(new DefaultMutableTreeNode(POPUP, false));
//    	((DefaultMutableTreeNode) node).add(new DefaultMutableTreeNode(SOUND, false));
    	
    	// 태그 사전 노드.
    	node = new DefaultMutableTreeNode(TAG_CONFIG, false);
    	configurationNodeTable.put(TAG_CONFIG, node);
    	projectRoot.add(node);
    	
    	/**
    	 * 프로토콜 편집 노드 추가 삽입 - uClair 2.0 에서는 빠짐.
    	 * 작성 일자 : 2010. 09. 15.
    	 * 작성자 : 박용우
    	 * 작성 내용 : uClair 2.0 에서는 빠짐.
    	node = new DefaultMutableTreeNode(PROTOCOL_CONFIG, false);
    	configurationNodeTable.put(PROTOCOL_CONFIG, node);
    	projectRoot.add(node);
    	 */

    	// 디바이스 환경 노드.
    	node = new DefaultMutableTreeNode(DEVICE_CONFIG, false);
    	configurationNodeTable.put(DEVICE_CONFIG, node);
    	projectRoot.add(node);
    	
    	// 데이터 수집환경 노드.
    	node = new DefaultMutableTreeNode(DATA_COLLECT_CONFIG, false);
    	configurationNodeTable.put(DATA_COLLECT_CONFIG, node);
    	projectRoot.add(node);

    	// 이벤트 사전 노드.
    	node = new DefaultMutableTreeNode(EVENT_CONFIG, false);
    	configurationNodeTable.put(EVENT_CONFIG, node);
    	projectRoot.add(node);

    	// 사용자 함수 사전 노드.
    	node = new DefaultMutableTreeNode(SCRIPT_CONFIG, false);
    	configurationNodeTable.put(SCRIPT_CONFIG, node);
    	projectRoot.add(node);

    	// 사용자 환경 노드.
    	node = new DefaultMutableTreeNode(USER_CONFIG, false);
    	configurationNodeTable.put(USER_CONFIG, node);
    	projectRoot.add(node);
    	
    	// GIS 환경 노드.
    	node = new DefaultMutableTreeNode(GIS_CONFIG, false);
    	configurationNodeTable.put(GIS_CONFIG, node);
    	projectRoot.add(node);
    	
    	// 화면 목록 트리 노드 생성.
    	File windowFile = new File(project.getProjectPath().getPath(), IProjectConstants.WINDOW_PATH);
    	node = new WindowTreeNode(WINDOW_CONFIG, windowFile);

    	File[] fileList = windowFile.listFiles(WindowTreeNode.filter);
    	if(null != fileList){
    		for(File file : fileList){
    			if(!file.isDirectory()){
    				WindowNameNode wnn = new WindowNameNode(file);
    				node.insert(wnn, node.getChildCount());
    			}
    		}
    	}
    	configurationNodeTable.put(WINDOW_CONFIG, node);
    	projectRoot.add(node);
    	
        setRoot(projectRoot);
    }
    
    public MutableTreeNode getConfigurationNode(String key) {
    	MutableTreeNode node = null;
    	
    	if (null != key) {
    		node = configurationNodeTable.get(key);
    	}
    	return node;
    }
    
    public String getConfigurationNodeKey(Object node) {
    	String key = null;
    	
    	if ((null != node) && configurationNodeTable.containsValue(node)) {
    		if (configurationNodeTable.containsKey(node.toString())) {
    			key = node.toString();
    		}
    	}
    	
    	return key;
    }

	@Override
	public Object getRoot() {
		return root;
	}
	
    public void setRoot(DefaultMutableTreeNode root) {
        this.root = root;
        if(null != root){
        	fireTreeStructureChanged(this, getPath(root), null, null);
        }
        else {
        	fireTreeStructureChanged(this, null);
        }
    }


    public Project getProject() {
    	return project;
    }
    
    public void setProject(Project p) {
    	if (null != networkConfiguration) {
    		networkConfiguration.removeNetworkConfigurationChangeListener(this);
    		networkConfiguration = null;
    	}

    	if (null != databaseConfiguration) {
    		databaseConfiguration.removeDatabaseConfigurationChangeListener(this);
    		databaseConfiguration = null;
    	}
    	
//    	if(null != windowEditor){
//    		windowEditor.removePropertyChangeListener(this);
//    		windowEditor = null;
//    	}
        if (p != null) {
            project = p;
            projectPath = p.getProjectPath();

            networkConfiguration = p.getNetworkConfiguration();
            networkConfiguration.addNetworkConfigurationChangeListener(this);
            
            databaseConfiguration = p.getDatabaseConfiguration();
            databaseConfiguration.addDatabaseConfigurationChangeListener(this);
            
//            windowEditor = DeveloperActionFactory.getFactory().getDeveloper().getWindowWorkspace();
//            windowEditor.addPropertyChangeListener(this);
            
            initProjectNodes();
//            initWindowNode();
        }
        else {
        	project = null;
        	projectPath = null;
        	this.setRoot(null);
        }
    }


    private void initWindowNode() {
//        windowNode.removeAllChildren();
//        if (networkEnvironment != null) {
//            Vector v = networkEnvironment.getNodeList();
//            for (int i = 0; i < v.size(); i++) {
//                addWindowTreeNode(((Node)v.get(i)).getName());
//            }
//        }
    }

    public void reload() {
        initWindowNode();
    }
    public void reload(TreeNode node) {
        if (node != null) {
            if (node instanceof WindowTreeNode) {
                ((WindowTreeNode)node).refreshNode();
            }
            fireTreeStructureChanged(this, getPath(node), null, null);
        }
    }


    public Object getChild(Object parent, int index) {
        return ((TreeNode)parent).getChildAt(index);
    }
    
    public int getChildCount(Object parent) {
        return ((TreeNode)parent).getChildCount();
    }
    
    public boolean isLeaf(Object node) {
        return ((TreeNode)node).isLeaf();
    }
    
    public void valueForPathChanged(TreePath path, Object newValue) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
        node.setUserObject(newValue);
        nodeChanged(node);
    }
    
    public int getIndexOfChild(Object parent, Object child) {
        int index = -1;
        if ((parent != null) && (child != null)) {
            index = ((TreeNode)parent).getIndex((TreeNode)child);
        }

        return index;
    }
    
    public void addTreeModelListener(TreeModelListener t) {
        listenerList.add(TreeModelListener.class, t);
    }
    
    public void removeTreeModelListener(TreeModelListener t) {
        listenerList.remove(TreeModelListener.class, t);
    }

    public void nodeChanged(TreeNode node) {
        if ((listenerList != null) && (node != null)) {
            TreeNode parent = node.getParent();
            if (parent != null) {
                int index = parent.getIndex(node);
                if (index != -1) {
                    int[] indexArray = new int[1];
                    indexArray[0] = index;
                    nodesChanged(parent, indexArray);
                }
            }
            else if (node == getRoot()) {
                nodesChanged(node, null);
            }
        }
    }
    
    public void nodesChanged(TreeNode node, int[] childIndices) {
        if (node != null) {
            if (childIndices != null) {
                int length = childIndices.length;

                if(length > 0) {
                    Object[] children = new Object[length];
                    for (int counter = 0; counter < length; counter++) {
                        children[counter] = node.getChildAt(childIndices[counter]);
                    }
                    fireTreeNodesChanged(this, getPath(node), childIndices, children);
                }
            }
            else if (node == getRoot()) {
                fireTreeNodesChanged(this, getPath(node), null, null);
            }
        }
    }

    protected TreeNode[] getPath(TreeNode node) {
        return getPath(node, 0);
    }
    protected TreeNode[] getPath(TreeNode node, int depth) {
        TreeNode[] path = null;

        if (node == null) {
            if(depth == 0) return null;
            else path = new TreeNode[depth];
        }
        else {
            depth++;
            if (node == root) path = new TreeNode[depth];
            else path = getPath(node.getParent(), depth);
            path[path.length - depth] = node;
        }

        return path;
    }


    protected void fireTreeNodesChanged(Object source, Object[] path,
                                        int[] childIndices, Object[] children) {
        Object[] listeners = listenerList.getListenerList();
        TreeModelEvent e = null;

        for (int i = listeners.length-2; i >= 0; i -= 2) {
            if (listeners[i] == TreeModelListener.class) {
                if (e == null)
                    e = new TreeModelEvent(source, path, childIndices, children);
                ((TreeModelListener)listeners[i+1]).treeNodesChanged(e);
            }
        }
    }

    protected void fireTreeNodesInserted(Object source, Object[] path,
            int[] childIndices, Object[] children) {
        Object[] listeners = listenerList.getListenerList();
        TreeModelEvent e = null;

        for (int i = listeners.length-2; i >= 0; i -= 2) {
            if (listeners[i] == TreeModelListener.class) {
                if (e == null)
                    e = new TreeModelEvent(source, path, childIndices, children);
                ((TreeModelListener)listeners[i+1]).treeNodesInserted(e);
            }
        }
    }
    
    protected void fireTreeNodesRemoved(Object source, Object[] path,
                                        int[] childIndices, Object[] children) {
        Object[] listeners = listenerList.getListenerList();
        TreeModelEvent e = null;

        for (int i = listeners.length-2; i >= 0; i -= 2) {
            if (listeners[i] == TreeModelListener.class) {
                if (e == null)
                    e = new TreeModelEvent(source, path, childIndices, children);
                ((TreeModelListener)listeners[i+1]).treeNodesRemoved(e);
            }
        }
    }
    
    protected void fireTreeStructureChanged(Object source, Object[] path,
            int[] childIndices, Object[] children) {
        Object[] listeners = listenerList.getListenerList();
        TreeModelEvent e = null;

        for (int i = listeners.length-2; i >= 0; i -= 2) {
            if (listeners[i] == TreeModelListener.class) {
                if (e == null)
                    e = new TreeModelEvent(source, path, childIndices, children);
                ((TreeModelListener)listeners[i+1]).treeStructureChanged(e);
            }
        }
    }
    
    // 트리노드 삭제를 위한 메서드.
    private void fireTreeStructureChanged(Object source, TreePath path) {
    	Object[] listeners = listenerList.getListenerList();
    	TreeModelEvent e = null;
    	for (int i = listeners.length-2; i>=0; i-=2) {
    		if (listeners[i]==TreeModelListener.class) {
    			if (e == null)
    				e = new TreeModelEvent(source, path);
    			((TreeModelListener)listeners[i+1]).treeStructureChanged(e);
    		}
    	}
    }

    @Override
    public void nodeAdded(HMINode addedNode) {
    	MutableTreeNode networkNode = configurationNodeTable.get(NETWORK_CONFIG);
    	if (null != networkNode) {
    		HMINodeTreeNode treeNode = new HMINodeTreeNode(addedNode);
    		((DefaultMutableTreeNode) networkNode).add(treeNode);    		

    		fireTreeStructureChanged(networkNode, getPath(networkNode), null, null);
    		// 화면 변경이 안되어서 주석처리
//    		fireTreeNodesInserted(treeNode, getPath(treeNode), null, null);
    	}
    }
    
    @Override
    public void nodeRemoved(HMINode removedNode) {
    	MutableTreeNode networkNode = configurationNodeTable.get(NETWORK_CONFIG);
    	HMINodeTreeNode removedTreeNode = null;
    	if (null != networkNode) {
    		Enumeration children = networkNode.children();
    		while (children.hasMoreElements()) {
    			HMINodeTreeNode child = (HMINodeTreeNode) children.nextElement();
    			if (child.getNode() == removedNode) {
    				removedTreeNode = child;
    				break;
    			}
    		}
    		if (null != removedTreeNode) {
    			removedNode.removePropertyChangeListener(this);
    			networkNode.remove(removedTreeNode);
    			fireTreeStructureChanged(networkNode, getPath(networkNode), null, null);
//    			fireTreeNodesRemoved(removedTreeNode, getPath(removedTreeNode), null, null);
    		}
    	}
    }
	@Override
    public void nodeNameChanged(String oldName, String newName) {
    	MutableTreeNode networkNode = configurationNodeTable.get(NETWORK_CONFIG);
    	fireTreeStructureChanged(networkNode, getPath(networkNode), null, null);
    	this.fireTreeNodesChanged(networkNode, getPath(networkNode), null, null);
    }

	@Override
    public void propertyChange(PropertyChangeEvent evt) {
		Object src = evt.getSource();
		String propName = evt.getPropertyName();
	
		if (HMINode.PROP_NAME.equals(propName)) {
			MutableTreeNode networkNode = configurationNodeTable.get(NETWORK_CONFIG);
			HMINodeTreeNode updateTreeNode = null;
			if (null != networkNode) {
				Enumeration children = networkNode.children();
				while (children.hasMoreElements()) {
					HMINodeTreeNode child = (HMINodeTreeNode) children.nextElement();
					if (child.getNode() == src) {
						updateTreeNode = child;
						break;
					}
				}
				if (null != updateTreeNode) {
					this.fireTreeNodesChanged(updateTreeNode, 
									getPath(updateTreeNode), null, null);
				}
			}
		}
		else if(Database.PROP_NAME.equals(propName)) {
			MutableTreeNode databaseNode = configurationNodeTable.get(DATABASE_CONFIG);
			DatabaseTreeNode updateTreeNode = null;
			if (null != databaseNode) {
				Enumeration children = databaseNode.children();
				while (children.hasMoreElements()) {
					DatabaseTreeNode child = (DatabaseTreeNode) children.nextElement();
					if (child.getDatabase() == src) {
						updateTreeNode = child;
						break;
					}
				}
				if (null != updateTreeNode) {
					this.fireTreeNodesChanged(updateTreeNode, 
									getPath(updateTreeNode), null, null);
				}
			}
		}
//		else if(WindowEditor.WINDOW_CREATE_PROPERTY.equals(propName)){
//			MutableTreeNode windowListNode = configurationNodeTable.get(WINDOW_CONFIG);
//			HMIDesignView newView = (HMIDesignView) evt.getNewValue();
//			
//			WindowNameNode newNode = new WindowNameNode(newView.getFile());
//			windowListNode.insert(newNode, windowListNode.getChildCount());
//			
//			this.fireTreeStructureChanged(windowListNode, 
//							getPath(windowListNode), null, null);
//			
//		}
//		else if(WindowEditor.WINDOW_DELETE_PROPERTY.equals(propName)){
//			Object newValue = evt.getNewValue();
//			
//			File removeFile = null;
//			// 열려있는 파일을 지웠을때.
//			if(newValue instanceof HMIDesignView){
//				HMIDesignView view = (HMIDesignView) newValue;
//				removeFile = view.getFile();
//			}
//			
//			if(null != removeFile){
//				MutableTreeNode windowListNode = configurationNodeTable.get(WINDOW_CONFIG);
//				WindowNameNode removedTreeNode = null;
//				if (null != windowListNode) {
//					Enumeration children = windowListNode.children();
//					while (children.hasMoreElements()) {
//						WindowNameNode child = (WindowNameNode) children.nextElement();
//						if (removeFile.equals(child.getUserObject())) {
//							removedTreeNode = child;
//							break;
//						}
//					}
//					if (null != removedTreeNode) {
//						windowListNode.remove(removedTreeNode);
//						fireTreeStructureChanged(windowListNode, getPath(windowListNode), null, null);
//					}
//				}
//			}
//		}
//		else if(WindowEditor.WINDOW_FILE_PROPERTY.equals(propName)){
//			updateWindowListNode();
//		}
    }
	
	@Override
    public void databaseAdded(Database addedNode) {
		MutableTreeNode databaseNode = configurationNodeTable.get(DATABASE_CONFIG);
    	if (null != databaseNode) {
    		DatabaseTreeNode treeNode = new DatabaseTreeNode(addedNode);
    		((DefaultMutableTreeNode) databaseNode).add(treeNode);    		

    		fireTreeStructureChanged(databaseNode, getPath(databaseNode), null, null);
    	}
    }
	
	@Override
    public void databaseNameChanged(String oldName, String newName) {
		MutableTreeNode databaseNode = configurationNodeTable.get(DATABASE_CONFIG);
    	fireTreeStructureChanged(databaseNode, getPath(databaseNode), null, null);
    	this.fireTreeNodesChanged(databaseNode, getPath(databaseNode), null, null);
    }
	
	@Override
    public void databaseRemoved(Database removedNode) {
		MutableTreeNode databaseNode = configurationNodeTable.get(DATABASE_CONFIG);
		DatabaseTreeNode removedTreeNode = null;
    	if (null != databaseNode) {
    		Enumeration children = databaseNode.children();
    		while (children.hasMoreElements()) {
    			DatabaseTreeNode child = (DatabaseTreeNode) children.nextElement();
    			if (child.getDatabase() == removedNode) {
    				removedTreeNode = child;
    				break;
    			}
    		}
    		if (null != removedTreeNode) {
    			removedNode.removePropertyChangeListener(this);
    			databaseNode.remove(removedTreeNode);
    			fireTreeStructureChanged(databaseNode, getPath(databaseNode), null, null);
    		}
    	}
    }
	
	/**
	 * F5번 키를 눌렀을시 화면 노드 업데이트에 사용되는 메서드.
	 */
	public void updateWindowListNode(){
		if(null != configurationNodeTable){
			// 화면 노드 업데이트.(새로 생성)
			WindowTreeNode listNode = (WindowTreeNode) configurationNodeTable.get(WINDOW_CONFIG);
			listNode.removeAllChildren();
			
			File windowFile = new File(project.getProjectPath().getPath(), IProjectConstants.WINDOW_PATH);
			File[] fileList = windowFile.listFiles(WindowTreeNode.filter);
			if(null != fileList){
				for(File file : fileList){
					if(!file.isDirectory()){
						WindowNameNode wnn = new WindowNameNode(file);
						listNode.insert(wnn, listNode.getChildCount());
					}
				}
			}
			reload(listNode);
		}
	}

}
