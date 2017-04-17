package engine;

import graph.Branch;
import graph.Formula;
import graph.Node;

public class IncrementSimplifier implements Engine {

	private Formula root;
	public IncrementSimplifier(Formula root) { this.root = root.copy(); }

	@Override
	public Formula go() {

		Formula simplifiedRoot = root.simplify();
		if (!simplifiedRoot.equals(root))
			return simplifiedRoot;

		if (root instanceof Branch<?>) {
			@SuppressWarnings("unchecked")
			GraphTransverser transverser = new GraphTransverser((Branch<Node>) root);
			while (transverser.hasNext()) {
				Branch<Node> parent = transverser.next();
				for (Node child : parent)
					if (child instanceof Formula) {
						Node simplifiedChild = ((Formula) child).simplify();
						if (!child.equals(simplifiedChild)) {
							parent.replaceChild(child, simplifiedChild);
							break;
						}
					}
			}
			transverser.done();
		}
		return root;
	}
}

