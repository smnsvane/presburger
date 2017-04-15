package graph.math;

import graph.NodeType;
import graph.VariableAssignment;

public interface Term extends NodeType {

	int evaluate(VariableAssignment varAss);
}
