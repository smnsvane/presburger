package graph.formula.comparator;

import graph.Formula;
import graph.Term;
import graph.VariableAssignment;
import graph.term.Constant;
import graph.term.Sum;

public class LessThan extends Comparator {

	public LessThan(Term child1, Term child2) { super(child1, child2); }
	@Override
	public boolean evaluate(VariableAssignment varAss) {
		return getFirstChild().evaluate(varAss) <
				getSecondChild().evaluate(varAss);
	}
	@Override
	public Formula negate() {
		GreaterThanOrEqualTo greaterOrEqual = new GreaterThanOrEqualTo(getFirstChild(), getSecondChild());
		return greaterOrEqual;
	}
	@Override
	public LessThan toLessThan() { return this; }
	@Override
	public LessThan isolate() {
		LessThan less = new LessThan(new Constant(0),
				Sum.isolationSum(getSecondChild().toSum(), getFirstChild().toSum()));
		return less;
	}
	@Override
	public LessThan copy() { return new LessThan(getFirstChild().copy(), getSecondChild().copy()); }
}
