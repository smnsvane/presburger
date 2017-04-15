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
}
