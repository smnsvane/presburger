package graph.term;

import graph.TwoChildrenBranch;
import graph.VariableAssignment;

public class Multiply extends TwoChildrenBranch<Constant, Term> implements Term {

	public static final String symbol = "*";

	@Override
	public String toString() { return getFirstChild()+symbol+getSecondChild(); }

	@Override
	public int evaluate(VariableAssignment varAss) {
		return getFirstChild().evaluate(varAss) * getSecondChild().evaluate(varAss);
	}
}
