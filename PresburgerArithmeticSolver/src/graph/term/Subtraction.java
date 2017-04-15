package graph.term;

import graph.TwoChildrenBranch;
import graph.VariableAssignment;

public class Subtraction extends TwoChildrenBranch<Term, Term> implements Term {

	@Override
	public String getSymbol() { return "-"; }
	@Override
	public int evaluate(VariableAssignment varAss) {
		return getFirstChild().evaluate(varAss) -
				getSecondChild().evaluate(varAss);
	}
	@Override
	public Subtraction multiply(int factor) {
		setFirstChild(getFirstChild().multiply(factor));
		setSecondChild(getSecondChild().multiply(factor));
		return this;
	}
	@Override
	public Sum toSum() {
		return Sum.sumFromChildren(getFirstChild().simplify(), getSecondChild().multiply(-1).simplify());
	}
	@Override
	public Addition simplify() {
		Addition add = new Addition();
		add.setFirstChild(getFirstChild());
		add.setSecondChild(getSecondChild().multiply(-1));
		return add;
	}
	@Override
	public Subtraction copy() {
		Subtraction copy = new Subtraction();
		copy.setFirstChild((Term) getFirstChild().copy());
		copy.setSecondChild((Term) getSecondChild().copy());
		return copy;
	}
}
