package graph.formula.comparator;

import graph.VariableAssignment;
import graph.formula.False;
import graph.formula.Formula;
import graph.formula.True;
import graph.term.Sum;

public class NotEqualTo extends Comparator {

	@Override
	public String getSymbol() { return "!="; }
	@Override
	public boolean evaluate(VariableAssignment varAss) {
		return getFirstChild().evaluate(varAss) !=
				getSecondChild().evaluate(varAss);
	}
	@Override
	public Formula negate() {
		EqualTo equal = new EqualTo();
		equal.setFirstChild(getFirstChild());
		equal.setSecondChild(getSecondChild());
		return equal;
	}
	@Override
	public Formula simplify() {
		NotEqualTo notEqual = (NotEqualTo) super.simplify();
		Sum sum1 = (Sum) notEqual.getFirstChild();
		Sum sum2 = (Sum) notEqual.getSecondChild();
		if (sum1.isConstant() && sum2.isConstant())
			if (sum1.evaluate(null) != sum2.evaluate(null))
				return new True();
			else
				return new False();
		return notEqual;
	}
}
