package graph.formula;

import graph.Formula;
import graph.SingleChildBranch;
import graph.VariableAssignment;
import graph.formula.quantifier.Exists;

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
		if (getChild() instanceof Exists)
			return this;
		Formula child = getChild().negate();
		return (Formula) child.simplify();
	}
	@Override
	public Not copy() { return new Not(getChild().copy()); }
}
