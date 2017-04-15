package graph.formula;

import graph.TwoChildrenBranch;
import graph.VariableAssignment;

public class Implies extends TwoChildrenBranch<Formula, Formula> implements Formula {

	@Override
	public String getSymbol() { return "->"; }
	@Override
	public boolean evaluate(VariableAssignment varAss) {
		return !getFirstChild().evaluate(varAss) ||
				getSecondChild().evaluate(varAss);
	}
	@Override
	public Formula negate() {
		And and = new And();
		and.setFirstChild(getFirstChild());
		and.setSecondChild(getSecondChild().negate());
		return and;
	}
	@Override
	public Formula simplify() {
		Or or = new Or();
		Not not = new Not();
		not.setChild(getFirstChild());
		or.setFirstChild(not);
		or.setSecondChild(getSecondChild());
		return (Formula) or.simplify();
	}
}
