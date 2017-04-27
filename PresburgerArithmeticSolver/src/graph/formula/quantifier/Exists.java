package graph.formula.quantifier;

import engine.CheckSubtreeForQuantifiers;
import graph.Formula;
import graph.formula.Not;
import graph.formula.True;

public class Exists extends Quantifier {

	private boolean negated = false;
	public Exists(String variableSymbol, Formula formula) {
		super(variableSymbol, formula);
	}
	@Override
	public Exists negate() {
		Exists negatedExists = copy();
		negatedExists.negated = !negated;
		return negatedExists;
	}
	@Override
	public Exists reduce() { return this; }
	public Not toForall() {
		Forall forall = new Forall(getVariableSymbol(), getChild().negate());
		Not not = new Not(forall);
		return not;
	}
	@Override
	public Exists copy() {
		Exists e = new Exists(getVariableSymbol(), getChild().copy());
		e.negated = negated;
		return e;
	}
	public boolean hasQuantifiersInSubtree() {
		Formula result = new CheckSubtreeForQuantifiers(getChild()).go();
		return result instanceof True;
	}
	@Override
	public String toString() {
		return (negated?"~":"")+super.toString();
	}
}
