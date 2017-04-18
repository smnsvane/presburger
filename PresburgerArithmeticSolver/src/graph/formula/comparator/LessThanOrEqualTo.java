package graph.formula.comparator;

import graph.Formula;
import graph.Term;
import graph.VariableAssignment;
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
	public LessThan toLessThan() {
		LessThan less = new LessThan(getFirstChild(), new Sum(getSecondChild(), new Constant(1)));
		return less;
	}
	@Override
	public LessThanOrEqualTo isolate() {
		LessThanOrEqualTo lessOrEqual = new LessThanOrEqualTo(new Constant(0), getSecondChild().toSum().sumDiff(getFirstChild().toSum()));
		return lessOrEqual;
	}
	@Override
	public LessThanOrEqualTo copy() { return new LessThanOrEqualTo(getFirstChild().copy(), getSecondChild().copy()); }
}
