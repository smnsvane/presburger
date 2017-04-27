package graph.formula.comparator;

import graph.Formula;
import graph.Term;
import graph.VariableAssignment;
import graph.formula.Or;

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
	public Or toCooper() {
		CooperLessThan child1 = new CooperLessThan(getFirstChild().toSum(), getSecondChild().toSum());
		CooperLessThan child2 = new CooperLessThan(getSecondChild().toSum(), getFirstChild().toSum());
		Or or = new Or(child1, child2);
		return or;
	}
	@Override
	public NotEqualTo copy() { return new NotEqualTo(getFirstChild().copy(), getSecondChild().copy()); }
}
