package graph.math.binary;

import graph.VariableAssignment;

public class Addition extends BinaryMathOperator {

	public static final String symbol = "+";

	@Override
	public String toString() {
		return getFirstChild().toString()+symbol+getSecondChild();
	}

	@Override
	public int evaluate(VariableAssignment varAss) {
		return getFirstChild().evaluate(varAss) +
				getSecondChild().evaluate(varAss);
	}

}
