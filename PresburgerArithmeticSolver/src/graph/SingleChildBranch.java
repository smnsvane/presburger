package graph;

public abstract class SingleChildBranch<Child extends Node> implements Branch {

	private Child child;
	public Child getChild() { return child; }
	public void setChild(Child child) { this.child = child; }
	@SuppressWarnings("unchecked")
	@Override
	public void replaceChild(Node victim, Node overtaker) {
		if (child.equals(victim))
			child = (Child) overtaker;
		else
			throw new RuntimeException("child not found");
	}
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof SingleChildBranch<?>))
			return false;
		SingleChildBranch<?> other = (SingleChildBranch<?>) obj;
		if (!getSymbol().equals(other.getSymbol()))
			return false;
		return child.equals(other.child);
	}
	@Override
	public String toString() { return getSymbol()+child; }
}
