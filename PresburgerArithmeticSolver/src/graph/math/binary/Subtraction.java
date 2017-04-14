package graph.math.binary;

import graph.Branch;
import graph.VariableAssignment;

public class Subtraction extends BinaryMathOperator {

	public static final String symbol = "-";
	public Subtraction(Branch parent) { super(parent, symbol); }

	@Override
	public int evaluate(VariableAssignment varAss) {
		return getFirstChild().evaluate(varAss) -
				getSecondChild().evaluate(varAss);
	}

}
