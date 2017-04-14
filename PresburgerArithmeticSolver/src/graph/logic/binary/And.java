package graph.logic.binary;

import graph.Branch;
import graph.VariableAssignment;

public class And extends BinaryLogicOperator {

	public And(Branch parent) { super(parent, "&"); }

	@Override
	public boolean evaluate(VariableAssignment varAss) {
		return getFirstChild().evaluate(varAss) &&
				getSecondChild().evaluate(varAss);
	}
}
