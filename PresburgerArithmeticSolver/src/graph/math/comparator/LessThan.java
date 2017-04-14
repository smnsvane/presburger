package graph.math.comparator;

import graph.Branch;
import graph.VariableAssignment;

public class LessThan extends Comparator {

	public LessThan(Branch parent) { super(parent, "<"); }

	@Override
	public boolean evaluate(VariableAssignment varAss) {
		return getFirstChild().evaluate(varAss) <
				getSecondChild().evaluate(varAss);
	}
}
