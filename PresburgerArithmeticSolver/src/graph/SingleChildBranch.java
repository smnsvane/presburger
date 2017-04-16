package graph;

import parser.SymbolBinding;

public abstract class SingleChildBranch<Child extends Node> extends Branch<Child> {

	private Child child;
	public Child getChild() { return child; }
	@Override
	public void replaceChild(Child victim, Child overtaker) {
		if (isLocked())
			throw new RuntimeException("Locked");
		if (victim.equals(child))
			child = overtaker;
		else
			throw new RuntimeException("Can't find victim "+victim);
	}
	public SingleChildBranch(Child child) { this.child = child; }
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
	public String toString() {
		String childString;
		if (SymbolBinding.lowerOrEqualPrecedence(child, this))
			childString = "("+child.toString()+")";
		else
			childString = child.toString();
		return getSymbol()+childString;
	}
}
