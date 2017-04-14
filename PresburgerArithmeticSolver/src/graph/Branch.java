package graph;

import java.util.Collections;
import java.util.List;

public abstract class Branch extends Node {

	private List<Node> children;
	public List<Node> getChildren() {
		if (children == null)
			throw new RuntimeException("Children have not yet been set");
		return children;
	}
	public void setChildren(List<Node> children) {
		this.children = Collections.unmodifiableList(children);
	}

	public Branch(Branch parent, String identifier) { super(parent, identifier); }
}
