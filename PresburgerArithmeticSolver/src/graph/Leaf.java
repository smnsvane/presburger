package graph;

import parser.SymbolBindings;

public abstract class Leaf implements Node {

	@Override
	public final String getSymbol() { return SymbolBindings.getSymbol(getClass()); }
}
