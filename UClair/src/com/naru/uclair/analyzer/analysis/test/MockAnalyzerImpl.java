package com.naru.uclair.analyzer.analysis.test;

import com.naru.common.NaruAssert;
import com.naru.uclair.project.Project;
import com.naru.uclair.project.TagDictionary;

/**
 * ���� ������ �ϱ� ���� �׽�Ʈ�� Mock Ŭ����
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
public class MockAnalyzerImpl {
	/** 
	 * ������Ʈ ����
	 */
	private Project project;
	
	private TagDictionary tagDictionary;
	
	private static MockAnalyzerImpl analyzerImpl;
	/**
	 * �⺻ ������
	 */
	private MockAnalyzerImpl() {
		
	}
	
	public static MockAnalyzerImpl getInstance() {
		NaruAssert.isNotNull(analyzerImpl);
		if(analyzerImpl == null) {
			analyzerImpl = new MockAnalyzerImpl();
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
		String tempMessage = "Test Mock Project";
		return tempMessage;
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
	public Object eachTagDependunyAnalysis(String tagkey) {
		return null;
	}

	/**
	 * 4. �����±װ� ��ü ������Ʈ�� ���Ǿ��� �κ��� ���Ӽ� �˻��� ���¹ٿ� ǥ���� ������ ��ȯ�ϴ�.
	 * ��� �����ʹ�  List ������ List<int>
	 * @param tagkey
	 * @return
	 */
	public Object eachTagDependunyAnalysisCount(String tagkey) {
		return null;
	}
	
	/**
	 * 5. �����±׿� �����ϴ� ���ӵǴ� �����±׸� �м��Ͽ� ��ȯ�Ѵ�.
	 * ��� �����ʹ� �����±� ���Ӱ���� �����Ѵ�.
	 * ��� -> Map ������ (Key : �±׸�, Value : Tag)
	 * @param tagkey (���õ� �����±׸�)
	 * @return
	 */
	public Object virtualTagDependunyAnalysis(String tagkey) {
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
	public Object physicalTagDependunyAnalysis(int option) {
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
	public Object eventDependunyAnalysis() {
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