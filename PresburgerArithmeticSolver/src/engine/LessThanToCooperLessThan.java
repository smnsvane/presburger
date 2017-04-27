package engine;

import graph.Branch;
import graph.Formula;
import graph.Node;
import graph.Term;
import graph.formula.comparator.Comparator;
import graph.term.Product;

public class LessThanToCooperLessThan implements Engine {

	private Formula root;
	public LessThanToCooperLessThan(Formula root) { this.root = root.copy(); }

	@Override
	public Formula go() {

		if (root instanceof Branch<?>) {
			@SuppressWarnings("unchecked")
			GraphTransverser transverser = new GraphTransverser((Branch<Node>) root);
			while (transverser.hasNext()) {
				Branch<Node> parent = transverser.next();
				for (Node child : parent)
					if (child instanceof Comparator) {
						Product p = (Product) child;
						Term neW = p.simplify();
						parent.replaceChild(p, neW);
					}
				transverser.done();
			}
		}
		return root;
	}
}
