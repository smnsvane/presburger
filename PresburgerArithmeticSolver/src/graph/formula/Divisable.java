package graph.formula;

import graph.Formula;
import graph.SingleChildBranch;
import graph.Term;
import graph.VariableAssignment;

public class Divisable extends SingleChildBranch<Term> implements Formula {

	private boolean negated;
	private int divisor;
	public Divisable(boolean negated, int divisor, Term child) {
		super(child);
		this.negated = negated;
		this.divisor = divisor;
	}

	@Override
	public boolean evaluate(VariableAssignment assignment) {
		if (negated)
			return getChild().evaluate(assignment) % divisor != 0;
		return getChild().evaluate(assignment) % divisor == 0;
	}
	@Override
	public Formula negate() {
		negated = !negated;
		return this;
	}
	@Override
	public Divisable simplify() { return this; }
	@Override
	public Divisable copy() { return new Divisable(negated, divisor, getChild().copy()); }
	@Override
	public String toString() { return (negated?"~":"")+divisor+getSymbol()+getChild(); }
}
