package graph.formula;

import graph.Formula;
import graph.Leaf;
import graph.VariableAssignment;

public class True extends Leaf implements Formula {

	@Override
	public String toString() { return getSymbol(); }
	@Override
	public boolean evaluate(VariableAssignment varAss) { return true; }
	@Override
	public Formula negate() { return new False(); }
	@Override
	public True reduce() { return this; }
	@Override
	public boolean equals(Object obj) { return obj instanceof True; }
	@Override
	public Formula copy() { return new True(); }
	@Override
	public void validate() {}
}
