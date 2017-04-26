package graph;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import parser.SymbolBindings;

public abstract class SingleChildBranch<Child extends Node> extends Branch<Child> {

	private Child child;
	public Child getChild() { return child; }
	@Override
	public List<Child> getChildren() { return Collections.singletonList(child); }
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
		if (SymbolBindings.lowerOrEqualPrecedence(child, this) &&
				child instanceof TwoChildrenBranch<?, ?>)
			childString = "("+child.toString()+")";
		else
			childString = child.toString();
		return getSymbol()+childString;
	}
	@Override
	public Iterator<Child> iterator() {
		return new Iterator<Child>() {
			boolean given = false;
			@Override
			public Child next() {
				given = true;
				return child;
			}
			@Override
			public boolean hasNext() {
				return !given;
			}
		};
	}
	@Override
	public void validate() {
		if (child == null)
			throw new RuntimeException(this+" has its child being null");
		child.validate();
	}
}
