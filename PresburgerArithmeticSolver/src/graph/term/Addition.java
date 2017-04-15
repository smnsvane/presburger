package graph.term;

import graph.TwoChildrenBranch;
import graph.VariableAssignment;

public class Addition extends TwoChildrenBranch<Term, Term> implements Term {

	@Override
	public String getSymbol() { return "+"; }
	@Override
	public int evaluate(VariableAssignment varAss) {
		return getFirstChild().evaluate(varAss) +
				getSecondChild().evaluate(varAss);
	}
	@Override
	public Sum toSum() {
		return Sum.sumFromChildren(getFirstChild(), getSecondChild());
	}
	@Override
	public Addition multiply(int factor) {
		setFirstChild(getFirstChild().multiply(factor));
		setSecondChild(getSecondChild().multiply(factor));
		return this;
	}
	@Override
	public Addition simplify() {
		setFirstChild(getFirstChild().simplify());
		setSecondChild(getSecondChild().simplify());
		return this;
	}
}
