package graph.math.binary;

import graph.Branch;
import graph.VariableAssignment;

public class Addition extends BinaryMathOperator {

	public Addition(Branch parent) { super(parent, "+"); }

	@Override
	public int evaluate(VariableAssignment varAss) {
		return getFirstChild().evaluate(varAss) +
				getSecondChild().evaluate(varAss);
	}

}
