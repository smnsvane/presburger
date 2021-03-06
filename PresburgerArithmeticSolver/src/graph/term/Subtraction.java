package graph.term;

import graph.Term;
import graph.TwoChildrenBranch;
import graph.VariableAssignment;

public class Subtraction extends TwoChildrenBranch<Term, Term> implements Term {

	public Subtraction(Term child1, Term child2) { super(child1, child2); }
	@Override
	public int evaluate(VariableAssignment varAss) {
		return getFirstChild().evaluate(varAss) -
				getSecondChild().evaluate(varAss);
	}
	@Override
	public Subtraction multiply(int factor) {
		Subtraction sub = new Subtraction(getFirstChild().multiply(factor), getSecondChild().multiply(factor));
		return sub;
	}
	@Override
	public Sum toSum() {
		return new Sum(getFirstChild(), getSecondChild().multiply(-1));
	}
	@Override
	public Subtraction copy() { return new Subtraction(getFirstChild().copy(), getSecondChild().copy()); }
}
