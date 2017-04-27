package graph.formula.quantifier;

import engine.GraphTransverser;
import graph.Branch;
import graph.Formula;
import graph.Node;
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
	public Exists reduce() { return this; }
	public Not toForall() {
		Forall forall = new Forall(getVariableSymbol(), getChild().negate());
		Not not = new Not(forall);
		return not;
	}
	@Override
	public Exists copy() { return new Exists(getVariableSymbol(), getChild().copy()); }
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public boolean hasNoQuantifiersInSubtree() {
		if (getChild() instanceof Branch<?>) {
			GraphTransverser transverser = new GraphTransverser((Branch<Node>) getChild());
			while (transverser.hasNext()) {
				Branch obj = transverser.next();
				if (obj instanceof Quantifier)
					return false;
			}
		}
		return true;
	}
}
