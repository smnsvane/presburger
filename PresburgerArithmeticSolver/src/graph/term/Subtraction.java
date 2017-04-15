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
		sum.addChild(getFirstChild().toSum());
		sum.addChild(getSecondChild().multiply(-1).toSum());
		return sum;
	}
	@Override
	public Addition simplify() {
		Addition add = new Addition();
		add.setFirstChild(getFirstChild());
		add.setSecondChild(getSecondChild().multiply(-1));
		return add;
	}
}
