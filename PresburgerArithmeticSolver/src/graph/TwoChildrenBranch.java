package graph;

import java.util.Iterator;

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

	@Override
	public Iterator<Node> iterator() {
		return new Iterator<Node>() {
			int nextCount = 0;
			@Override
			public Node next() {
				nextCount++;
				switch (nextCount) {
				case 1:
					return child1;
				case 2:
					return child2;
				default:
					throw new RuntimeException("Ran out of children");
				}
			}
			@Override
			public boolean hasNext() { return nextCount < 2; }
		};
	}
}
