package graph.logic.binary;

import graph.Branch;
import graph.Node;

import java.util.Iterator;

import graph.AbstractNode;
import graph.BinaryBranch;
import graph.logic.Logic;

public abstract class BinaryLogicOperator extends AbstractNode implements BinaryBranch<Logic>, Logic {

	public BinaryLogicOperator(Branch parent, String identifier) {
		super(parent, identifier);
	}

	private Logic child1, child2;
	@Override
	public Logic getFirstChild() { return child1; }
	@Override
	public Logic getSecondChild() { return child2; }
	@Override
	public void setFirstChild(Logic child) { child1 = child; }
	@Override
	public void setSecondChild(Logic child) { child2 = child; }

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
