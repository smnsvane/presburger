package graph.term;

import graph.TwoChildrenBranch;
import graph.VariableAssignment;

public class Product extends TwoChildrenBranch<Constant, Term> implements Term {

	@Override
	public String getSymbol() { return "*"; }
	public Product(Constant constant, Term term) { super(constant, term); }
	@Override
	public int evaluate(VariableAssignment varAss) {
		return getFirstChild().evaluate(varAss) * getSecondChild().evaluate(varAss);
	}
	@Override
	public Product multiply(int factor) {
		Product mul = new Product(getFirstChild().multiply(factor), getSecondChild());
		return mul;
	}
	@Override
	public Sum toSum() {
		return new Sum(this);
	}
	@Override
	public Term simplify() {
		Term overtaker = getSecondChild().multiply(getFirstChild().getValue());
		return overtaker;
	}
}
