package graph;

import java.util.HashMap;

public class VariableAssignment {

	private final HashMap<Character, Integer> assignment = new HashMap<>();

	public int getAssignment(char variableSymbol) {
		return assignment.get(variableSymbol);
	}
	public void addAssignment(char variableSymbol, int assignment) {
		this.assignment.put(variableSymbol, assignment);
	}
}
