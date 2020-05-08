package com.naru.uclair.analyzer.analysis;

import com.naru.common.NaruAssert;
import com.naru.uclair.project.Project;
import com.naru.uclair.project.TagDictionary;

/**
 * �⺻���� ��� �м��� ���� �м� ������ Ŭ����
 * ���� ����ġ ������Ʈ�� ���� ��ü������ ������ �־�� �Ѵ�.
 * 1. ������Ʈ ���� ��� 
 * 2. ��ü�±� �����Ӽ� �м�
 * 3. �����±� ���Ӽ� �м�
 * 4. �����±� ���Ӽ� �м�
 * 5. �����ּ� ���Ӽ� �м�
 * 6. ��ü �±� ���� ���� �м�
 * 7. �̺�Ʈ ���Ӽ� �м�
 * 8. ��꽺ũ��Ʈ ���� �м�
 * 9. ��ü ȿ���縳�� �м�
 * @author �����
 */
public class AnalyzerImpl {
	/** 
	 * ������Ʈ ����
	 */
	private Project project;
	
	private TagDictionary tagDictionary;
	
	private static AnalyzerImpl analyzerImpl;
	/**
	 * �⺻ ������
	 */
	private AnalyzerImpl() {
		
	}
	
	public static AnalyzerImpl getInstance() {
		NaruAssert.isNotNull(analyzerImpl);
		if(analyzerImpl == null) {
			analyzerImpl = new AnalyzerImpl();
		}
		return analyzerImpl;
	}
	
	/**
	 * ������Ʈ�� �ε��� �� �ٽ� �缳���� �Ѵ�. 
	 * @param project
	 */
	public void setProject(Project project) {
		this.project = project;
		tagDictionary = this.project.getTagDictionary();
	}
	
	/**
	 * 1. ��ü ������Ʈ ���� ��� �����͸� ���ڿ��� ��ȯ�Ѵ�.
	 */
	public String getProjectAbstractResult() {
		return "";
	}
	
	/**
	 * 2. ��ü�±� �����Ӽ� �м��� �����Ѵ�.
	 * �ɼ�(0:��ü�±�, 1:�̺�Ʈ����, 2:������Լ�, 3:ȭ��
	 * ��� �����ʹ� Map ������ (Key : �±׸�, Value : Tag)
	 * @param Option
	 * @return
	 */
	public Object allDanglingTagAnalysis(int Option) {
		return null;
	}
	
	/**
	 * 3. �����±װ� ��ü ������Ʈ�� ���Ǿ��� �κ��� ���Ӽ� �˻縦 �����Ѵ�.
	 * ��� �����ʹ�  List ������ List<EachTagDependuny>
	 * @param tagkey
	 * @return
	 */
	public Object eachTagDependencyAnalysis(String tagkey) {
		return null;
	}

	/**
	 * 4. �����±װ� ��ü ������Ʈ�� ���Ǿ��� �κ��� ���Ӽ� �˻��� ���¹ٿ� ǥ���� ������ ��ȯ�ϴ�.
	 * ��� �����ʹ�  List ������ List<int>
	 * @param tagkey
	 * @return
	 */
	public Object eachTagDependencyAnalysisCount(String tagkey) {
		return null;
	}
	
	/**
	 * 5. �����±׿� �����ϴ� ���ӵǴ� �����±׸� �м��Ͽ� ��ȯ�Ѵ�.
	 * ��� �����ʹ� �����±� ���Ӱ���� �����Ѵ�.
	 * ��� -> Map ������ (Key : �±׸�, Value : Tag)
	 * @param tagkey (���õ� �����±׸�)
	 * @return
	 */
	public Object virtualTagDependencyAnalysis(String tagkey) {
		return null;
	}
	
	/**
	 * 6. �����ּ� ���Ӽ� �±���ȸ
	 * �����ּҰ� �ߺ��Ǵ� �±׸� �м��Ͽ� �ɼǿ� ���� �м������ ��ȯ�Ѵ�.
	 * �ɼ� (0: ����̽��� �ּҰ� ��� ���� ���, 1: �ּҸ� �������)
	 * ��� ������ => Map ������ (Key : �±��ּ�, Value : List<Tag>) 
	 * @param option
	 * @return
	 */
	public Object physicalTagDependencyAnalysis(int option) {
		return null;
	}
	
	/**
	 * 7. ��ü�±� �������� �м� 
	 * ȭ���� ��ü���� ����� ȿ���� �±׸���� �˻��Ͽ� ����� ��ȯ�Ѵ�.
	 * ��� ������ => Map ������ (Key : Object��, Value : List<String>)
	 * @param windowName (ȭ���)
	 * @return
	 */
	public Object objectAndTagLinkAnalysis(String windowName) {
		return null;
	}
	
	/**
	 * 8. �̺�Ʈ ���Ӽ� �м�
	 * �����±׿� ������ �ִ� ��� ��(����)�±׸� �˻��Ͽ� ����� ��ȯ�Ѵ�.
	 * ��������� => Map ������ (Key : �̺�Ʈ��, Value : List<String>)
	 * value => <�̺�Ʈ��, �����±�, �����±�, �����±׸���Ʈ>
	 * @return
	 */
	public Object eventDependencyAnalysis() {
		return null;
	}
	
	/**
	 * 9. ��ũ��Ʈ ���� �м�
	 * ��� ��ũ��Ʈ�� ������ �����Ѵ�.
	 * ��������� => Map ������ (Key : ��ũ��Ʈ��, Value : String[��ũ��Ʈ ��� ���ڿ�])
	 * @return
	 */
	public Object scriptSyntaxAnalysis() {
		return null;
	}
	
	/**
	 * 10. ��ü ����Ʈ �縳�� �м�
	 * ��ü ȿ���� ���� �縳�� �� ���� ȿ�������� ���� ����� ��ȯ�Ѵ�.
	 * ��������� => Map ������ (Key : ��ü��, Value : List<String>[��üȿ����������])
	 * @param windowName
	 * @return
	 */
	public Object objectEffectCompatibilityAnalysis(String windowName) {
		return null;
	}
}