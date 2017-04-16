package graph.term;

import graph.TwoChildrenBranch;
import graph.VariableAssignment;

public class Addition extends TwoChildrenBranch<Term, Term> implements Term {

	@Override
	public String getSymbol() { return "+"; }
	public Addition(Term child1, Term child2) { super(child1, child2); }
	@Override
	public int evaluate(VariableAssignment varAss) {
		return getFirstChild().evaluate(varAss) +
				getSecondChild().evaluate(varAss);
	}
	@Override
	public Sum toSum() {
		return new Sum(getFirstChild(), getSecondChild());
	}
	@Override
	public Addition multiply(int factor) {
		Addition add = new Addition(getFirstChild().multiply(factor), getSecondChild().multiply(factor));
		return add;
	}
	@Override
	public Term simplify() {
		if (getFirstChild().equals(0))
			return getSecondChild();
		if (getSecondChild().equals(0))
			return getFirstChild();
		return this;
	}
}
