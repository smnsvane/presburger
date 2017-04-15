package graph.logic;

import graph.VariableAssignment;

public class False implements Formula {

	public static final String symbol = "false";
	@Override
	public boolean evaluate(VariableAssignment varAss) { return false; }
	@Override
	public Formula negate() { return new True(); }
	@Override
	public Formula replaceVariables(VariableAssignment assignment) { return this; }
}
