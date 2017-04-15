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
	public Term simplify() {
		if (getFirstChild().equals(0))
			return getSecondChild();
		if (getSecondChild().equals(0))
			return getFirstChild();
		return this;
	}
	@Override
	public Addition copy() {
		Addition copy = new Addition();
		copy.setFirstChild((Term) getFirstChild().copy());
		copy.setSecondChild((Term) getSecondChild().copy());
		return copy;
	}
}
