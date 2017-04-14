package graph.logic;

import graph.Branch;
import graph.Leaf;
import graph.VariableAssignment;

public class True extends Leaf implements Logic {

	public True(Branch parent, String identifier) { super(parent, "true"); }

	@Override
	public boolean evaluate(VariableAssignment varAss) { return true; }
}
