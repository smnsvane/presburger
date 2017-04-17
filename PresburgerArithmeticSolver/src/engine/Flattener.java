package engine;

import graph.Branch;
import graph.Formula;
import graph.Node;
import graph.Term;
import graph.term.Sum;

public class Flattener implements Engine {

	private Formula root;
	public Flattener(Formula root) { this.root = root; }

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
						Term neW = sum.flatten();
						parent.replaceChild(child, neW);
					}
				transverser.done();
			}
		}
		return root;
	}
}
