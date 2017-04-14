package graph;

public interface BinaryBranch<Child extends NodeType> extends NodeBranch<Child> {

	Child getFirstChild();
	Child getSecondChild();
	void setFirstChild(Child child);
	void setSecondChild(Child child);
}
