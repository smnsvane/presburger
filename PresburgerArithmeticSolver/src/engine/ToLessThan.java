package engine;

import graph.Branch;
import graph.Formula;
import graph.Node;
import graph.formula.comparator.Comparator;

public class ToLessThan implements Engine {

	private Branch<Node> root;
	public ToLessThan(Branch<Node> root) { this.root = root; }

	@Override
	public Branch<Node> go() {

		GraphTransverser transverser = new GraphTransverser(root);
		while (transverser.hasNext()) {
			Branch<Node> parent = transverser.next();
			for (Node child : parent)
				if (child instanceof Comparator) {
					Comparator comp = (Comparator) child;
					Formula neW = comp.toLessThan();
					parent.replaceChild(child, neW);
				}
			transverser.done();
		}

		return root;
	}
}
