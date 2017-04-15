package graph.math.comparator;

import graph.NodeBranch;
import graph.logic.Formula;
import graph.math.Term;

public abstract class Comparator implements NodeBranch, Formula {

	private Term child1, child2;
	public Term getFirstChild() { return child1; }
	public Term getSecondChild() { return child2; }
	public void setFirstChild(Term child) { child1 = child; }
	public void setSecondChild(Term child) { child2 = child; }

//	public abstract LessThan toLessThan(); 
}
