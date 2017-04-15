package graph;

import java.util.HashMap;

public class VariableAssignment {

	private HashMap<String, Integer> assignment = new HashMap<>();
	public Integer getAssignment(String variableSymbol) { return assignment.get(variableSymbol); }
	public VariableAssignment put(String variableSymbol, Integer assignment) {
		this.assignment.put(variableSymbol, assignment);
		return this;
	}

	@Override
	public String toString() { return assignment.toString(); }
}
