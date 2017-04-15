package graph.formula;

import graph.Node;
import graph.VariableAssignment;

public class True implements Formula {

	public static final String symbol = "true";
	@Override
	public boolean evaluate(VariableAssignment varAss) { return true; }
	@Override
	public Formula negate() { return new True(); }
	@Override
	public Node replaceVariables(VariableAssignment assignment) { return this; }
}
