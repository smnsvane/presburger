package graph;

import graph.term.Sum;

public interface Term extends Node {

	int evaluate(VariableAssignment assignment);
	Sum toSum();
	Term multiply(int factor);
	Term simplify();
	Term copy();
}
