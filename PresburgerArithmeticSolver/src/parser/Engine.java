package parser;

import graph.VariableAssignment;
import graph.logic.Formula;

public class Engine {

	public boolean applyAssignment(Formula root, VariableAssignment varAss) {
		return root.evaluate(varAss);
	}
}
