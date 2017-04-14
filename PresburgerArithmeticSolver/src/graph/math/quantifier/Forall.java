package graph.math.quantifier;

import graph.Branch;

public class Forall extends Quantifier {

	public static final String symbol = "A";
	public Forall(Branch parent, String variableSymbol) {
		super(parent, symbol, variableSymbol);
	}
}
