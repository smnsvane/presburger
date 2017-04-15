package graph.formula.quantifier;

import graph.formula.Formula;

public class Exists extends Quantifier {

	public static final String symbol = "E";
	public Exists(String variableSymbol) { super(variableSymbol); }

	@Override
	public String toString() {
		return symbol+variableSymbol+"."+getChild();
	}

	@Override
	public Formula negate() {
		Forall forall = new Forall(variableSymbol);
		forall.setChild(getChild().negate());
		return forall;
	}
}
