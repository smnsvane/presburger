package graph.math;

import graph.Node;
import graph.VariableAssignment;

public interface Math extends Node {

	int evaluate(VariableAssignment varAss);
}
