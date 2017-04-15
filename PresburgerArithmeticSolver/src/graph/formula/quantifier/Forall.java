package graph.formula.quantifier;

import graph.formula.Formula;

public class Forall extends Quantifier {

	@Override
	public String getSymbol() { return "A"; }
	public Forall(String variableSymbol) { super(variableSymbol); }
	@Override
	public Formula negate() {
		Exists exists = new Exists(variableSymbol);
		exists.setChild(getChild().negate());
		return exists;
	}
}
