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
		return new Sum(getFirstChild().simplify(), getSecondChild().multiply(-1).simplify());
	}
	@Override
	public Addition simplify() {
		Addition add = new Addition(getFirstChild(), getSecondChild().multiply(-1));
		return add;
	}
	@Override
	public Subtraction copy() { return new Subtraction(getFirstChild().copy(), getSecondChild().copy()); }
}
