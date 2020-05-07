package analyzer.views.project;

import java.util.Enumeration;

import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;

import com.naru.common.NaruAssert;
import com.naru.uclair.database.Database;

public class DatabaseTreeNode implements MutableTreeNode {

	private MutableTreeNode parent;

	private Database database;

	public DatabaseTreeNode(final Database userObject) {
		setUserObject(userObject);
	}

	@Override
	public Enumeration children() {
		// Do not anything.
		return null;
	}

	@Override
	public boolean getAllowsChildren() {
		return false;
	}

	@Override
	public TreeNode getChildAt(final int childIndex) {
		// Do not anything.
		return null;
	}

	@Override
	public int getChildCount() {
		return 0;
	}

	public Database getDatabase() {
		return database;
	}

	@Override
	public int getIndex(final TreeNode node) {
		// Do not anything.
		return -1;
	}

	@Override
	public TreeNode getParent() {
		return parent;
	}

	@Override
	public void insert(final MutableTreeNode child, final int index) {
		// Do not anything.
		// throw new IllegalStateException("NodeTreeNode can't take a child");
	}

	@Override
	public boolean isLeaf() {
		return true;
	}

	@Override
	public void remove(final int index) {
		// Do not anything.
		// throw new IllegalStateException("NodeTreeNode can't take a child");
	}

	@Override
	public void remove(final MutableTreeNode node) {
		// Do not anything.
		// throw new IllegalStateException("NodeTreeNode can't take a child");
	}

	@Override
	public void removeFromParent() {
		if (null != parent) {
			parent.remove(this);
		}
	}

	@Override
	public void setParent(final MutableTreeNode newParent) {
		parent = newParent;
	}

	@Override
	public void setUserObject(final Object object) {
		NaruAssert.isNotNull(object);
		NaruAssert.isTrue(object instanceof Database);

		if (database != object) {
			// if (null != node) {
			// node.removePropertyChangeListener(this);
			// }
			database = (Database) object;
			// node.addPropertyChangeListener(this);
		}
	}

	@Override
	public String toString() {
		return database.getName();
	}
}
