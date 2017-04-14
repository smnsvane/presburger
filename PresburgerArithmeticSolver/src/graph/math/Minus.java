package graph.math;

import java.util.Iterator;

import graph.Branch;
import graph.Node;
import graph.UnaryBranch;
import graph.AbstractNode;
import graph.VariableAssignment;

public class Minus extends AbstractNode implements UnaryBranch<Math>, Math {

	public static final String symbol = "-";
	public Minus(Branch parent) { super(parent, symbol); }

	private Math child;
	@Override
	public Math getChild() { return child; }
	@Override
	public void setChild(Math child) { this.child = child; }

	@Override
	public int evaluate(VariableAssignment varAss) {
		return - getChild().evaluate(varAss);
	}

	@Override
	public Iterator<Node> iterator() {
		return new Iterator<Node>() {
			boolean done = false;
			@Override
			public Node next() {
				done = true;
				return child;
			}
			@Override
			public boolean hasNext() { return done; }
		};
	}
}
