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
	
				Constant child1 = new Constant(v.factor);
				Constant child2 = new Constant(assignment.getAssignment(v.variableSymbol));

				Product m = new Product();
				m.setFirstChild(child1);
				m.setSecondChild(child2);
	
				explorer.getParent().replaceChild(v, m);
			}
		return root;
	}
}
