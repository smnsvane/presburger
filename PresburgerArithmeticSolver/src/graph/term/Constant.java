package graph.term;

import graph.Node;
import graph.VariableAssignment;

public class Constant implements Term {

	private final int number;
	public Constant(int number) { this.number = number; }
	@Override
	public String toString() { return Integer.toString(number); }
	@Override
	public int evaluate(VariableAssignment varAss) { return number; }
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
		int value = number * factor;
		Constant constant = new Constant(value);
		return constant;
	}
}
