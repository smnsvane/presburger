package graph;

public abstract class AbstractNode implements Node {

	private final Branch parent;
	public final Branch getParent() { return parent; }

	private final String identifier;
	public final String getIdentifier() { return identifier; }

	public AbstractNode(Branch parent, String identifier) {
		this.parent = parent;
		this.identifier = identifier;
	}
}
