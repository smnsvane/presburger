package engine;

import graph.Branch;
import graph.Node;
import graph.formula.comparator.Comparator;

public class Isolater implements Engine {

	private Branch<Node> root;
	public Isolater(Branch<Node> root) { this.root = root; }

	@Override
	public Branch<Node> go() {

		GraphTransverser transverser = new GraphTransverser(root);
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

		return root;
	}
}
