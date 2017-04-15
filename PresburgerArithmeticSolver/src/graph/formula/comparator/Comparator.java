package graph.formula.comparator;

import graph.TwoChildrenBranch;
import graph.formula.Formula;
import graph.term.Sum;
import graph.term.Term;

public abstract class Comparator extends TwoChildrenBranch<Term, Term> implements Formula {

	@Override
	public Formula simplify() {
		Sum sum1 = getFirstChild().toSum();
		sum1.flatten();
		sum1.simplify();
		
		Sum sum2 = getSecondChild().toSum();
		sum2.flatten();
		sum2.simplify();

		Sum emptySum = new Sum();
		Sum combinedSum = Sum.isolationSum(sum2, sum1);
		setFirstChild(emptySum);
		setSecondChild(combinedSum.simplify());
		return this;
	}
}
