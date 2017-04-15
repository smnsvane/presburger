package graph.formula.quantifier;

import graph.formula.Formula;

public class Exists extends Quantifier {

	@Override
	public String getSymbol() { return "E"; }
	public Exists(String variableSymbol) { super(variableSymbol); }
	@Override
	public Formula negate() {
		Forall forall = new Forall(variableSymbol);
		forall.setChild(getChild().negate());
		return forall;
	}
}
