package graph.math.quantifier;

import graph.SingleChildBranch;
import graph.VariableAssignment;
import graph.logic.Formula;

public abstract class Quantifier extends SingleChildBranch<Formula> implements Formula {

	public final String variableSymbol;
	public Quantifier(String variableSymbol) { this.variableSymbol = variableSymbol; }

	@Override
	public boolean evaluate(VariableAssignment varAss) {
		throw new RuntimeException("not implemented");
	}
}
