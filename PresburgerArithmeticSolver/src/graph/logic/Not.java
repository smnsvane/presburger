package graph.logic;

import graph.NodeBranch;
import graph.VariableAssignment;

public class Not implements NodeBranch, Formula {

	public static final String symbol = "~";
	//TODO: include '(' and ')' if child have lower precedence than '~'
	@Override
	public String toString() { return symbol+child; }

	private Formula child;
	public Formula getChild() { return child; }
	public void setChild(Formula child) { this.child = child; }

	@Override
	public boolean evaluate(VariableAssignment varAss) {
		return !getChild().evaluate(varAss);
	}

	@Override
	public Formula negate() { return child; }
}
