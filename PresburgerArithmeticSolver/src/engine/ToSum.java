package engine;

import graph.Branch;
import graph.Formula;
import graph.Node;
import graph.formula.Not;

public class ToSum implements Engine {

	private Formula root;
	public ToSum(Formula root) { this.root = root.copy(); }

	@Override
	public Formula go() {

		while (root instanceof Not)
			root = ((Not) root).getChild().negate();

		if (root instanceof Branch<?>) {
			@SuppressWarnings("unchecked")
			GraphTransverser transverser = new GraphTransverser((Branch<Node>) root);
			while (transverser.hasNext()) {
				Branch<Node> parent = transverser.next();
				for (Node child : parent)
					if (child instanceof Not) {
						Not n = (Not) child;
						Formula neW = n.getChild().negate();
						parent.replaceChild(n, neW);
					}
				transverser.done();
			}
		}
		return root;
	}
}
