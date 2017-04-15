package graph;

import java.util.Iterator;

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

	@Override
	public Iterator<Node> iterator() {
		return new Iterator<Node>() {
			boolean given = false;
			@Override
			public Node next() {
				if (!given) {
					given = true;
					return child;
				} else
					throw new RuntimeException("Ran out of children");
			}
			@Override
			public boolean hasNext() {
				return given;
			}
		};
	}
}
