package engine;

import graph.Branch;
import graph.Node;

public class Simplifier implements Engine {

	private Node root;
	public Simplifier(Node root) { this.root = root; }

	public Node fullSimplify(Node n) {
		Node org;
		do {
			org = n;
			n = org.simplify();
		} while (!org.equals(n));
		return n;
	}

	@Override
	public Node go() {

		root = fullSimplify(root.copy());

		if (root instanceof Branch<?>) {
			@SuppressWarnings("unchecked")
			GraphTransverser transverser = new GraphTransverser((Branch<Node>) root);
			while (transverser.hasNext()) {
				Branch<Node> parent = transverser.next();
				for (Node child : parent)
					parent.replaceChild(child, fullSimplify(child));
				transverser.done();
			}
		}

		return root;
	}
}
