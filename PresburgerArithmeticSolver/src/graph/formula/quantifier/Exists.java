package graph.formula.quantifier;

import graph.Formula;
import graph.formula.Not;

public class Exists extends Quantifier {

	public Exists(String variableSymbol, Formula formula) {
		super(variableSymbol, formula);
	}
	@Override
	public Formula negate() {
		Forall forall = new Forall(getVariableSymbol(), getChild().negate());
		return forall;
	}
	@Override
	public Exists simplify() { return this; }
	public Not toForall() {
		Forall forall = new Forall(getVariableSymbol(), getChild().negate());
		Not not = new Not(forall);
		return not;
	}
	@Override
	public Exists copy() { return new Exists(getVariableSymbol(), getChild().copy()); }
}
