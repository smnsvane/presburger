package graph;

import java.util.HashMap;

public class VariableAssignment {

	private HashMap<String, Integer> assignment = new HashMap<>();
	public Integer getAssignment(String variableSymbol) { return assignment.get(variableSymbol); }
	public void addAssignment(String variableSymbol, Integer assignment) { this.assignment.put(variableSymbol, assignment); }

	@Override
	public String toString() { return assignment.toString(); }
}
