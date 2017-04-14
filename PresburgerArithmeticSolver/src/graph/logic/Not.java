package graph.logic;

import java.util.Iterator;

import graph.UnaryBranch;
import graph.VariableAssignment;

public class Not implements UnaryBranch<Logic>, Logic {

	public static final String symbol = "~";
	//TODO: include '(' and ')' if child have lower precedence than '~'
	@Override
	public String toString() { return symbol+child; }

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
	public Logic negate() { return child; }

	@Override
	public Iterator<Logic> iterator() {
		return new Iterator<Logic>() {
			boolean done = false;
			@Override
			public Logic next() {
				done = true;
				return child;
			}
			@Override
			public boolean hasNext() { return done; }
		};
	}
}
