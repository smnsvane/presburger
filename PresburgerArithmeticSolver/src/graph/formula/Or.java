package graph.formula;

import graph.Formula;
import graph.TwoChildrenBranch;
import graph.VariableAssignment;

public class Or extends TwoChildrenBranch<Formula, Formula> implements Formula {

	public Or(Formula child1, Formula child2) { super(child1, child2); }
	@Override
	public boolean evaluate(VariableAssignment varAss) {
		return getFirstChild().evaluate(varAss) ||
				getSecondChild().evaluate(varAss);
	}
	@Override
	public And negate() {
		Formula negatedChild1 = getFirstChild().negate();
		Formula negatedChild2 = getSecondChild().negate();
		And and = new And(negatedChild1, negatedChild2);
		return and;
	}
	@Override
	public Formula reduce() {
		if (getFirstChild() instanceof True || getSecondChild() instanceof True)
			return new True();
		if (getFirstChild() instanceof False)
			return getSecondChild();
		if (getSecondChild() instanceof False)
			return getFirstChild();
		return this;
	}
	@Override
	public Or copy() { return new Or(getFirstChild().copy(), getSecondChild().copy()); }
}
