package engine;

import graph.GraphIterator;
import graph.Node;
import graph.formula.Formula;
import graph.term.Term;

public class Simplifier implements Engine {

	private Formula root;
	public Simplifier(Formula root) { this.root = root; }
	
	@Override
	public Formula go() {
		GraphIterator explorer = new GraphIterator(root);
		for (Node n : explorer)
			if (explorer.getParent() == null)
				root = ((Formula) n).simplify();
			else if (n instanceof Formula)
				explorer.getParent().replaceChild(n, ((Formula) n).simplify());
			else if (n instanceof Term)
				explorer.getParent().replaceChild(n, ((Term) n).simplify());
			else
				throw new RuntimeException("unknown node type");
		return root;
	}
}
