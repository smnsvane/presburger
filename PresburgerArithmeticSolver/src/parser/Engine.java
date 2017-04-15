package parser;

import graph.VariableAssignment;
import graph.formula.Formula;

public class Engine {

	public boolean applyAssignment(Formula root, VariableAssignment varAss) {
		return root.evaluate(varAss);
	}
}
