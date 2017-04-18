package graph.formula;

import graph.Formula;
import graph.Leaf;
import graph.VariableAssignment;

public class True implements Formula, Leaf {

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
	public Formula copy() { return new True(); }
}
