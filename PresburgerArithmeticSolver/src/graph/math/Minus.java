package graph.math;

import java.util.Iterator;

import graph.UnaryBranch;
import graph.VariableAssignment;

public class Minus implements UnaryBranch<Math>, Math {

	public static final String symbol = "-";

	//TODO: include '(' and ')' if child have lower precedence than '-'
	@Override
	public String toString() { return symbol+getChild(); }

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
	public Iterator<Math> iterator() {
		return new Iterator<Math>() {
			boolean done = false;
			@Override
			public Math next() {
				done = true;
				return child;
			}
			@Override
			public boolean hasNext() { return done; }
		};
	}
}
