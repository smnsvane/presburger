package graph.term;

import graph.Term;
import graph.TwoChildrenBranch;
import graph.VariableAssignment;

public class Product extends TwoChildrenBranch<Constant, Term> implements Term {

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
	public Term simplify() {
		Term overtaker = getSecondChild().multiply(getFirstChild().getValue());
		return overtaker;
	}
	@Override
	public Product copy() { return new Product(getFirstChild().copy(), getSecondChild().copy()); }
}
