package graph;

public abstract class Branch extends Node {

	public final int maxChildren;
	public abstract void addChild(Node child);

	public Branch(Branch parent, String identifier, int maxChildren) {
		super(parent, identifier);
		this.maxChildren = maxChildren;
	}
}
