package graph.formula.comparator;

import graph.Formula;
import graph.Term;
import graph.VariableAssignment;
import graph.term.Constant;

public class GreaterThan extends Comparator {

	public GreaterThan(Term child1, Term child2) { super(child1, child2); }
	@Override
	public boolean evaluate(VariableAssignment varAss) {
		return getFirstChild().evaluate(varAss) >
				getSecondChild().evaluate(varAss);
	}
	@Override
	public Formula negate() {
		LessThanOrEqualTo lessOrEqual = new LessThanOrEqualTo(getFirstChild(), getSecondChild());
		return lessOrEqual;
	}
	@Override
	public LessThan toLessThan() {
		LessThan less = new LessThan(getSecondChild(), getFirstChild());
		return less;
	}
	@Override
	public GreaterThan isolate() {
		GreaterThan greater = new GreaterThan(new Constant(0), getSecondChild().toSum().sumDiff(getFirstChild().toSum()));
		return greater;
	}
	@Override
	public GreaterThan copy() { return new GreaterThan(getFirstChild().copy(), getSecondChild().copy()); }
}
