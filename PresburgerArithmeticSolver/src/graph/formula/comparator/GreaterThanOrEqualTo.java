package graph.formula.comparator;

import graph.Formula;
import graph.Term;
import graph.VariableAssignment;
import graph.term.Constant;

public class GreaterThanOrEqualTo extends Comparator {

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
	public CooperLessThan toCooper() {
		CooperLessThan less = new CooperLessThan(getSecondChild().toSum(), getFirstChild().toSum().add(new Constant(1)));
		return less;
	}
	@Override
	public GreaterThanOrEqualTo copy() { return new GreaterThanOrEqualTo(getFirstChild().copy(), getSecondChild().copy()); }
}
