package com.naru.uclair.analyzer.views.project;

import java.io.File;

import javax.swing.JPopupMenu;




public class WindowNodePopup extends JPopupMenu {

//	private WindowEditor editor;
	
//	private Action newAction;
//	private Action openAction;
//	private Action closeAction;
//	private Action deleteAction;

	private void reset() {
		this.removeAll();
	}

//	protected void initPopup() {
//		newAction = editor.getAction(EditorActionFactory.NEW);
//		openAction = editor.getAction(EditorActionFactory.OPEN);
//		closeAction = editor.getAction(EditorActionFactory.CLOSE);
//		deleteAction = editor.getAction(WindowEditorActionFactory.DELETE_WINDOW);
//	}

	public WindowNodePopup() {
	}
	
//	public WindowNodePopup(WindowEditor editor) {
//		this(editor, null);
//	}
//	public WindowNodePopup(WindowEditor editor, File target) {
//		super("WindowNode Popup");
//	
////		this.editor = editor;
////		initPopup();
//		setTarget(target);
//	}
	
	public void setTarget(File target) {
//		reset();
//		
//		add(newAction);
//		
//		if (null != target) {
//			addSeparator();
//			
//			boolean isOpened = editor.isOpened(target);
//			if (isOpened) {
//				add(closeAction);
//				closeAction.putValue(Messages.getString("close.action.file.param"), target);
//			}
//			else {
//				add(openAction);
//				openAction.putValue(Messages.getString("open.action.file.param"), target);
//			}
//			addSeparator();
//			
//			add(deleteAction);
//			deleteAction.putValue(Messages.getString("delete.action.file.param"), target);
//		}
	}
}
