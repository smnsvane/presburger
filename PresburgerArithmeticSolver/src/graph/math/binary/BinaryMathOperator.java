package graph.math.binary;

import java.util.List;

import graph.Branch;
import graph.Node;
import graph.math.Math;

public abstract class BinaryMathOperator extends Branch implements Math {

	public BinaryMathOperator(Branch parent, String identifier) {
		super(parent, identifier);
	}

	public Math getFirstChild() { return (Math) getChildren().get(0); }
	public Math getSecondChild() { return (Math) getChildren().get(1); }

	@Override
	public void setChildren(List<Node> children) {
		if (children.size() != 2)
			throw new RuntimeException(getClass()+" must have exactly two children");
		for (Node n : children)
			if (!(n instanceof Math))
				throw new RuntimeException(getClass()+" must have "+Math.class+" children");
		super.setChildren(children);
	}
}
