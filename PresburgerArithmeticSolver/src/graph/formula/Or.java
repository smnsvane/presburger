package graph.formula;

import graph.TwoChildrenBranch;
import graph.VariableAssignment;

public class Or extends TwoChildrenBranch<Formula, Formula> implements Formula {

	public static final String symbol = "/";

	//TODO: include '(' and ')' if children have lower precedence than 'this'
	@Override
	public String toString() {
		return getFirstChild()+symbol+getSecondChild();
	}

	@Override
	public boolean evaluate(VariableAssignment varAss) {
		return getFirstChild().evaluate(varAss) ||
				getSecondChild().evaluate(varAss);
	}

	@Override
	public Formula negate() {
		And and = new And();
		and.setFirstChild(getFirstChild().negate());
		and.setSecondChild(getSecondChild().negate());
		return and;
	}

	@Override
	public Formula simplify() {
		setFirstChild(getFirstChild().simplify());
		setSecondChild(getSecondChild().simplify());
		if (getFirstChild() instanceof True || getSecondChild() instanceof True)
			return new True();
		if (getFirstChild() instanceof False)
			return getSecondChild();
		if (getSecondChild() instanceof False)
			return getFirstChild();
		return this;
	}
}
