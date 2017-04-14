package graph.logic;

import graph.VariableAssignment;

public class True implements Logic {

	public static final String symbol = "true";
	@Override
	public boolean evaluate(VariableAssignment varAss) { return true; }
	@Override
	public Logic negate() { return new True(); }
}
