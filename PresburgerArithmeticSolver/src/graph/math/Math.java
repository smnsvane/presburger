package graph.math;

import graph.NodeType;
import graph.VariableAssignment;

public interface Math extends NodeType {

	int evaluate(VariableAssignment varAss);
}
