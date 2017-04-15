package graph.term;

import graph.Node;
import graph.VariableAssignment;

public class Constant implements Term {

	private final int value;
	public int getValue() { return value; }
	public Constant(int value) { this.value = value; }
	@Override
	public String toString() { return Integer.toString(value); }
	@Override
	public int evaluate(VariableAssignment varAss) { return value; }
	@Override
	public Node replaceVariables(VariableAssignment assignment) { return this; }
	@Override
	public Sum toSum() {
		Sum sum = new Sum();
		sum.addChild(this);
		return sum;
	}
	@Override
	public Constant multiply(int factor) {
		int value = this.value * factor;
		Constant constant = new Constant(value);
		return constant;
	}
	@Override
	public Constant simplify() { return this; }
}
