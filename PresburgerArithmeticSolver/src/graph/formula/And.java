package graph.formula;

import graph.TwoChildrenBranch;
import graph.VariableAssignment;

public class And extends TwoChildrenBranch<Formula, Formula> implements Formula {

	public static final String symbol = "&";

	@Override
	public boolean evaluate(VariableAssignment varAss) {
		return getFirstChild().evaluate(varAss) &&
				getSecondChild().evaluate(varAss);
	}

	//TODO: include '(' and ')' if children have lower precedence than 'this'
	@Override
	public String toString() {
		return getFirstChild()+symbol+getSecondChild();
	}
	@Override
	public Formula negate() {
		Or or = new Or();
		or.setFirstChild(getFirstChild().negate());
		or.setSecondChild(getSecondChild().negate());
		return or;
	}
	@Override
	public Formula simplify() {
		setFirstChild(getFirstChild().simplify());
		setSecondChild(getSecondChild().simplify());
		if (getFirstChild() instanceof True)
			return getSecondChild();
		if (getSecondChild() instanceof True)
			return getFirstChild();
		if (getFirstChild() instanceof False || getSecondChild() instanceof False)
			return new False();
		return this;
	}
}
