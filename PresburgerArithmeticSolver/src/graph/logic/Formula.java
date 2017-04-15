package graph.logic;

import graph.Node;
import graph.VariableAssignment;

public interface Formula extends Node {

	boolean evaluate(VariableAssignment varAss);
	Formula negate();
}
