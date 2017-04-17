package graph.formula.comparator;

import graph.Formula;
import graph.Term;
import graph.VariableAssignment;
import graph.formula.And;
import graph.formula.True;
import graph.term.Addition;
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
	public Formula simplify() {
		if (getFirstChild().equals(getSecondChild()))
			return new True();
		return super.simplify();
	}
	@Override
	public And toLessThan() {
		LessThan child1 =
			new LessThan(getFirstChild(), new Addition(getSecondChild(), new Constant(1)));
		LessThan child2 =
				new LessThan(getSecondChild(), new Addition(getFirstChild(), new Constant(1)));
		And and = new And(child1, child2);
		return and;
	}
	@Override
	public EqualTo isolate() {
		EqualTo equal = new EqualTo(new Constant(0), getSecondChild().toSum().sumDiff(getFirstChild().toSum()));
		return equal;
	}
	@Override
	public EqualTo copy() { return new EqualTo(getFirstChild().copy(), getSecondChild().copy()); }
}
