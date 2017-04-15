package graph.term;

import java.util.ArrayList;

import graph.Branch;
import graph.VariableAssignment;

public class Sum implements Term, Branch {

	private ArrayList<Term> children = new ArrayList<>();
	public void addChild(Term child) { children.add(child); }

	@Override
	public Sum replaceVariables(VariableAssignment assignment) {
		for (int i = 0; i < children.size(); i++)
			children.set(i, (Term) children.get(i).replaceVariables(assignment));
		return this;
	}
	@Override
	public int evaluate(VariableAssignment varAss) {
		int value = 0;
		for (Term t : children)
			value += t.evaluate(varAss);
		return value;
	}
	@Override
	public Sum toSum() { return this; }
	@Override
	public Sum multiply(int factor) {
		for (int i = 0; i < children.size(); i++)
			children.set(i, children.get(i).multiply(factor));
		return this;
	}
	public void flatten() {
		for (int i = 0; i < children.size(); i++) {
			if (children.get(i) instanceof Sum) {
				Sum child = (Sum) children.remove(i);
				children.addAll(child.children);
			}
		}
	}
}
