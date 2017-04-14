package graph.math.quantifier;

import java.util.Iterator;

import graph.Branch;
import graph.Node;
import graph.UnaryBranch;
import graph.AbstractNode;
import graph.VariableAssignment;
import graph.logic.Logic;

public abstract class Quantifier extends AbstractNode implements UnaryBranch<Logic>, Logic {

	public final String variableSymbol;
	public Quantifier(Branch parent, String identifier, String variableSymbol) {
		super(parent, identifier);
		this.variableSymbol = variableSymbol;
	}

	private Logic child;
	@Override
	public Logic getChild() { return child; }
	@Override
	public void setChild(Logic child) { this.child = child; }

	@Override
	public Iterator<Node> iterator() {
		return new Iterator<Node>() {
			boolean done = false;
			@Override
			public Node next() {
				done = true;
				return child;
			}
			@Override
			public boolean hasNext() { return done; }
		};
	}

	@Override
	public boolean evaluate(VariableAssignment varAss) {
		throw new RuntimeException("not implemented");
	}
}
