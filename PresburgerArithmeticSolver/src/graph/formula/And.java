package graph.formula;

import graph.TwoChildrenBranch;
import graph.VariableAssignment;

public class And extends TwoChildrenBranch<Formula, Formula> implements Formula {

	public And(Formula child1, Formula child2) { super(child1, child2); }
	@Override
	public boolean evaluate(VariableAssignment varAss) {
		return getFirstChild().evaluate(varAss) &&
				getSecondChild().evaluate(varAss);
	}
	@Override
	public Formula negate() {
		return new Or(getFirstChild().negate(), getSecondChild().negate());
	}
	@Override
	public Formula simplify() {
		if (getFirstChild() instanceof True)
			return getSecondChild();
		if (getSecondChild() instanceof True)
			return getFirstChild();
		if (getFirstChild() instanceof False || getSecondChild() instanceof False)
			return new False();
		return this;
	}
	@Override
	public And copy() { return new And(getFirstChild().copy(), getSecondChild().copy()); }
}
