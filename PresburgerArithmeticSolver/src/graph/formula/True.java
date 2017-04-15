package graph.formula;

import graph.VariableAssignment;

public class True implements Formula {

	@Override
	public String getSymbol() { return "true"; }
	@Override
	public String toString() { return getSymbol(); }
	@Override
	public boolean evaluate(VariableAssignment varAss) { return true; }
	@Override
	public Formula negate() { return new True(); }
	@Override
	public True simplify() { return this; }
	@Override
	public boolean equals(Object obj) { return obj instanceof True; }
	@Override
	public True copy() { return new True(); }
}
