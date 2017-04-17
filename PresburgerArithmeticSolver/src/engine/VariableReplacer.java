package engine;

import graph.Branch;
import graph.Formula;
import graph.Node;
import graph.VariableAssignment;
import graph.term.Constant;
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

		if (root instanceof Branch<?>) {
			@SuppressWarnings("unchecked")
			GraphTransverser transverser = new GraphTransverser((Branch<Node>) root);
			while (transverser.hasNext()) {
				Branch<Node> parent = transverser.next();
				for (Node child : parent)
					if (child instanceof Variable) {
						Variable v = (Variable) child;
						if (assignment.hasAssignment(v.getVariableSymbol())) {
							int value = v.evaluate(assignment);
							Constant c = new Constant(value);
							parent.replaceChild(child, c);
						}
					}
				transverser.done();
			}
		}
		return root;
	}
}
