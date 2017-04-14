package graph;

public abstract class Node {

	private final Branch parent;
	public Branch getParent() { return parent; }

	private final String identifier;
	public String getIdentifier() { return identifier; }

	public Node(Branch parent, String identifier) {
		this.parent = parent;
		this.identifier = identifier;
	}
}
