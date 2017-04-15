package graph.formula.comparator;

import graph.VariableAssignment;
import graph.formula.False;
import graph.formula.Formula;
import graph.formula.True;
import graph.term.Sum;

public class LessThanOrEqualTo extends Comparator {

	@Override
	public String getSymbol() { return "<="; }
	@Override
	public boolean evaluate(VariableAssignment varAss) {
		return getFirstChild().evaluate(varAss) <=
				getSecondChild().evaluate(varAss);
	}
	@Override
	public Formula negate() {
		GreaterThan greater = new GreaterThan();
		greater.setFirstChild(getFirstChild());
		greater.setSecondChild(getSecondChild());
		return greater;
	}
	@Override
	public Formula simplify() {
		LessThanOrEqualTo lessOrEqual = (LessThanOrEqualTo) super.simplify();
		Sum sum1 = (Sum) lessOrEqual.getFirstChild();
		Sum sum2 = (Sum) lessOrEqual.getSecondChild();
		if (sum1.isConstant() && sum2.isConstant())
			if (sum1.evaluate(null) <= sum2.evaluate(null))
				return new True();
			else
				return new False();
		return lessOrEqual;
	}
}
