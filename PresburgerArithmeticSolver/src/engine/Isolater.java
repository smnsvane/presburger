package engine;

import graph.Branch;
import graph.Formula;
import graph.Node;
import graph.formula.comparator.Comparator;

public class Isolater implements Engine {

	private Formula root;
	public Isolater(Formula root) { this.root = root; }

	@Override
	public Formula go() {

		if (root instanceof Branch<?>) {
			@SuppressWarnings("unchecked")
			GraphTransverser transverser = new GraphTransverser((Branch<Node>) root);
			while (transverser.hasNext()) {
				Branch<Node> parent = transverser.next();
				for (Node child : parent)
					if (child instanceof Comparator) {
						Comparator comp = (Comparator) child;
						Comparator neW = comp.isolate();
						parent.replaceChild(child, neW);
					}
				transverser.done();
			}
		}

		return root;
	}
}
