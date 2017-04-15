package graph.formula.quantifier;

import graph.formula.Formula;
import graph.formula.comparator.EqualTo;
import graph.term.Term;

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
	@Override
	public Exists copy() {
		Exists copy = new Exists(variableSymbol);
		copy.setChild((Formula) getChild().copy());
		return copy;
	}
}
