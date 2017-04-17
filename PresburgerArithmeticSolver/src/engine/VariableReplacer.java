package engine;

import graph.Branch;
import graph.Node;
import graph.VariableAssignment;
import graph.term.Constant;
import graph.term.Variable;

public class VariableReplacer implements Engine {

	private Branch<Node> root;
	private VariableAssignment assignment;
	public VariableReplacer(Branch<Node> root, VariableAssignment assignment) {
		this.root = root;
		this.assignment = assignment;
	}

	@Override
	public Branch<Node> go() {

		GraphTransverser transverser = new GraphTransverser(root);
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

		return root;
	}
}
