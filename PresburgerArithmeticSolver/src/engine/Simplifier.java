package engine;

import graph.Branch;
import graph.Node;

public class Simplifier extends Engine {

	public Simplifier(Branch<Node> root) {
		super(root);
		while (hasNext()) {
			Branch<Node> parent = next();
			for (Node child : parent) {
				Node simplifiedChild = child.simplify();
				parent.replaceChild(child, simplifiedChild);
			}
			done();
		}
	}
}
