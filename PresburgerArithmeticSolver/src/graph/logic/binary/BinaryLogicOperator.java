package graph.logic.binary;

import java.util.List;

import graph.Branch;
import graph.Node;
import graph.logic.Logic;

public abstract class BinaryLogicOperator extends Branch implements Logic {

	public BinaryLogicOperator(Branch parent, String identifier) {
		super(parent, identifier);
	}

	public Logic getFirstChild() { return (Logic) getChildren().get(0); }
	public Logic getSecondChild() { return (Logic) getChildren().get(1); }

	@Override
	public void setChildren(List<Node> children) {
		if (children.size() != 2)
			throw new RuntimeException(getClass()+" must have exactly two children");
		for (Node n : children)
			if (!(n instanceof Logic))
				throw new RuntimeException(getClass()+" must have "+Logic.class+" children");
		super.setChildren(children);
	}
}
