package graph.math;

import java.util.List;

import graph.Branch;
import graph.Node;
import graph.VariableAssignment;

public class Minus extends Branch implements Math {

	public Minus(Branch parent) { super(parent, "-"); }

	public Math getChild() { return (Math) getChildren().get(0); }

	@Override
	public void setChildren(List<Node> children) {
		if (children.size() != 1)
			throw new RuntimeException(getClass()+" must have exactly one child");
		if (!(children.get(0) instanceof Math))
			throw new RuntimeException(getClass()+" must have a "+Math.class+" child");
		super.setChildren(children);
	}

	@Override
	public int evaluate(VariableAssignment varAss) {
		return - getChild().evaluate(varAss);
	}
}
