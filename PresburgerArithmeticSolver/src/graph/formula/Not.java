package graph.formula;

import graph.SingleChildBranch;
import graph.VariableAssignment;

public class Not extends SingleChildBranch<Formula> implements Formula {

	@Override
	public String getSymbol() { return "~"; }
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
}
