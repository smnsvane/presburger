package graph.math.binary;

import graph.TwoChildrenBranch;
import graph.VariableAssignment;
import graph.math.Term;

public class Subtraction extends TwoChildrenBranch<Term, Term> implements Term {

	public static final String symbol = "-";

	@Override
	public String toString() {
		return getFirstChild()+symbol+getSecondChild();
	}

	@Override
	public int evaluate(VariableAssignment varAss) {
		return getFirstChild().evaluate(varAss) -
				getSecondChild().evaluate(varAss);
	}
}
