package graph.term;

import graph.TwoChildrenBranch;
import graph.VariableAssignment;

public class Subtraction extends TwoChildrenBranch<Term, Term> implements Term {

	public static final String symbol = "-";

	@Override
	public String toString() { return getFirstChild()+symbol+getSecondChild(); }
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
		Sum sum = new Sum();
		sum.addChild(getFirstChild());
		sum.addChild(getSecondChild());
		return sum;
	}
}
