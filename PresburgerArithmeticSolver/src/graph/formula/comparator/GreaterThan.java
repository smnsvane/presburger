package graph.formula.comparator;

import graph.Formula;
import graph.Term;
import graph.VariableAssignment;

public class GreaterThan extends Comparator {

	public GreaterThan(Term child1, Term child2) { super(child1, child2); }
	@Override
	public boolean evaluate(VariableAssignment assignment) {
		int eval1 = getFirstChild().evaluate(assignment);
		int eval2 = getSecondChild().evaluate(assignment);
		return eval1 > eval2;
	}
	@Override
	public Formula negate() {
		LessThanOrEqualTo lessOrEqual = new LessThanOrEqualTo(getFirstChild(), getSecondChild());
		return lessOrEqual;
	}
	@Override
	public CooperLessThan toCooper() {
		CooperLessThan less = new CooperLessThan(getSecondChild().toSum(), getFirstChild().toSum());
		return less;
	}
	@Override
	public GreaterThan copy() { return new GreaterThan(getFirstChild().copy(), getSecondChild().copy()); }
}
