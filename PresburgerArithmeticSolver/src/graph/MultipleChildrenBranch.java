package graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

import parser.NodeSorter;

public abstract class MultipleChildrenBranch<Child extends Node> extends Branch<Child> {

	private ArrayList<Child> children = new ArrayList<>();
	@Override
	public List<Child> getChildren() { return Collections.unmodifiableList(children); }
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
		Collections.sort(this.children, new NodeSorter());
	}
	// custom implementation to allow use of "replaceChild(..)" call during iteration
	@Override
	public ListIterator<Child> iterator() {
		return new ListIterator<Child>() {
			int index = 0;
			@Override
			public boolean hasNext() { return index < children.size(); }
			@Override
			public Child next() { return children.get(index++); }
			@Override
			public void add(Child e) { throw new RuntimeException("not implemented"); }
			@Override
			public boolean hasPrevious() { return index > 0; }
			@Override
			public int nextIndex() { return index; }
			@Override
			public Child previous() { return children.get(--index); }
			@Override
			public int previousIndex() { return index - 1; }
			@Override
			public void remove() {
				if (isLocked())
					throw new RuntimeException("Locked");
				children.remove(--index);
			}
			@Override
			public void set(Child e) {
				if (isLocked())
					throw new RuntimeException("Locked");
				children.set(index - 1, e);
			}
		};
	}
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
	@Override
	public void validate() {
		if (!children.isEmpty())
			throw new RuntimeException(this+" have no children");
		for (Child c : this)
			if (c == null)
				throw new RuntimeException(this+" has a null child");
			else
				validate();
	}
}
