package engine.cooper;

import engine.Engine;
import engine.GraphTransverser;
import graph.Branch;
import graph.Formula;
import graph.Node;
import graph.formula.comparator.LessThan;
import graph.formula.quantifier.Exists;

public class VariableIsolater implements Engine {

	private Formula root;
	private String variableSymbol;
	public VariableIsolater(Formula root) { this.root = root.copy(); }

	@Override
	public Formula go() {

		if (root instanceof Exists) {
			Exists exists = (Exists) root;
			if (exists.hasNoQuantifiersInSubtree())
				variableSymbol = exists.getVariableSymbol();
		}
		if (root instanceof Branch<?>) {
			@SuppressWarnings("unchecked")
			GraphTransverser transverser = new GraphTransverser((Branch<Node>) root);
			while (transverser.hasNext()) {
				Branch<Node> parent = transverser.next();
				for (Node child : parent)
					if (child instanceof Exists) {
						Exists exists = (Exists) child;
						if (exists.hasNoQuantifiersInSubtree())
							variableSymbol = exists.getVariableSymbol();
					} else if (child instanceof LessThan && variableSymbol != null) {
						LessThan less = (LessThan) child;
						LessThan neW = less.isolate(variableSymbol);
						parent.replaceChild(less, neW);
					}
				transverser.done();
			}
		}

		return root;
	}
}
