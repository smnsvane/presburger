package engine;

import graph.Branch;
import graph.Formula;
import graph.Node;

public class Reducer implements Engine {

	private Formula root;
	public Reducer(Formula root) { this.root = root.copy(); }

	@Override
	public Formula go() {

		root = root.reduce();

		if (root instanceof Branch<?>) {
			@SuppressWarnings("unchecked")
			GraphTransverser transverser = new GraphTransverser((Branch<Node>) root);
			while (transverser.hasNext()) {
				Branch<Node> parent = transverser.next();
				for (Node child : parent)
					if (child instanceof Formula) {
						Formula neW = ((Formula) child).reduce();
						parent.replaceChild(child, neW);
					}
				transverser.done();
			}
		}

		return root;
	}
}
