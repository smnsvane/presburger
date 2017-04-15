package graph.formula.comparator;

import graph.VariableAssignment;
import graph.formula.False;
import graph.formula.Formula;
import graph.formula.True;
import graph.term.Sum;

public class GreaterThan extends Comparator {

	@Override
	public String getSymbol() { return ">"; }
	@Override
	public boolean evaluate(VariableAssignment varAss) {
		return getFirstChild().evaluate(varAss) >
				getSecondChild().evaluate(varAss);
	}
	@Override
	public Formula negate() {
		LessThanOrEqualTo lessOrEqual = new LessThanOrEqualTo();
		lessOrEqual.setFirstChild(getFirstChild());
		lessOrEqual.setSecondChild(getSecondChild());
		return lessOrEqual;
	}
	@Override
	public Formula simplify() {
		GreaterThan greater = (GreaterThan) super.simplify();
		Sum sum1 = (Sum) greater.getFirstChild();
		Sum sum2 = (Sum) greater.getSecondChild();
		if (sum1.isConstant() && sum2.isConstant())
			if (sum1.evaluate(null) > sum2.evaluate(null))
				return new True();
			else
				return new False();
		return greater;
	}
}
