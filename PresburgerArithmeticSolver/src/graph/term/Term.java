package graph.term;

import graph.Node;
import graph.VariableAssignment;

public interface Term extends Node {

	int evaluate(VariableAssignment varAss);
}
