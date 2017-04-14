package graph.math.binary;

import java.util.Iterator;

import graph.Branch;
import graph.Node;
import graph.AbstractNode;
import graph.BinaryBranch;
import graph.math.Math;

public abstract class BinaryMathOperator extends AbstractNode implements BinaryBranch<Math>, Math {

	public BinaryMathOperator(Branch parent, String identifier) {
		super(parent, identifier);
	}

	private Math child1, child2;
	@Override
	public Math getFirstChild() { return child1; }
	@Override
	public Math getSecondChild() { return child2; }
	@Override
	public void setFirstChild(Math child) { child1 = child; }
	@Override
	public void setSecondChild(Math child) { child2 = child; }

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
