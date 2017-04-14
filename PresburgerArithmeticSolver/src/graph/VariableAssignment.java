package graph;

import java.util.HashMap;

public class VariableAssignment {

	private final HashMap<String, Integer> assignment = new HashMap<>();

	public int getAssignment(String variableSymbol) {
		return assignment.get(variableSymbol);
	}
	public void addAssignment(String variableSymbol, int assignment) {
		this.assignment.put(variableSymbol, assignment);
	}
}
