package graph.logic;

import graph.Node;
import graph.VariableAssignment;

public interface Logic extends Node {

	boolean evaluate(VariableAssignment varAss);
}
