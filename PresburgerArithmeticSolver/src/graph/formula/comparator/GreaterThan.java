package graph.formula.comparator;

import graph.VariableAssignment;
import graph.formula.Formula;
import graph.term.Constant;
import graph.term.Sum;
import graph.term.Term;

public class GreaterThan extends Comparator {

	@Override
	public String getSymbol() { return ">"; }
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
		GreaterThan greater = new GreaterThan(new Constant(0),
				Sum.isolationSum(getSecondChild().toSum(), getFirstChild().toSum()));
		return greater;
	}
}
