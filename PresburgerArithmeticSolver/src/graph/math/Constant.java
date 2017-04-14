package graph.math;

import graph.VariableAssignment;

public class Constant implements Math {

	private final int number;
	public Constant(int number) { this.number = number; }

	@Override
	public String toString() { return Integer.toString(number); }
	@Override
	public int evaluate(VariableAssignment varAss) { return number; }
}
