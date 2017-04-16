package graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public abstract class MultipleChildrenBranch<Child extends Node> extends Branch<Child> implements Iterable<Child> {

	private ArrayList<Child> children;
	@Override
	public void replaceChild(Child victim, Child overtaker) {
		if (isLocked())
			throw new RuntimeException("Locked");
		int index = children.indexOf(victim);
		if (index == -1)
			throw new RuntimeException("Can't find victim "+victim);
		children.set(index, overtaker);
	}
	public MultipleChildrenBranch(Collection<Child> children) {
		this.children.addAll(children);
	}
	@Override
	public Iterator<Child> iterator() { return children.iterator(); }
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof MultipleChildrenBranch<?>))
			return false;
		MultipleChildrenBranch<?> other = (MultipleChildrenBranch<?>) obj;
		if (!getSymbol().equals(other.getSymbol()))
			return false;
		if (children.size() != other.children.size())
			return false;
		for (int i = 0; i < children.size(); i++)
			if (!children.get(i).equals(other.children.get(i)))
				return false;
		return true;
	}
	@Override
	public String toString() { return getSymbol()+children; }
}
