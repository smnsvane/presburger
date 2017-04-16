package engine;

import graph.Node;
import graph.formula.Formula;
import graph.term.Term;

public class IncrementSimplifier implements Engine {

	private Formula root;
	public IncrementSimplifier(Formula root) { this.root = root; }
	
	@Override
	public Formula go() {
		GraphIterator explorer = new GraphIterator(root);
		for (Node n : explorer) {
			Node simplified = null;
			if (explorer.getParent() == null) {
				simplified = n.simplify();
				root = (Formula) simplified;
			} else if (n instanceof Formula) {
				simplified = n.simplify();
				explorer.getParent().replaceChild(n, (Formula) simplified);
			} else if (n instanceof Term) {
				simplified = n.simplify();
				explorer.getParent().replaceChild(n, (Term) simplified);
			} else
				throw new RuntimeException("unknown node type");
			if (!simplified.equals(n))
				break;
		}
		return root;
	}
}
