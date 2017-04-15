package graph;

public abstract class SingleChildBranch<Child extends Node> implements Branch {

	private Child child;
	public Child getChild() { return child; }
	public void setChild(Child child) { this.child = child; }

	@SuppressWarnings("unchecked")
	@Override
	public Node replaceVariables(VariableAssignment assignment) {
		child.replaceVariables(assignment);
		return (Child) this;
	}
}
