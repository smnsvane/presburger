package graph.formula.quantifier;

import graph.formula.Formula;
import graph.formula.Not;

public class Exists extends Quantifier {

	@Override
	public String getSymbol() { return "E"; }
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
}
