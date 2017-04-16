package parser;

import graph.Node;

public class Binding {

	public final String symbol;
	public final Class<? extends Node> clazz;
	public final int precedence;
	public Binding(String symbol, Class<? extends Node> clazz, int precedence) {
		this.symbol = symbol;
		this.clazz = clazz;
		this.precedence = precedence;
	}
}
