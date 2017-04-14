package graph.math;

import graph.Branch;
import graph.Leaf;
import graph.VariableAssignment;

public class Variable extends Leaf implements Math {

	public final int factor;
	public final char variableSymbol;

	public Variable(Branch parent, char variableSymbol) {
		this(parent, 1, variableSymbol);
	}
	public Variable(Branch parent, int factor, char variableSymbol) {
		super(parent, Character.toString(variableSymbol));
		this.factor = factor;
		this.variableSymbol = variableSymbol;
	}

	@Override
	public int evaluate(VariableAssignment varAss) {
		return varAss.getAssignment(variableSymbol) * factor;
	}
}
