package graph;

public interface UnaryBranch<Child extends NodeType> extends NodeBranch<Child> {

	Child getChild();
	void setChild(Child child);
}
