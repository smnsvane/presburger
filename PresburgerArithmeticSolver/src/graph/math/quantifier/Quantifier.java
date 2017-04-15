package graph.math.quantifier;

import graph.NodeBranch;
import graph.VariableAssignment;
import graph.logic.Formula;

public abstract class Quantifier implements NodeBranch, Formula {

	public final String variableSymbol;
	public Quantifier(String variableSymbol) {
		this.variableSymbol = variableSymbol;
	}

	private Formula child;
	public Formula getChild() { return child; }
	public void setChild(Formula child) { this.child = child; }

	@Override
	public boolean evaluate(VariableAssignment varAss) {
		throw new RuntimeException("not implemented");
	}
}
