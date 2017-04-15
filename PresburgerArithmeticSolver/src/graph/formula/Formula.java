package graph.formula;

import graph.Node;
import graph.VariableAssignment;

public interface Formula extends Node {

	boolean evaluate(VariableAssignment varAss);
	Formula negate();
}
