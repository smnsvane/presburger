package graph.logic;

import graph.NodeType;
import graph.VariableAssignment;

public interface Formula extends NodeType {

	boolean evaluate(VariableAssignment varAss);
	Formula negate();
}
