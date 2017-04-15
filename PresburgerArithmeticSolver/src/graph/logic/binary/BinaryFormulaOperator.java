package graph.logic.binary;

import graph.NodeBranch;
import graph.logic.Formula;

public abstract class BinaryFormulaOperator implements NodeBranch, Formula {

	private Formula child1, child2;
	public Formula getFirstChild() { return child1; }
	public Formula getSecondChild() { return child2; }
	public void setFirstChild(Formula child) { child1 = child; }
	public void setSecondChild(Formula child) { child2 = child; }
}
