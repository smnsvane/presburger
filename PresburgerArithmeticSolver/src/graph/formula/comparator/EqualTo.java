package graph.formula.comparator;

import graph.VariableAssignment;
import graph.formula.Formula;

public class EqualTo extends Comparator {

	public static final String symbol = "=";

	@Override
	public String toString() {
		return getFirstChild()+symbol+getSecondChild();
	}

	@Override
	public boolean evaluate(VariableAssignment varAss) {
		return getFirstChild().evaluate(varAss) ==
				getSecondChild().evaluate(varAss);
	}

	@Override
	public Formula negate() {
		NotEqualTo notEqual = new NotEqualTo();
		notEqual.setFirstChild(getFirstChild());
		notEqual.setSecondChild(getSecondChild());
		return notEqual;
	}
}
