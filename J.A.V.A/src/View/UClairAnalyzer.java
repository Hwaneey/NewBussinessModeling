package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;

import com.javadocking.DockingManager;
import com.javadocking.dock.Position;
import com.javadocking.dock.SplitDock;
import com.javadocking.dock.TabDock;
import com.javadocking.dockable.DefaultDockable;
import com.javadocking.dockable.Dockable;
import com.javadocking.dockable.DockingMode;
import com.javadocking.dockable.DraggableContent;
import com.javadocking.drag.DragListener;
import com.javadocking.model.FloatDockModel;


public class UClairAnalyzer extends JPanel
{

	/**
	 * 
	 */

	// Static fields.


	private static final long serialVersionUID = 1L;
	public static final int FRAME_WIDTH = 600;
	public static final int FRAME_HEIGHT = 450;

	// Constructor.

	public UClairAnalyzer(JFrame frame)
	{
		super(new BorderLayout());

		
		// �� ���� ����
		FloatDockModel dockModel = new FloatDockModel();
		dockModel.addOwner("frame0", frame);
		
		
		
		// �� �𵨿� ����� �־���
		DockingManager.setDockModel(dockModel);

		//������Ʈ ����
		TextPanel textPanel1 = new TextPanel("");
		TextPanel textPanel2 = new TextPanel("I am window 2.");
		TextPanel textPanel3 = new TextPanel("");
		TextPanel textPanel4 = new TextPanel("");
		
				
		
		
		
		//�г��� ������� �־��ְ� �������� ���ش�
		Dockable dockable1 = new DefaultDockable("Window1", textPanel1, "�м���", null, DockingMode.ALL);
		Dockable dockable2 = new DefaultDockable("Window2", textPanel2, "Window 2", null, DockingMode.ALL);
		Dockable dockable3 = new DefaultDockable("Window3", textPanel3, "System out", null, DockingMode.ALL);
		Dockable dockable4 = new DefaultDockable("Window4", textPanel4, "System err", null, DockingMode.ALL);
				
	
		// �� ��ũ ����
		TabDock leftTabDock = new TabDock();
		TabDock rightTabDock = new TabDock();
		TabDock bottomTabDock = new TabDock();


		// �� ��ũ�� �����̺��� �߰�
		leftTabDock.addDockable(dockable1, new Position(0));
		rightTabDock.addDockable(dockable2, new Position());
		
		bottomTabDock.addDockable(dockable3, new Position(0));
		bottomTabDock.addDockable(dockable4, new Position(1));

				

		//�������� �ǵ� ������ ������ �ְ� �������
		//���� �� �� �� ��ũ�� ������ �ְ� �����.
		
		SplitDock topSplitDock = new SplitDock();
		topSplitDock.addChildDock(leftTabDock, new Position(0));
		topSplitDock.addChildDock(rightTabDock, new Position(0));

		SplitDock rightSplitDock = new SplitDock();
		//compositeTabDock1.addChildDock(dock1, new Position(0));
		//topSplitDock.addChildDock(bottomTabDock, new Position(1));
		
		SplitDock bottomSplitDock = new SplitDock();
		bottomSplitDock.addChildDock(bottomTabDock, new Position(0));
		
		// ��ũ �𵨿� �÷�����
		dockModel.addRootDock("topdock", topSplitDock, frame);
		dockModel.addRootDock("rightdock", rightSplitDock, frame);
		dockModel.addRootDock("bottomdock", bottomSplitDock, frame);
				

		// ���� �г��� ����
		JSplitPane SplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		SplitPane.setDividerLocation(600);


		// Add the root docks to the split panes.
		SplitPane.setLeftComponent(topSplitDock);		
		SplitPane.setRightComponent(rightSplitDock);
		SplitPane.setRightComponent(bottomSplitDock);
		

		
		//�гο� ���ø� �г� �߰�
		add(SplitPane, BorderLayout.CENTER);
	
		
	}
	
	/**
	 * This is the class for the content.
	 */
	private class TextPanel extends JPanel implements DraggableContent
	{
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private JLabel label; 
		
		public TextPanel(String text)
		{
			super(new FlowLayout());
			
			// The panel.
			setMinimumSize(new Dimension(100,100));
			setPreferredSize(new Dimension(150,150));
			setBackground(Color.white);
			
			setBorder(BorderFactory.createLineBorder(Color.white));
			
			// The label.
			label = new JLabel(text);
			label.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
			add(label);
		}
		
		// Implementations of DraggableContent.

		public void addDragListener(DragListener dragListener)
		{
			addMouseListener(dragListener);
			addMouseMotionListener(dragListener);
			label.addMouseListener(dragListener);
			label.addMouseMotionListener(dragListener);
		}
	}

	// Main method.
	
	public static void createAndShowGUI()
	{
		
		// Create the frame.
		JFrame frame = new JFrame("UClair Analyzer");
		// Create the panel and add it to the frame.
		UClairAnalyzer panel = new UClairAnalyzer(frame);
		frame.getContentPane().add(panel);

		// Set the frame properties and show it.
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation((screenSize.width - FRAME_WIDTH) / 2, (screenSize.height - FRAME_HEIGHT) / 2);
		frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		frame.setVisible(true);
		
		
		frame.add(panel);
		
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setPreferredSize(new Dimension(1600, 900));
		frame.setSize(1280,720);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		
		 JMenuBar m = new JMenuBar();
	    frame.setJMenuBar( m );
	        
	        JMenu m_file = new JMenu("����");
	        JMenu m_analysis = new JMenu("�м�");     
	        JMenu m_test = new JMenu("�׽�Ʈ");     
	        JMenu m_case = new JMenu("���̽�");     
	        JMenu m_help = new JMenu("����");
	        
	        m.add( m_file);
	        m.add( m_analysis);     
	        m.add(m_test);
	        m.add(m_case);
	        m.add(m_help);
	        
	        
	        
	        JMenuItem i_save = new JMenuItem("����");
	        JMenuItem i_load = new JMenuItem("�ε�");     
	        m_file.add( i_save);
	        m_file.add( i_load);
	        
		
		
	}

	public static void main(String args[]) 
	{
        Runnable doCreateAndShowGUI = new Runnable() 
        {
            public void run() 
            {
                createAndShowGUI();
            }
        };
        SwingUtilities.invokeLater(doCreateAndShowGUI);
    }
	
}