package com.naru.uclair.analyzer.views.message;

import java.awt.Color;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.Icon;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.jidesoft.docking.DockableFrame;
import com.jidesoft.swing.JideTabbedPane;
import com.naru.common.logging.BasicLogger;
import com.naru.common.logging.LogValueEventListener;
import com.naru.common.logging.LoggerManager;
import com.naru.uclair.analyzer.constants.AnalyzerConstants;

public class MessageView extends DockableFrame implements
		LogValueEventListener, ChangeListener {

	private static final long serialVersionUID = 1L;

	private static final String SYS_OUT = AnalyzerConstants
			.getString("MessageView.TabTitle.Sysout"); //$NON-NLS-1$
	private static final String SYS_ERR = AnalyzerConstants
			.getString("MessageView.TabTitle.Syserr"); //$NON-NLS-1$

	private JTextArea systemOutTextArea;
	private JTextArea systemErrTextArea;

	private JideTabbedPane tabbedPane;

	// private PrintStream outStream;

	// private PrintStream errorStream;

	private BasicLogger systemOutLogger;
	private BasicLogger systemErrLogger;
	// 2009.07.07 OpcLogger
	// 이 부분은 OPC 로거가 시스템 에러에 정보메시지를 너무 많이 뿌려서 필요없으므로 메시지를 안찍기 위해 수정함
	private Logger opcLoger;

	private void initialize() {
//		systemOutLogger = LoggerManager
//				.getLogger(LoggerManager.SYSTEM_OUT_NAME);
//		systemOutLogger.addLogValueEventListener(this);
//
//		systemErrLogger = LoggerManager
//				.getLogger(LoggerManager.SYSTEM_ERR_NAME);
//		systemErrLogger.addLogValueEventListener(this);

		opcLoger = Logger.getLogger("org.jinterop"); // LoggerManager.getLogger(
		// "org.openscada.opc.lib.da.Server"
		// );
		opcLoger.setLevel(Level.WARNING);

		systemOutTextArea = new JTextArea();
		// systemOutTextArea.setEditable(false);
		systemErrTextArea = new JTextArea();
		// systemErrTextArea.setEditable(false);

		tabbedPane = new JideTabbedPane();
		tabbedPane.addChangeListener(this);

		tabbedPane.addTab(SYS_OUT, new JScrollPane(systemOutTextArea));
		tabbedPane.addTab(SYS_ERR, new JScrollPane(systemErrTextArea));

		getContentPane().add(tabbedPane);

	}

	public MessageView(String key) {
		this(key, null);
	}

	public MessageView(String key, Icon icon) {
		super(key, icon);
		initialize();
	}

	@Override
	public void LogValueEvent(String loggerName, String logMsg) {
		if (LoggerManager.SYSTEM_OUT_NAME.equals(loggerName)) {
			setTabTitleColor(SYS_OUT);
			systemOutTextArea.append(logMsg);
		} else if (LoggerManager.SYSTEM_ERR_NAME.equals(loggerName)) {
			setTabTitleColor(SYS_ERR);
			systemErrTextArea.append(logMsg);
		}
	}

	private void setTabTitleColor(String tabName) {
		if (null != tabName) {
			int index = tabbedPane.indexOfTab(tabName);
			if (index != tabbedPane.getSelectedIndex()) {
				tabbedPane.setForegroundAt(index, new Color(61, 149, 255));
			}
		}
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		int index = tabbedPane.getSelectedIndex();
		tabbedPane.setForegroundAt(index, null);
	}
}
