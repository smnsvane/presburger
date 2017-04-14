package graph.logic;

import java.util.List;

import graph.Branch;
import graph.Node;
import graph.VariableAssignment;

public class Not extends Branch implements Logic {

	public Not(Branch parent) { super(parent, "~"); }

	public Logic getChild() { return (Logic) getChildren().get(0); }

	@Override
	public void setChildren(List<Node> children) {
		if (children.size() != 1)
			throw new RuntimeException(getClass()+" must have exactly one child");
		if (!(children.get(0) instanceof Logic))
			throw new RuntimeException(getClass()+" must have a "+Logic.class+" child");
		super.setChildren(children);
	}

	@Override
	public boolean evaluate(VariableAssignment varAss) {
		return !getChild().evaluate(varAss);
	}
}
