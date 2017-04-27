package graph.formula.comparator;

import graph.Formula;
import graph.Term;
import graph.TwoChildrenBranch;
import graph.formula.False;
import graph.formula.True;

public abstract class Comparator extends TwoChildrenBranch<Term, Term> implements Formula {

	public Comparator(Term child1, Term child2) { super(child1, child2); }
	public abstract Formula toCooper();
	@Override
	public Formula reduce() {
		try {
			if (evaluate(null))
				return new True();
			return new False();
		} catch (NullPointerException e) {
			return this;
		}
	}
}
