package graph.math.comparator;

import graph.VariableAssignment;
import graph.logic.Formula;

public class NotEqualTo extends Comparator {

	public static final String symbol = "!=";

	@Override
	public String toString() {
		return getFirstChild()+symbol+getSecondChild();
	}

	@Override
	public boolean evaluate(VariableAssignment varAss) {
		return getFirstChild().evaluate(varAss) !=
				getSecondChild().evaluate(varAss);
	}

	@Override
	public Formula negate() {
		EqualTo equal = new EqualTo();
		equal.setFirstChild(getFirstChild());
		equal.setSecondChild(getSecondChild());
		return equal;
	}
}
