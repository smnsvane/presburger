package engine;

import graph.Branch;
import graph.Node;

public class IncrementSimplifier implements Engine {

	private Node root;
	public IncrementSimplifier(Branch<Node> root) { this.root = root; }

	@Override
	public Node go() {

		root = root.copy();
		Node simplifiedRoot = root.simplify();
		if (!simplifiedRoot.equals(root))
			return simplifiedRoot;

		if (root instanceof Branch<?>) {
			@SuppressWarnings("unchecked")
			GraphTransverser transverser = new GraphTransverser((Branch<Node>) root);
			while (transverser.hasNext()) {
				Branch<Node> parent = transverser.next();
				for (Node child : parent) {
					Node simplifiedChild = child.simplify();
					if (!child.equals(simplifiedChild)) {
						parent.replaceChild(child, simplifiedChild);
						break;
					}
				}
				transverser.done();
			}
		}

		return root;
	}
}

