package graph.formula.comparator;

import graph.TwoChildrenBranch;
import graph.formula.Formula;
import graph.term.Sum;
import graph.term.Term;

public abstract class Comparator extends TwoChildrenBranch<Term, Term> implements Formula {

	@Override
	public Formula simplify() {
		Sum child1 = getFirstChild().toSum();
		child1.flatten();
		child1.simplify();
		setFirstChild(child1);
		Sum child2 = getSecondChild().toSum();
		child2.flatten();
		child2.simplify();
		setSecondChild(child2);
		return this;
	}
}
