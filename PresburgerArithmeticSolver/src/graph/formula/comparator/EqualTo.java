package graph.formula.comparator;

import graph.VariableAssignment;
import graph.formula.And;
import graph.formula.Formula;
import graph.formula.True;
import graph.term.Addition;
import graph.term.Constant;
import graph.term.Sum;
import graph.term.Term;

public class EqualTo extends Comparator {

	@Override
	public String getSymbol() { return "="; }
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
		EqualTo equal = new EqualTo(new Sum(), Sum.isolationSum(getSecondChild().toSum(), getFirstChild().toSum()));
		return equal;
	}
}
