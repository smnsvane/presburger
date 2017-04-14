package graph.logic;

import graph.Branch;
import graph.Leaf;
import graph.VariableAssignment;

public class False extends Leaf implements Logic {

	public False(Branch parent, String identifier) { super(parent, "false"); }

	@Override
	public boolean evaluate(VariableAssignment varAss) { return false; }
}
