package graph;

public interface BinaryBranch<N extends Node> extends Branch {

	Node getFirstChild();
	Node getSecondChild();
	void setFirstChild(N child);
	void setSecondChild(N child);
}
