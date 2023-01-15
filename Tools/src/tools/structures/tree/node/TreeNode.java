package tools.structures.tree.node;

import java.util.ArrayList;
import java.util.List;

public class TreeNode<A> {
	public static int count = 0;
	public final Object id;
	public List<A> children = new ArrayList<>();
	public List<A> parents = new ArrayList<>();
	public int $;
	public TreeNode(Object o) { id = o; count++; }
	public TreeNode() { this(count); }
}
