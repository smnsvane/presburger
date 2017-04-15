package graph.term;

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
	@Override
	public Term replaceVariables(VariableAssignment assignment) {
		Integer value = assignment.getAssignment(variableSymbol);
		if (value == null) {
			System.out.println("No assignment for "+variableSymbol);
			return this;
		}
		return new Constant(evaluate(assignment));
	}
	@Override
	public Sum toSum() {
		Sum sum = new Sum();
		sum.addChild(this);
		return sum;
	}
	@Override
	public Variable multiply(int factor) {
		int newFactor = this.factor * factor;
		return new Variable(newFactor, variableSymbol);
	}
}
