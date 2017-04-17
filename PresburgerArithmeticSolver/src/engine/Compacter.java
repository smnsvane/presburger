package engine;

import graph.Branch;
import graph.Formula;
import graph.Node;
import graph.term.Sum;

public class Compacter implements Engine {

	private Formula root;
	public Compacter(Formula root) { this.root = root.copy(); }

	@Override
	public Formula go() {

		if (root instanceof Branch<?>) {
			@SuppressWarnings("unchecked")
			GraphTransverser transverser = new GraphTransverser((Branch<Node>) root);
			while (transverser.hasNext()) {
				Branch<Node> parent = transverser.next();
				for (Node child : parent)
					if (child instanceof Sum) {
						Sum sum = (Sum) child;
						Sum neW = sum.compact();
						parent.replaceChild(child, neW);
					}
				transverser.done();
			}
		}
		return root;
	}
}
