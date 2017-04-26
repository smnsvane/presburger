package graph.formula;

import graph.Formula;
import graph.Leaf;
import graph.VariableAssignment;

public class False extends Leaf implements Formula {

	@Override
	public String toString() { return getSymbol(); }
	@Override
	public boolean evaluate(VariableAssignment varAss) { return false; }
	@Override
	public Formula negate() { return new True(); }
	@Override
	public False simplify() { return this; }
	@Override
	public boolean equals(Object obj) { return obj instanceof False; }
	@Override
	public Formula copy() { return new False(); }
	@Override
	public void validate() {}
}
