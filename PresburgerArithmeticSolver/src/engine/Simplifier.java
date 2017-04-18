package engine;

import graph.Branch;
import graph.Formula;
import graph.Node;

public class Simplifier implements Engine {

	private Formula root;
	public Simplifier(Formula root) { this.root = root.copy(); }

	public Formula fullSimplify(Formula n) {
		Formula org;
		do {
			org = n;
			n = org.simplify();
		} while (!org.equals(n));
		return n;
	}

	@Override
	public Formula go() {

		root = fullSimplify(root);

		if (root instanceof Branch<?>) {
			@SuppressWarnings("unchecked")
			GraphTransverser transverser = new GraphTransverser((Branch<Node>) root);
			while (transverser.hasNext()) {
				Branch<Node> parent = transverser.next();
				for (Node child : parent)
					if (child instanceof Formula) {
						Formula neW = fullSimplify((Formula) child);
						parent.replaceChild(child, neW);
					}
				transverser.done();
			}
		}

		return root;
	}
}
