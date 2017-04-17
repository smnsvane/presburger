package graph.formula.comparator;

import graph.VariableAssignment;
import graph.formula.Formula;
import graph.term.Constant;
import graph.term.Sum;
import graph.term.Term;

public class GreaterThanOrEqualTo extends Comparator {

	@Override
	public String getSymbol() { return ">="; }
	public GreaterThanOrEqualTo(Term child1, Term child2) { super(child1, child2); }
	@Override
	public boolean evaluate(VariableAssignment varAss) {
		return getFirstChild().evaluate(varAss) >=
				getSecondChild().evaluate(varAss);
	}
	@Override
	public Formula negate() {
		LessThan less = new LessThan(getFirstChild(), getSecondChild());
		return less;
	}
	@Override
	public Formula toLessThan() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public GreaterThanOrEqualTo isolate() {
		GreaterThanOrEqualTo greaterOrEqual = new GreaterThanOrEqualTo(new Constant(0),
				Sum.isolationSum(getSecondChild().toSum(), getFirstChild().toSum()));
		return greaterOrEqual;
	}
	@Override
	public GreaterThanOrEqualTo copy() { return new GreaterThanOrEqualTo(getFirstChild().copy(), getSecondChild().copy()); }
}
