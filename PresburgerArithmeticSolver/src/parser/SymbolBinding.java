package parser;

import java.util.ArrayList;

import graph.Node;
import graph.formula.And;
import graph.formula.Implies;
import graph.formula.Not;
import graph.formula.Or;
import graph.formula.comparator.EqualTo;
import graph.formula.comparator.GreaterThan;
import graph.formula.comparator.GreaterThanOrEqualTo;
import graph.formula.comparator.LessThan;
import graph.formula.comparator.LessThanOrEqualTo;
import graph.formula.comparator.NotEqualTo;
import graph.formula.quantifier.Exists;
import graph.formula.quantifier.Forall;
import graph.term.Addition;
import graph.term.Product;
import graph.term.Subtraction;

public abstract class SymbolBinding {

	private static int precendenceCounter = 100;
	private static int nextPrecendence() { return --precendenceCounter; }
	private static int samePrecendence() { return precendenceCounter; }
	private static ArrayList<Binding> list = new ArrayList<>();
	public static String getSymbol(Class<? extends Node> clazz) {
		for (Binding b : list)
			if (clazz.equals(b.clazz))
				return b.symbol;
		throw new RuntimeException("can't find class "+clazz);
	}
	public static int getPrecedence(String symbol) {
		if (symbol == null)
			return 102;
		for (Binding b : list)
			if (symbol.equals(b.symbol))
				return b.precedence;
//		System.out.println("Warning: could not find symbol "+symbol);
		return 101;
	}
	public static boolean lowerOrEqualPrecedence(Node n1, Node n2) {
		int prec1 = getPrecedence(n1.getSymbol());
		int prec2 = getPrecedence(n2.getSymbol());
		boolean needsBracketGrouping = prec1 <= prec2;
		return needsBracketGrouping;
	}

	static {
		list.add(new Binding("*", Product.class, nextPrecendence()));
		list.add(new Binding("+", Addition.class, nextPrecendence()));
		list.add(new Binding("-", Subtraction.class, samePrecendence()));
		list.add(new Binding("=", EqualTo.class, nextPrecendence()));
		list.add(new Binding("!=", NotEqualTo.class, nextPrecendence()));
		list.add(new Binding(">", GreaterThan.class, nextPrecendence()));
		list.add(new Binding("<", LessThan.class, nextPrecendence()));
		list.add(new Binding(">=", GreaterThanOrEqualTo.class, nextPrecendence()));
		list.add(new Binding("<=", LessThanOrEqualTo.class, nextPrecendence()));
		list.add(new Binding("~", Not.class, nextPrecendence()));
		list.add(new Binding("&", And.class, nextPrecendence()));
		list.add(new Binding("/", Or.class, samePrecendence()));
		list.add(new Binding("->", Implies.class, nextPrecendence()));
		list.add(new Binding("A", Forall.class, nextPrecendence()));
		list.add(new Binding("E", Exists.class, samePrecendence()));
	}
}
