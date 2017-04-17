package engine;

import graph.Branch;
import graph.Formula;
import graph.Node;
import graph.Term;

public class Flattener implements Engine {

	private Formula root;
	public Flattener(Formula root) { this.root = root.copy(); }

	@Override
	public Formula go() {

		if (root instanceof Branch<?>) {
			@SuppressWarnings("unchecked")
			GraphTransverser transverser = new GraphTransverser((Branch<Node>) root);
			while (transverser.hasNext()) {
				Branch<Node> parent = transverser.next();
				for (Node child : parent)
					if (child instanceof Term && child instanceof Branch<?>) {
						Term t = (Term) child;
						Term neW = t.flatten();
						parent.replaceChild(child, neW);
					}
				transverser.done();
			}
		}
		return root;
	}
}
