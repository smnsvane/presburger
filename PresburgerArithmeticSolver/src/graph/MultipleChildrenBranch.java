package graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class MultipleChildrenBranch<Child extends Node> implements Branch {

	private ArrayList<Child> children = new ArrayList<>();
	public void addChild(Child child) { children.add(child); }
	public List<Child> viewChildren() { return Collections.unmodifiableList(children); }
	public ArrayList<Child> editChildren() { return children; }
	@SuppressWarnings("unchecked")
	@Override
	public void replaceChild(Node victim, Node overtaker) {
		int index = children.indexOf(victim);
		if (index == -1)
			throw new RuntimeException("child not found");
		children.set(index, (Child) overtaker);
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
}
