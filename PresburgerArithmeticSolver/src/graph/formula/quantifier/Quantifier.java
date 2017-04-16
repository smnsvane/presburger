package graph.formula.quantifier;

import graph.SingleChildBranch;
import graph.VariableAssignment;
import graph.formula.Formula;

public abstract class Quantifier extends SingleChildBranch<Formula> implements Formula {

	private String variableSymbol;
	public String getVariableSymbol() { return variableSymbol; }
	public Quantifier(String variableSymbol, Formula formula) {
		super(formula);
		this.variableSymbol = variableSymbol;
	}
	@Override
	public boolean evaluate(VariableAssignment varAss) {
		throw new RuntimeException("not implemented");
	}
	@Override
	public String toString() { return getSymbol()+variableSymbol+"."+getChild(); }
}
