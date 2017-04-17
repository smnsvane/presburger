package engine;

import graph.Branch;
import graph.Node;
import graph.VariableAssignment;
import graph.term.Constant;
import graph.term.Variable;

public class VariableReplacer extends Engine {

	public VariableReplacer(Branch<Node> root, VariableAssignment assignment) {
		super(root);
		while (hasNext()) {
			Branch<Node> parent = next();
			for (Node child : parent)
				if (child instanceof Variable) {
					Variable v = (Variable) child;
					if (assignment.hasAssignment(v.getVariableSymbol())) {
						int value = v.evaluate(assignment);
						Constant c = new Constant(value);
						parent.replaceChild(child, c);
					}
				}
			done();
		}
	}
}
