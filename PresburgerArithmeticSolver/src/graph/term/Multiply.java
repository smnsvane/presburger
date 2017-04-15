package graph.term;

import graph.TwoChildrenBranch;
import graph.VariableAssignment;

public class Multiply extends TwoChildrenBranch<Constant, Term> implements Term {

	@Override
	public String getSymbol() { return "*"; }
	@Override
	public int evaluate(VariableAssignment varAss) {
		return getFirstChild().evaluate(varAss) * getSecondChild().evaluate(varAss);
	}
	@Override
	public Multiply multiply(int factor) {
		setFirstChild(getFirstChild().multiply(factor));
		return this;
	}
	@Override
	public Sum toSum() {
		return Sum.sumFromChildren(this);
	}
	@Override
	public Term simplify() {
		Term overtaker = getSecondChild().multiply(getFirstChild().value);
		return overtaker;
	}
	@Override
	public Multiply copy() {
		Multiply copy = new Multiply();
		copy.setFirstChild((Constant) getFirstChild().copy());
		copy.setSecondChild((Term) getSecondChild().copy());
		return copy;
	}
}
