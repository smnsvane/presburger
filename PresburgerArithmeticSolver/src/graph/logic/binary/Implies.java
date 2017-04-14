package graph.logic.binary;

import graph.VariableAssignment;
import graph.logic.Logic;

public class Implies extends BinaryLogicOperator {

	public static final String symbol = "->";

	//TODO: include '(' and ')' if children have lower precedence than 'this'
	@Override
	public String toString() {
		return getFirstChild()+" "+symbol+" "+getSecondChild();
	}

	@Override
	public boolean evaluate(VariableAssignment varAss) {
		return !getFirstChild().evaluate(varAss) ||
				getSecondChild().evaluate(varAss);
	}

	@Override
	public Logic negate() {
		And and = new And();
		and.setFirstChild(getFirstChild());
		and.setSecondChild(getSecondChild().negate());
		return and;
	}
}
