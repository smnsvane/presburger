package graph.math;

import graph.VariableAssignment;

public class Variable implements Term {

	public final int factor;
	public final String variableSymbol;

	public Variable(int factor, String variableSymbol) {
		this.factor = factor;
		this.variableSymbol = variableSymbol;
	}
	@Override
	public String toString() {
		return (factor==1?"":factor)+variableSymbol;
	}
	@Override
	public int evaluate(VariableAssignment varAss) {
		return varAss.getAssignment(variableSymbol) * factor;
	}
}
