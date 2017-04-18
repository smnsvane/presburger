package engine.cooper;

import engine.Engine;
import engine.GraphTransverser;
import graph.Branch;
import graph.Formula;
import graph.Node;
import graph.formula.Not;
import graph.formula.quantifier.Forall;

public class ToExister implements Engine {

	private Formula root;
	public ToExister(Formula root) { this.root = root.copy(); }

	@Override
	public Formula go() {

		if (root instanceof Forall) {
			Forall forall = (Forall) root;
			root = forall.toExists();
		}
		if (root instanceof Branch<?>) {
			@SuppressWarnings("unchecked")
			GraphTransverser transverser = new GraphTransverser((Branch<Node>) root);
			while (transverser.hasNext()) {
				Branch<Node> parent = transverser.next();
				for (Node child : parent)
					if (child instanceof Forall) {
						Forall forall = (Forall) child;
						Not not = forall.toExists();
						parent.replaceChild(forall, not);
					}
				transverser.done();
			}
		}

		return root;
	}
}
