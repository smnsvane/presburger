package graph.formula.comparator;

import graph.VariableAssignment;
import graph.formula.False;
import graph.formula.Formula;
import graph.formula.True;
import graph.term.Sum;

public class EqualTo extends Comparator {

	@Override
	public String getSymbol() { return "="; }
	@Override
	public boolean evaluate(VariableAssignment varAss) {
		return getFirstChild().evaluate(varAss) ==
				getSecondChild().evaluate(varAss);
	}
	@Override
	public Formula negate() {
		NotEqualTo notEqual = new NotEqualTo();
		notEqual.setFirstChild(getFirstChild());
		notEqual.setSecondChild(getSecondChild());
		return notEqual;
	}
	@Override
	public Formula simplify() {
		if (getFirstChild() instanceof Sum && getSecondChild() instanceof Sum) {
			Sum sum1 = (Sum) getFirstChild();
			Sum sum2 = (Sum) getSecondChild();
			if (sum1.isConstant() && sum2.isConstant())
				if (sum1.evaluate(null) == sum2.evaluate(null))
					return new True();
				else
					return new False();
		}
		return this;
	}
}
