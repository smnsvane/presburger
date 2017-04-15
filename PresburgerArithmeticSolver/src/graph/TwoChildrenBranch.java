package graph;

public abstract class TwoChildrenBranch<Child1 extends Node, Child2 extends Node> implements Branch {

	private Child1 child1;
	private Child2 child2;
	public Child1 getFirstChild() { return child1; }
	public Child2 getSecondChild() { return child2; }
	public void setFirstChild(Child1 child) { child1 = child; }
	public void setSecondChild(Child2 child) { child2 = child; }

	@SuppressWarnings("unchecked")
	@Override
	public Node replaceVariables(VariableAssignment assignment) {
		child1 = (Child1) child1.replaceVariables(assignment);
		child2 = (Child2) child2.replaceVariables(assignment);
		return this;
	}
}
