package graph.logic.binary;

import graph.VariableAssignment;
import graph.logic.Logic;

public class And extends BinaryLogicOperator {

	public static final String symbol = "&";

	@Override
	public boolean evaluate(VariableAssignment varAss) {
		return getFirstChild().evaluate(varAss) &&
				getSecondChild().evaluate(varAss);
	}

	//TODO: include '(' and ')' if children have lower precedence than 'this'
	@Override
	public String toString() {
		return getFirstChild()+" "+symbol+" "+getSecondChild();
	}

	@Override
	public Logic negate() {
		Or or = new Or();
		or.setFirstChild(getFirstChild().negate());
		or.setSecondChild(getSecondChild().negate());
		return or;
	}
}
