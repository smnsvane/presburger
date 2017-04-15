package graph.formula.quantifier;

import graph.formula.Formula;

public class Forall extends Quantifier {

	public static final String symbol = "A";

	public Forall(String variableSymbol) { super(variableSymbol); }

	@Override
	public String toString() {
		return symbol+variableSymbol+"."+getChild();
	}

	@Override
	public Formula negate() {
		Exists exists = new Exists(variableSymbol);
		exists.setChild(getChild().negate());
		return exists;
	}
}
