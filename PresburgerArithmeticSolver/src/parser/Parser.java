package parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import graph.formula.And;
import graph.formula.Formula;
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
import graph.term.Constant;
import graph.term.Product;
import graph.term.Subtraction;
import graph.term.Term;
import graph.term.Variable;

public class Parser {

	public Formula parseLogic(String rawFormula) {

		String symbol = SymbolBinding.getSymbol(Forall.class);
		int index = rawFormula.indexOf(symbol);
		if (index == 0) {
			int dotIndex = rawFormula.indexOf(".", symbol.length());
			String varSymbol = rawFormula.substring(symbol.length(), dotIndex);
			Forall all = new Forall(varSymbol, parseLogic(rawFormula.substring(dotIndex + 1)));
			return all;
		}

		symbol = SymbolBinding.getSymbol(Exists.class);
		index = rawFormula.indexOf(symbol);
		if (index == 0) {
			int dotIndex = rawFormula.indexOf(".", symbol.length());
			String varSymbol = rawFormula.substring(symbol.length(), dotIndex);
			Exists exists = new Exists(varSymbol, parseLogic(rawFormula.substring(dotIndex + 1)));
			return exists;
		}

		symbol = SymbolBinding.getSymbol(Implies.class);
		index = rawFormula.indexOf(symbol);
		if (index != -1) {
			Implies implies = new Implies(
					parseLogic(rawFormula.substring(0, index)),
					parseLogic(rawFormula.substring(index + symbol.length())));
			return implies;
		}

		symbol = SymbolBinding.getSymbol(Or.class);
		index = rawFormula.indexOf(symbol);
		if (index != -1) {
			Or or = new Or(
					parseLogic(rawFormula.substring(0, index)),
					parseLogic(rawFormula.substring(index + symbol.length())));
			return or;
		}

		symbol = SymbolBinding.getSymbol(And.class);
		index = rawFormula.indexOf(symbol);
		if (index != -1) {
			And and = new And(
					parseLogic(rawFormula.substring(0, index)),
					parseLogic(rawFormula.substring(index + symbol.length())));
			return and;
		}

		symbol = SymbolBinding.getSymbol(Not.class);
		index = rawFormula.indexOf(symbol);
		if (index != -1) {
			if (index != 0)
				throw new RuntimeException("trying to pass "+Not.class+
						" from a non-start line position, stuff will be skipped. Most likely malformed input");
			Not not = new Not(
					parseLogic(rawFormula.substring(index + symbol.length())));
			return not;
		}

		symbol = SymbolBinding.getSymbol(LessThanOrEqualTo.class);
		index = rawFormula.indexOf(symbol);
		if (index != -1) {
			LessThanOrEqualTo lessOrEqual = new LessThanOrEqualTo(
					parseMath(rawFormula.substring(0, index)),
					parseMath(rawFormula.substring(index + symbol.length())));
			return lessOrEqual;
		}

		symbol = SymbolBinding.getSymbol(GreaterThanOrEqualTo.class);
		index = rawFormula.indexOf(symbol);
		if (index != -1) {
			GreaterThanOrEqualTo greaterOrEqual = new GreaterThanOrEqualTo(
					parseMath(rawFormula.substring(0, index)),
					parseMath(rawFormula.substring(index + symbol.length())));
			return greaterOrEqual;
		}

		symbol = SymbolBinding.getSymbol(LessThan.class);
		index = rawFormula.indexOf(symbol);
		if (index != -1) {
			LessThan less = new LessThan(
					parseMath(rawFormula.substring(0, index)),
					parseMath(rawFormula.substring(index + symbol.length())));
			return less;
		}

		symbol = SymbolBinding.getSymbol(GreaterThan.class);
		index = rawFormula.indexOf(symbol);
		if (index != -1) {
			GreaterThan greater = new GreaterThan(
					parseMath(rawFormula.substring(0, index)),
					parseMath(rawFormula.substring(index + symbol.length())));
			return greater;
		}

		symbol = SymbolBinding.getSymbol(NotEqualTo.class);
		index = rawFormula.indexOf(symbol);
		if (index != -1) {
			NotEqualTo notEqual = new NotEqualTo(
					parseMath(rawFormula.substring(0, index)),
					parseMath(rawFormula.substring(index + symbol.length())));
			return notEqual;
		}

		symbol = SymbolBinding.getSymbol(EqualTo.class);
		index = rawFormula.indexOf(symbol);
		if (index != -1) {
			EqualTo equalTo = new EqualTo(
					parseMath(rawFormula.substring(0, index)),
					parseMath(rawFormula.substring(index + symbol.length())));
			return equalTo;
		}

		throw new RuntimeException(rawFormula+" could not be parsed as a "+Formula.class);
	}

	public Term parseMath(String rawFormula) {

		String symbol = SymbolBinding.getSymbol(Addition.class);
		int index = rawFormula.indexOf(symbol);
		if (index != -1) {
			Addition add = new Addition(
					parseMath(rawFormula.substring(0, index)),
					parseMath(rawFormula.substring(index + symbol.length())));
			return add;
		}

		symbol = SymbolBinding.getSymbol(Subtraction.class);
		index = rawFormula.indexOf(symbol);
		if (index != -1) {
			Subtraction sub = new Subtraction(
					parseMath(rawFormula.substring(0, index)),
					parseMath(rawFormula.substring(index + symbol.length())));
			return sub;
		}

		symbol = SymbolBinding.getSymbol(Product.class);
		index = rawFormula.indexOf(symbol);
		if (index != -1) {
			Product mul = new Product(
					(Constant) parseMath(rawFormula.substring(0, index)),
					parseMath(rawFormula.substring(index + symbol.length())));
			return mul;
		}

		// no other symbol matches at this point
		// look for constant and variable matches
		Pattern varPattern = Pattern.compile("(-?)([0-9]*\\*?)([A-Za-z][0-9]*)");
		Matcher m = varPattern.matcher(rawFormula);
		if (m.find()) {
			String factorString = m.group(2);
			int factor;
			if (factorString.isEmpty())
				factor = 1;
			else
				factor = Integer.parseInt(factorString);
			if (!m.group(1).isEmpty())
				factor = -factor;
			Variable var = new Variable(factor, m.group(3));
			return var;
		}

		int number = Integer.parseInt(rawFormula);
		Constant constant = new Constant(number);
		return constant;
	}
}
