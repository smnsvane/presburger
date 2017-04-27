package engine;

import graph.Branch;
import graph.Formula;
import graph.Node;
import graph.formula.False;
import graph.formula.True;
import graph.formula.quantifier.Quantifier;

public class CheckSubtreeForQuantifiers implements Engine {

	private Formula root;
	public CheckSubtreeForQuantifiers(Formula root) { this.root = root.copy(); }

	@Override
	public Formula go() {

		if (root instanceof Quantifier)
			return new True();

		if (root instanceof Branch<?>) {
			@SuppressWarnings("unchecked")
			GraphTransverser transverser = new GraphTransverser((Branch<Node>) root);
			while (transverser.hasNext()) {
				Branch<Node> parent = transverser.next();
				for (Node child : parent)
					if (child instanceof Quantifier)
						return new True();
				transverser.done();
			}
		}
		return new False();
	}
}
