package graph.logic;

import java.util.Iterator;

import graph.AbstractNode;
import graph.Branch;
import graph.Node;
import graph.UnaryBranch;
import graph.VariableAssignment;

public class Not extends AbstractNode implements UnaryBranch<Logic>, Logic {

	public static final String symbol = "~";
	public Not(Branch parent) { super(parent, symbol); }

	private Logic child;
	@Override
	public Logic getChild() { return child; }
	@Override
	public void setChild(Logic child) { this.child = child; }

	@Override
	public boolean evaluate(VariableAssignment varAss) {
		return !getChild().evaluate(varAss);
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
