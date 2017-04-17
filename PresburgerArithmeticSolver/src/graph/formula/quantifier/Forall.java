package graph.formula.quantifier;

import graph.formula.Formula;
import graph.formula.Not;

public class Forall extends Quantifier {

	@Override
	public String getSymbol() { return "A"; }
	public Forall(String variableSymbol, Formula formula) {
		super(variableSymbol, formula);
	}
	@Override
	public Formula negate() {
		Exists exists = new Exists(getVariableSymbol(), getChild().negate());
		return exists;
	}
	@Override
	public Formula simplify() { return this; }
	public Not toExists() {
		Exists exists = new Exists(getVariableSymbol(), getChild().negate());
		Not not = new Not(exists);
		return not;
	}
	@Override
	public Forall copy() { return new Forall(getVariableSymbol(), getChild().copy()); }
}
