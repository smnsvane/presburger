package graph.math.comparator;

import graph.Branch;
import graph.VariableAssignment;

public class GreaterThan extends Comparator {

	public static final String symbol = ">";
	public GreaterThan(Branch parent) { super(parent, symbol); }

	@Override
	public boolean evaluate(VariableAssignment varAss) {
		return getFirstChild().evaluate(varAss) >
				getSecondChild().evaluate(varAss);
	}
}
