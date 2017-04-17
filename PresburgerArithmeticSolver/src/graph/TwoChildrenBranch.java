package graph;

import java.util.Iterator;

import parser.SymbolBinding;

public abstract class TwoChildrenBranch<Child1 extends Node, Child2 extends Node> extends Branch<Node> {

	private Child1 child1;
	private Child2 child2;
	public Child1 getFirstChild() { return child1; }
	public Child2 getSecondChild() { return child2; }
	@SuppressWarnings("unchecked")
	@Override
	public void replaceChild(Node victim, Node overtaker) {
		if (isLocked())
			throw new RuntimeException("Locked");
		if (victim.equals(child1))
			child1 = (Child1) overtaker;
		else if (victim.equals(child2)) //TODO: what if the children are equal and I want to replace the second child?
			child2 = (Child2) overtaker;
		else
			throw new RuntimeException("Can't find victim "+victim);
	}
	public TwoChildrenBranch(Child1 child1, Child2 child2) {
		this.child1 = child1;
		this.child2 = child2;
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
	public String toString() {
		String child1String;
		if (SymbolBinding.lowerOrEqualPrecedence(child1, this))
			child1String = "("+child1.toString()+")";
		else
			child1String = child1.toString();
		String child2String;
		if (SymbolBinding.lowerOrEqualPrecedence(child2, this))
			child2String = "("+child2.toString()+")";
		else
			child2String = child2.toString();
		return child1String+getSymbol()+child2String;
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
