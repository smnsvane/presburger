package graph.logic;

import graph.VariableAssignment;

public class False implements Logic {

	public static final String symbol = "false";
	@Override
	public boolean evaluate(VariableAssignment varAss) { return false; }
	@Override
	public Logic negate() { return new True(); }
}
