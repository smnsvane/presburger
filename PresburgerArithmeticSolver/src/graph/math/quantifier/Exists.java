package graph.math.quantifier;

import graph.Branch;

public class Exists extends Quantifier {

	public static final String symbol = "E";
	public Exists(Branch parent, String variableSymbol) {
		super(parent, symbol, variableSymbol);
	}
}
