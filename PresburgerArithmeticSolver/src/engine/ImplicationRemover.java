package engine;

import graph.Branch;
import graph.Formula;
import graph.Node;
import graph.formula.Implies;
import graph.formula.Or;

public class ImplicationRemover implements Engine {

	private Formula root;
	public ImplicationRemover(Formula root) { this.root = root.copy(); }

	@Override
	public Formula go() {

		if (root instanceof Branch<?>) {
			@SuppressWarnings("unchecked")
			GraphTransverser transverser = new GraphTransverser((Branch<Node>) root);
			while (transverser.hasNext()) {
				Branch<Node> parent = transverser.next();
				for (Node child : parent)
					if (child instanceof Implies) {
						Implies i = (Implies) child;
						Or neW = i.reduce();
						parent.replaceChild(i, neW);
					}
				transverser.done();
			}
		}
		return root;
	}
}
