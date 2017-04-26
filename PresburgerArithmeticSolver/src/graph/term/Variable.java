package graph.term;

import graph.Leaf;
import graph.Term;
import graph.VariableAssignment;

public class Variable extends Leaf implements Term {

	private int factor;
	public int getFactor() { return factor; }

	private String variableSymbol;
	public String getVariableSymbol() { return variableSymbol; }

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
	public Sum toSum() { return new Sum(this); }
	@Override
	public Term copy() { return new Variable(getFactor(), getVariableSymbol()); }
	@Override
	public Term flatten() { return this; }
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Variable) {
			Variable other = (Variable) obj;
			return factor == other.factor &&
					getVariableSymbol().equals(other.getVariableSymbol());
		}
		return false;
	}
	@Override
	public void validate() {
		if (factor == 0)
			throw new RuntimeException(this+" has a factor of 0");
		if (variableSymbol == null || variableSymbol.isEmpty())
			throw new RuntimeException(this+" has no variable");
	}
}
