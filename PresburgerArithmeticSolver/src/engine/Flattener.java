package engine;

import graph.Branch;
import graph.Node;
import graph.term.Sum;

public class Flattener implements Engine {

	private Branch<Node> root;
	public Flattener(Branch<Node> root) { this.root = root; }

	@Override
	public Branch<Node> go() {

		GraphTransverser transverser = new GraphTransverser(root);
		while (transverser.hasNext()) {
			Branch<Node> parent = transverser.next();
			for (Node child : parent)
				if (child instanceof Sum) {
					Sum sum = (Sum) child;
					Sum neW = sum.flatten();
					parent.replaceChild(child, neW);
				}
			transverser.done();
		}

		return root;
	}
}
