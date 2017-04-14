package graph.logic.binary;

import graph.Branch;
import graph.VariableAssignment;

public class And extends BinaryLogicOperator {

	public static final String symbol = "&";
	public And(Branch parent) { super(parent, symbol); }

	@Override
	public boolean evaluate(VariableAssignment varAss) {
		return getFirstChild().evaluate(varAss) &&
				getSecondChild().evaluate(varAss);
	}
}
