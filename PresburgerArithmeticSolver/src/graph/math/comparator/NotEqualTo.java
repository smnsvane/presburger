package graph.math.comparator;

import graph.Branch;
import graph.VariableAssignment;

public class NotEqualTo extends Comparator {

	public NotEqualTo(Branch parent) { super(parent, "!="); }

	@Override
	public boolean evaluate(VariableAssignment varAss) {
		return getFirstChild().evaluate(varAss) !=
				getSecondChild().evaluate(varAss);
	}
}
