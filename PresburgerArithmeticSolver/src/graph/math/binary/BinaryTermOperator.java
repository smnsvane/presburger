package graph.math.binary;

import graph.NodeBranch;
import graph.math.Term;

public abstract class BinaryTermOperator implements NodeBranch, Term {

	private Term child1, child2;
	public Term getFirstChild() { return child1; }
	public Term getSecondChild() { return child2; }
	public void setFirstChild(Term child) { child1 = child; }
	public void setSecondChild(Term child) { child2 = child; }
}
