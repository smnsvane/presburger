package graph.formula.comparator;

import graph.TwoChildrenBranch;
import graph.formula.False;
import graph.formula.Formula;
import graph.formula.True;
import graph.term.Term;

public abstract class Comparator extends TwoChildrenBranch<Term, Term> implements Formula {

	public Comparator(Term child1, Term child2) { super(child1, child2); }
	public abstract Formula isolate();
	public abstract Formula toLessThan();
	@Override
	public Formula simplify() {
		try {
			if (evaluate(null))
				return new True();
			return new False();
		} catch (NullPointerException e) {
			return this;
		}
	}
}
