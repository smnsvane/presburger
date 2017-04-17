package graph.formula.comparator;

import graph.VariableAssignment;
import graph.formula.Formula;
import graph.formula.Or;
import graph.term.Constant;
import graph.term.Sum;
import graph.term.Term;

public class NotEqualTo extends Comparator {

	@Override
	public String getSymbol() { return "!="; }
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
	public Formula toLessThan() {
		LessThan child1 = new LessThan(getFirstChild(), getSecondChild());
		LessThan child2 = new LessThan(getSecondChild(), getFirstChild());
		Or or = new Or(child1, child2);
		return or;
	}
	@Override
	public NotEqualTo isolate() {
		NotEqualTo notEqual = new NotEqualTo(new Constant(0),
				Sum.isolationSum(getSecondChild().toSum(), getFirstChild().toSum()));
		return notEqual;
	}
	@Override
	public NotEqualTo copy() { return new NotEqualTo(getFirstChild().copy(), getSecondChild().copy()); }
}
