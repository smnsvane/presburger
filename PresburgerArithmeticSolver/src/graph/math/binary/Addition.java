package graph.math.binary;

import graph.VariableAssignment;

public class Addition extends BinaryTermOperator {

	public static final String symbol = "+";

	@Override
	public String toString() {
		return getFirstChild()+symbol+getSecondChild();
	}

	@Override
	public int evaluate(VariableAssignment varAss) {
		return getFirstChild().evaluate(varAss) +
				getSecondChild().evaluate(varAss);
	}
}
