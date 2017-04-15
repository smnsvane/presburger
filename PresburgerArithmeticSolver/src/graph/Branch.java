package graph;

public interface Branch extends Node {

	void replaceChild(Node victim, Node overtaker);
}
