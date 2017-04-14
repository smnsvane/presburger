package graph;

public interface UnaryBranch<N extends Node> extends Branch {

	Node getChild();
	void setChild(N child);
}
