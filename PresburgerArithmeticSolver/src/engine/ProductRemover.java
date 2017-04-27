package engine;

import graph.Branch;
import graph.Formula;
import graph.Node;
import graph.Term;
import graph.term.Product;

public class ProductRemover implements Engine {

	private Formula root;
	public ProductRemover(Formula root) { this.root = root.copy(); }

	@Override
	public Formula go() {

		if (root instanceof Branch<?>) {
			@SuppressWarnings("unchecked")
			GraphTransverser transverser = new GraphTransverser((Branch<Node>) root);
			while (transverser.hasNext()) {
				Branch<Node> parent = transverser.next();
				for (Node child : parent)
					if (child instanceof Product) {
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
