package graph.logic;

import graph.VariableAssignment;

public class True implements Formula {

	public static final String symbol = "true";
	@Override
	public boolean evaluate(VariableAssignment varAss) { return true; }
	@Override
	public Formula negate() { return new True(); }
}
