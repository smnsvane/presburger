package graph.formula.quantifier;

import graph.SingleChildBranch;
import graph.VariableAssignment;
import graph.formula.Formula;

public abstract class Quantifier extends SingleChildBranch<Formula> implements Formula {

	public final String variableSymbol;
	public Quantifier(String variableSymbol) { this.variableSymbol = variableSymbol; }
	@Override
	public boolean evaluate(VariableAssignment varAss) {
		throw new RuntimeException("not implemented");
	}
	@Override
	public Quantifier simplify() {
		System.out.println("simplify quantifier is not implemented, just simplifying its child");
		setChild((Formula) getChild().simplify());
		return this;
	}
	@Override
	public String toString() { return getSymbol()+variableSymbol+"."+getChild(); }
}
