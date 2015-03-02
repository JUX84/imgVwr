package view;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.*;
import java.util.*;
import java.io.*;

import model.Language;
import controller.Controller;

public class TreeView extends BaseView
{
    private final JTree tree;

    public TreeView(final Controller controller)
	{
		super();
        FileSystemModel fileSystemModel = new FileSystemModel(new File(System.getProperty("user.home")));
		tree = new JTree(fileSystemModel);
		tree.setEditable(true);
		tree.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent event) {
				File file = (File) tree.getLastSelectedPathComponent();
				controller.pathSelected(file);
			}
		});
        JScrollPane scroll = new JScrollPane(tree);
		add(scroll);
		controller.init(this);
	}

	public void setLanguage(Language language) {
        super.setTitle(language.getString("tree"));
	}

	@Override
	public void update (Observable o, Object arg) {
		String tmp = (String)arg;
		if(tmp.equals("language"))
			setLanguage((Language)o);
	}
}

class FileSystemModel implements TreeModel {
	private final File root;

	private final Vector listeners = new Vector();

	public FileSystemModel(File rootDirectory) {
		root = rootDirectory;
	}

	public Object getRoot() {
		return root;
	}

	public Object getChild(Object parent, int index) {
		File directory = (File) parent;
		String[] children = directory.list();
		return new TreeFile(directory, children[index]);
	}

	public int getChildCount(Object parent) {
		File file = (File) parent;
		if (file.isDirectory()) {
			String[] fileList = file.list();
			if (fileList != null)
				return file.list().length;
		}
		return 0;
	}

	public boolean isLeaf(Object node) {
		File file = (File) node;
		return file.isFile();
	}

	public int getIndexOfChild(Object parent, Object child) {
		File directory = (File) parent;
		File file = (File) child;
		String[] children = directory.list();
		for (int i = 0; i < children.length; i++) {
			if (file.getName().equals(children[i])) {
				return i;
			}
		}
		return -1;

	}

	public void valueForPathChanged(TreePath path, Object value) {
		File oldFile = (File) path.getLastPathComponent();
		String fileParentPath = oldFile.getParent();
		String newFileName = (String) value;
		File targetFile = new File(fileParentPath, newFileName);
		boolean b = oldFile.renameTo(targetFile);
        if(b) {
            File parent = new File(fileParentPath);
            int[] changedChildrenIndices = {getIndexOfChild(parent, targetFile)};
            Object[] changedChildren = {targetFile};
            fireTreeNodesChanged(path.getParentPath(), changedChildrenIndices, changedChildren);
        }
	}

	private void fireTreeNodesChanged(TreePath parentPath, int[] indices, Object[] children) {
		TreeModelEvent event = new TreeModelEvent(this, parentPath, indices, children);
		Iterator iterator = listeners.iterator();
		TreeModelListener listener;
		while (iterator.hasNext()) {
			listener = (TreeModelListener) iterator.next();
			listener.treeNodesChanged(event);
		}
	}

	public void addTreeModelListener(TreeModelListener listener) {
        listeners.add(listener);
	}

	public void removeTreeModelListener(TreeModelListener listener) {
		listeners.remove(listener);
	}

	private class TreeFile extends File {
		public TreeFile(File parent, String child) {
			super(parent, child);
		}

		public String toString() {
			return getName();
		}
	}
}
