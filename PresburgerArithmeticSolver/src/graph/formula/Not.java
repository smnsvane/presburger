package graph.formula;

import graph.SingleChildBranch;
import graph.VariableAssignment;

public class Not extends SingleChildBranch<Formula> implements Formula {

	public static final String symbol = "~";
	//TODO: include '(' and ')' if child have lower precedence than '~'
	@Override
	public String toString() { return symbol+getChild(); }

	@Override
	public boolean evaluate(VariableAssignment varAss) {
		return !getChild().evaluate(varAss);
	}

	@Override
	public Formula negate() { return getChild(); }
}
