package engine;

import graph.Branch;
import graph.Formula;
import graph.Node;
import graph.formula.Not;
import graph.formula.quantifier.Exists;

public class DeMorgan implements Engine {

	private Formula root;
	public DeMorgan(Formula root) { this.root = root.copy(); }

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
						Formula notChild = n.getChild();
						if (!(notChild instanceof Exists)) {
							Formula neW = notChild.negate();
							parent.replaceChild(n, neW);
						}
					}
				transverser.done();
			}
		}
		return root;
	}
}
