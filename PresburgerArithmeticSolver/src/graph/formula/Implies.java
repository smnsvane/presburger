package graph.formula;

import graph.Formula;
import graph.TwoChildrenBranch;
import graph.VariableAssignment;

public class Implies extends TwoChildrenBranch<Formula, Formula> implements Formula {

	public Implies(Formula child1, Formula child2) { super(child1, child2); }
	@Override
	public boolean evaluate(VariableAssignment varAss) {
		return !getFirstChild().evaluate(varAss) ||
				getSecondChild().evaluate(varAss);
	}
	@Override
	public Formula negate() {
		And and = new And(getFirstChild(), getSecondChild().negate());
		return and;
	}
	@Override
	public Formula simplify() {
		Not not = new Not(getFirstChild());
		Or or = new Or(not, getSecondChild());
		return or;
	}
	@Override
	public Implies copy() { return new Implies(getFirstChild().copy(), getSecondChild().copy()); }
}
