package graph.math.quantifier;

import java.util.Iterator;

import graph.UnaryBranch;
import graph.VariableAssignment;
import graph.logic.Logic;

public abstract class Quantifier implements UnaryBranch<Logic>, Logic {

	public final String variableSymbol;
	public Quantifier(String variableSymbol) {
		this.variableSymbol = variableSymbol;
	}

	private Logic child;
	@Override
	public Logic getChild() { return child; }
	@Override
	public void setChild(Logic child) { this.child = child; }

	@Override
	public Iterator<Logic> iterator() {
		return new Iterator<Logic>() {
			boolean done = false;
			@Override
			public Logic next() {
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
