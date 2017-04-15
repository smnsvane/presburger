package graph.term;

import graph.VariableAssignment;

public class Variable implements Term {

	public final int factor;
	public final String variableSymbol;
	public String getSymbol() { return variableSymbol; }

	public Variable(int factor, String variableSymbol) {
		this.factor = factor;
		this.variableSymbol = variableSymbol;
	}
	@Override
	public String toString() {
		return (factor==1?"":(factor==-1?"-":factor))+variableSymbol;
	}
	@Override
	public int evaluate(VariableAssignment assignment) {
		return assignment.getAssignment(variableSymbol) * factor;
	}
	@Override
	public Variable multiply(int factor) {
		int newFactor = this.factor * factor;
		return new Variable(newFactor, variableSymbol);
	}
	@Override
	public Variable simplify() { return this; }
	@Override
	public Sum toSum() { return Sum.sumFromChildren(this); }
}
