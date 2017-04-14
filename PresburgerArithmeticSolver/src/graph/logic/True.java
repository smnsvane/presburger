package graph.logic;

import graph.AbstractNode;
import graph.Branch;
import graph.Leaf;
import graph.VariableAssignment;

public class True extends AbstractNode implements Leaf, Logic {

	public True(Branch parent, String identifier) { super(parent, "true"); }

	@Override
	public boolean evaluate(VariableAssignment varAss) { return true; }
}
