package graph.math;

import graph.NodeBranch;
import graph.VariableAssignment;

public class Multiply implements NodeBranch, Term {

	public static final String symbol = "*";

	private Constant constant;
	public Term getConstantChild() { return constant; }
	public void setConstantChild(Constant child) { constant = child; }

	private Term math;
	public Term getMathChild() { return math; }
	public void setMathChild(Term child) { math = child; }

	@Override
	public String toString() {
		return constant+symbol+math;
	}

	@Override
	public int evaluate(VariableAssignment varAss) {
		return constant.evaluate(varAss) * math.evaluate(varAss);
	}
}
