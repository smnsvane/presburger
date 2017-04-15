package graph.term;

import graph.VariableAssignment;

public class Variable implements Term {

	public final int factor;
	public int getFactor() { return factor; }
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
	public int evaluate(VariableAssignment varAss) {
		return varAss.getAssignment(variableSymbol) * factor;
	}
	@Override
	public Term replaceVariables(VariableAssignment assignment) {
		Integer value = assignment.getAssignment(variableSymbol);
		if (value == null) {
			System.out.println("Warning: No assignment for "+variableSymbol);
			return this;
		}
		return new Constant(evaluate(assignment));
	}
	@Override
	public Sum toSum() {
		return Sum.sumFromChildren(this);
	}
	@Override
	public Variable multiply(int factor) {
		int newFactor = this.factor * factor;
		return new Variable(newFactor, variableSymbol);
	}
	@Override
	public Variable simplify() { return this; }
}
