package tools.structures.tree.node;

import java.util.ArrayList;
import java.util.List;

public class TreeNode {
	public static int count = 0;
	public static TreeNode[] create(int n) {
		TreeNode[] ns = new TreeNode[n];
		for (int i=0; i<n; i++) ns[i] = new TreeNode();
		return ns;
	}
	public final int id;
	public List<TreeNode> children = new ArrayList<>();
	public List<TreeNode> parents = new ArrayList<>();
	public int $;
	public TreeNode() { id = count++; }
}
