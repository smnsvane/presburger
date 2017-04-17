package engine;

import graph.Node;
import graph.VariableAssignment;
import graph.formula.Formula;
import graph.term.Constant;
import graph.term.Product;
import graph.term.Variable;

public class VariableReplacer implements Engine {

	private Formula root;
	private VariableAssignment assignment;
	public VariableReplacer(Formula root, VariableAssignment assignment) {
		this.root = root;
		this.assignment = assignment;
	}

	@Override
	public Formula go() {
		GraphIterator explorer = new GraphIterator(root);
		for (Node n : explorer)
			if (n instanceof Variable) {
				Variable v = (Variable) n;
				if (assignment.getAssignment(v.getVariableSymbol()) != null) {

					Constant child1 = new Constant(v.getFactor());
					Constant child2 = new Constant(assignment.getAssignment(v.getVariableSymbol()));

					Product m = new Product(child1, child2);

					explorer.getParent().replaceChild(v, m);
				}
			}
		return root;
	}
}
