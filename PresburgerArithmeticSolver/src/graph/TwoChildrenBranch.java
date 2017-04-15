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
	public void replaceChild(Node victim, Node overtaker) {
		if (child1.equals(victim))
			child1 = (Child1) overtaker;
		else if (child2.equals(victim))
			child2 = (Child2) overtaker;
		else
			throw new RuntimeException("child not found");
	}
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof TwoChildrenBranch<?, ?>))
			return false;
		TwoChildrenBranch<?, ?> other = (TwoChildrenBranch<?, ?>) obj;
		if (!getSymbol().equals(other.getSymbol()))
			return false;
		return child1.equals(other.child1) && child2.equals(other.child2);
	}
	@Override
	public String toString() { return child1+getSymbol()+child2; }
}
