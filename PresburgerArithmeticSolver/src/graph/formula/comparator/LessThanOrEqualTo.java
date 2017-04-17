package graph.formula.comparator;

import graph.Formula;
import graph.Term;
import graph.VariableAssignment;
import graph.term.Addition;
import graph.term.Constant;
import graph.term.Sum;

public class LessThanOrEqualTo extends Comparator {

	public LessThanOrEqualTo(Term child1, Term child2) { super(child1, child2); }
	@Override
	public boolean evaluate(VariableAssignment varAss) {
		return getFirstChild().evaluate(varAss) <=
				getSecondChild().evaluate(varAss);
	}
	@Override
	public Formula negate() {
		GreaterThan greater = new GreaterThan(getFirstChild(), getSecondChild());
		return greater;
	}
	@Override
	public Formula toLessThan() {
		Addition add = new Addition(getSecondChild(), new Constant(1));
		LessThan less = new LessThan(getFirstChild(), add);
		return less;
	}
	@Override
	public LessThanOrEqualTo isolate() {
		LessThanOrEqualTo lessOrEqual = new LessThanOrEqualTo(new Constant(0),
				Sum.isolationSum(getSecondChild().toSum(), getFirstChild().toSum()));
		return lessOrEqual;
	}
	@Override
	public LessThanOrEqualTo copy() { return new LessThanOrEqualTo(getFirstChild().copy(), getSecondChild().copy()); }
}
