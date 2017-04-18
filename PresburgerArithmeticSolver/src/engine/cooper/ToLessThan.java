package engine.cooper;

import engine.Engine;
import engine.GraphTransverser;
import graph.Branch;
import graph.Formula;
import graph.Node;
import graph.formula.comparator.Comparator;

public class ToLessThan implements Engine {

	private Formula root;
	public ToLessThan(Formula root) { this.root = root.copy(); }

	@Override
	public Formula go() {

		if (root instanceof Branch<?>) {
			@SuppressWarnings("unchecked")
			GraphTransverser transverser = new GraphTransverser((Branch<Node>) root);
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
		}
		return root;
	}
}
