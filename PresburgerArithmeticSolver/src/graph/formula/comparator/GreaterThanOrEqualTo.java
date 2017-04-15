package graph.formula.comparator;

import graph.VariableAssignment;
import graph.formula.False;
import graph.formula.Formula;
import graph.formula.True;
import graph.term.Sum;
import graph.term.Term;

public class GreaterThanOrEqualTo extends Comparator {

	@Override
	public String getSymbol() { return ">="; }
	@Override
	public boolean evaluate(VariableAssignment varAss) {
		return getFirstChild().evaluate(varAss) >=
				getSecondChild().evaluate(varAss);
	}
	@Override
	public Formula negate() {
		LessThan less = new LessThan();
		less.setFirstChild(getFirstChild());
		less.setSecondChild(getSecondChild());
		return less;
	}
	@Override
	public Formula simplify() {
		if (getFirstChild() instanceof Sum && getSecondChild() instanceof Sum) {
			Sum sum1 = (Sum) getFirstChild();
			Sum sum2 = (Sum) getSecondChild();
			if (sum1.isConstant() && sum2.isConstant())
				if (sum1.evaluate(null) >= sum2.evaluate(null))
					return new True();
				else
					return new False();
		}
		return this;
	}
	@Override
	public GreaterThanOrEqualTo copy() {
		GreaterThanOrEqualTo copy = new GreaterThanOrEqualTo();
		copy.setFirstChild((Term) getFirstChild().copy());
		copy.setSecondChild((Term) getSecondChild().copy());
		return copy;
	}
}
