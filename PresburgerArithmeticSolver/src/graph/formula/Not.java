package graph.formula;

import graph.Formula;
import graph.SingleChildBranch;
import graph.VariableAssignment;

public class Not extends SingleChildBranch<Formula> implements Formula {

	public Not(Formula child) { super(child); }
	@Override
	public boolean evaluate(VariableAssignment varAss) {
		return !getChild().evaluate(varAss);
	}
	@Override
	public Formula negate() { return getChild(); }
	@Override
	public Formula simplify() {
		Formula child = getChild().negate();
		return (Formula) child.simplify();
	}
	@Override
	public Not copy() { return new Not(getChild().copy()); }
}
