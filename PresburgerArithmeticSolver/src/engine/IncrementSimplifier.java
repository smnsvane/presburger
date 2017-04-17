package engine;

import graph.Branch;
import graph.Node;

public class IncrementSimplifier extends Engine {

	public IncrementSimplifier(Branch<Node> root) {
		super(root);
		while (hasNext()) {
			Branch<Node> parent = next();
			for (Node child : parent) {
				Node simplifiedChild = child.simplify();
				if (!child.equals(simplifiedChild)) {
					parent.replaceChild(child, simplifiedChild);
					break;
				}
			}
			done();
		}
	}
}
