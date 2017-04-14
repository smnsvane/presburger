package graph.math.quantifier;

import java.util.List;

import graph.Branch;
import graph.Node;
import graph.VariableAssignment;
import graph.logic.Logic;

public abstract class Quantifier extends Branch implements Logic {

	public final String variableSymbol;
	public Quantifier(Branch parent, String identifier, String variableSymbol) {
		super(parent, identifier);
		this.variableSymbol = variableSymbol;
	}

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
		throw new RuntimeException("not implemented");
	}
}
