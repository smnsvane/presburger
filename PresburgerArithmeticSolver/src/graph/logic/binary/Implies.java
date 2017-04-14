package graph.logic.binary;

import graph.Branch;
import graph.VariableAssignment;

public class Implies extends BinaryLogicOperator {

	public static final String symbol = "->";
	public Implies(Branch parent) { super(parent, symbol); }

	@Override
	public boolean evaluate(VariableAssignment varAss) {
		return !getFirstChild().evaluate(varAss) ||
				getSecondChild().evaluate(varAss);
	}
}
