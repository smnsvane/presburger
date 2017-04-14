package graph.logic;

import graph.NodeType;
import graph.VariableAssignment;

public interface Logic extends NodeType {

	boolean evaluate(VariableAssignment varAss);
	Logic negate();
}
