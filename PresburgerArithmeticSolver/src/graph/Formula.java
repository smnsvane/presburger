package graph;

public interface Formula extends Node {

	boolean evaluate(VariableAssignment assignment);
	Formula negate();
	Formula reduce();
	Formula copy();
}
