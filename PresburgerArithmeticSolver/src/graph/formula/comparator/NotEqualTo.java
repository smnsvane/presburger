package graph.formula.comparator;

import graph.Formula;
import graph.Term;
import graph.VariableAssignment;
import graph.formula.Or;
import graph.term.Constant;

public class NotEqualTo extends Comparator {

	public NotEqualTo(Term child1, Term child2) { super(child1, child2); }
	@Override
	public boolean evaluate(VariableAssignment varAss) {
		return getFirstChild().evaluate(varAss) !=
				getSecondChild().evaluate(varAss);
	}
	@Override
	public Formula negate() {
		EqualTo equal = new EqualTo(getFirstChild(), getSecondChild());
		return equal;
	}
	@Override
	public Or toLessThan() {
		LessThan child1 = new LessThan(getFirstChild(), getSecondChild());
		LessThan child2 = new LessThan(getSecondChild(), getFirstChild());
		Or or = new Or(child1, child2);
		return or;
	}
	@Override
	public NotEqualTo isolate() {
		NotEqualTo notEqual = new NotEqualTo(new Constant(0), getSecondChild().toSum().sumDiff(getFirstChild().toSum()));
		return notEqual;
	}
	@Override
	public NotEqualTo copy() { return new NotEqualTo(getFirstChild().copy(), getSecondChild().copy()); }
}
