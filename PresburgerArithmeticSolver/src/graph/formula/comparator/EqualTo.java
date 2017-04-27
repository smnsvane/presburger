package graph.formula.comparator;

import graph.Formula;
import graph.Term;
import graph.VariableAssignment;
import graph.formula.And;
import graph.formula.True;
import graph.term.Constant;

public class EqualTo extends Comparator {

	public EqualTo(Term child1, Term child2) { super(child1, child2); }
	@Override
	public boolean evaluate(VariableAssignment varAss) {
		return getFirstChild().evaluate(varAss) ==
				getSecondChild().evaluate(varAss);
	}
	@Override
	public NotEqualTo negate() {
		NotEqualTo notEqual = new NotEqualTo(getFirstChild(), getSecondChild());
		return notEqual;
	}
	@Override
	public Formula reduce() {
		if (getFirstChild().equals(getSecondChild()))
			return new True();
		return super.reduce();
	}
	@Override
	public And toCooper() {
		CooperLessThan child1 =
			new CooperLessThan(getFirstChild().toSum(), getSecondChild().toSum().add(new Constant(1)));
		CooperLessThan child2 =
				new CooperLessThan(getSecondChild().toSum(), getFirstChild().toSum().add(new Constant(1)));
		And and = new And(child1, child2);
		return and;
	}
	@Override
	public EqualTo copy() { return new EqualTo(getFirstChild().copy(), getSecondChild().copy()); }
}
