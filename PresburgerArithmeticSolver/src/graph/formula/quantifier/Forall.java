package graph.formula.quantifier;

import graph.Formula;
import graph.formula.Not;

public class Forall extends Quantifier {

	public Forall(String variableSymbol, Formula formula) {
		super(variableSymbol, formula);
	}
	@Override
	public Formula negate() {
		Exists exists = new Exists(getVariableSymbol(), getChild().negate());
		return exists;
	}
	@Override
	public Formula reduce() { return this; }
	public Not toExists() {
		Not notChild = new Not(getChild());
		Exists exists = new Exists(getVariableSymbol(), notChild);
		Not notParent = new Not(exists);
		return notParent;
	}
	@Override
	public Forall copy() { return new Forall(getVariableSymbol(), getChild().copy()); }
}
