package graph.math.comparator;

import graph.VariableAssignment;
import graph.logic.Logic;

public class GreaterThan extends Comparator {

	public static final String symbol = ">";

	@Override
	public String toString() {
		return getFirstChild()+symbol+getSecondChild();
	}

	@Override
	public boolean evaluate(VariableAssignment varAss) {
		return getFirstChild().evaluate(varAss) >
				getSecondChild().evaluate(varAss);
	}

	@Override
	public Logic negate() {
		LessThanOrEqualTo lessOrEqual = new LessThanOrEqualTo();
		lessOrEqual.setFirstChild(getFirstChild());
		lessOrEqual.setSecondChild(getSecondChild());
		return lessOrEqual;
	}
}
