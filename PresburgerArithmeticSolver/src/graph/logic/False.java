package graph.logic;

import graph.AbstractNode;
import graph.Branch;
import graph.Leaf;
import graph.VariableAssignment;

public class False extends AbstractNode implements Leaf, Logic {

	public static final String symbol = "false";
	public False(Branch parent, String identifier) { super(parent, symbol); }

	@Override
	public boolean evaluate(VariableAssignment varAss) { return false; }
}
