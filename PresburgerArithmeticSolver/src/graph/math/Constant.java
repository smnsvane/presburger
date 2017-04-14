package graph.math;

import graph.Branch;
import graph.Leaf;
import graph.VariableAssignment;

public class Constant extends Leaf implements Math{

	private final int number;
	public Constant(Branch parent, int number) {
		super(parent, Integer.toString(number));
		this.number = number;
	}

	@Override
	public int evaluate(VariableAssignment varAss) { return number; }
}
