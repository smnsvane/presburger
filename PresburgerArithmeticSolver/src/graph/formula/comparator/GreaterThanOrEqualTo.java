package graph.formula.comparator;

import graph.VariableAssignment;
import graph.formula.Formula;

public class GreaterThanOrEqualTo extends Comparator {

	public static final String symbol = ">=";

	@Override
	public String toString() {
		return getFirstChild()+symbol+getSecondChild();
	}

	@Override
	public boolean evaluate(VariableAssignment varAss) {
		return getFirstChild().evaluate(varAss) >=
				getSecondChild().evaluate(varAss);
	}

	@Override
	public Formula negate() {
		LessThan less = new LessThan();
		less.setFirstChild(getFirstChild());
		less.setSecondChild(getSecondChild());
		return less;
	}
}