package graph.math.comparator;

import graph.Branch;
import graph.VariableAssignment;

public class GreaterThanOrEqualTo extends Comparator {

	public static final String symbol = ">=";
	public GreaterThanOrEqualTo(Branch parent) { super(parent, symbol); }

	@Override
	public boolean evaluate(VariableAssignment varAss) {
		return getFirstChild().evaluate(varAss) >=
				getSecondChild().evaluate(varAss);
	}
}
