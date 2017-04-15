package parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import graph.SymbolBinding;
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
import graph.term.Multiply;
import graph.term.Subtraction;
import graph.term.Term;
import graph.term.Variable;

public class Parser {

	private SymbolBinding bindings = new SymbolBinding();

	public Formula parseLogic(String rawFormula) {

		String symbol = bindings.getSymbol(Forall.class);
		int index = rawFormula.indexOf(symbol);
		if (index == 0) {
			int dotIndex = rawFormula.indexOf(".", symbol.length());
			String varSymbol = rawFormula.substring(symbol.length(), dotIndex);
			Forall all = new Forall(varSymbol);
			all.setChild(parseLogic(rawFormula.substring(dotIndex + 1)));
			return all;
		}

		symbol = bindings.getSymbol(Exists.class);
		index = rawFormula.indexOf(symbol);
		if (index == 0) {
			int dotIndex = rawFormula.indexOf(".", symbol.length());
			String varSymbol = rawFormula.substring(symbol.length(), dotIndex);
			Exists exists = new Exists(varSymbol);
			exists.setChild(parseLogic(rawFormula.substring(dotIndex + 1)));
			return exists;
		}

		symbol = bindings.getSymbol(Implies.class);
		index = rawFormula.indexOf(symbol);
		if (index != -1) {
			Implies implies = new Implies();
			implies.setFirstChild(parseLogic(rawFormula.substring(0, index)));
			implies.setSecondChild(parseLogic(rawFormula.substring(index + symbol.length())));
			return implies;
		}

		symbol = bindings.getSymbol(Or.class);
		index = rawFormula.indexOf(symbol);
		if (index != -1) {
			Or or = new Or();
			or.setFirstChild(parseLogic(rawFormula.substring(0, index)));
			or.setSecondChild(parseLogic(rawFormula.substring(index + symbol.length())));
			return or;
		}

		symbol = bindings.getSymbol(And.class);
		index = rawFormula.indexOf(symbol);
		if (index != -1) {
			And and = new And();
			and.setFirstChild(parseLogic(rawFormula.substring(0, index)));
			and.setSecondChild(parseLogic(rawFormula.substring(index + symbol.length())));
			return and;
		}

		symbol = bindings.getSymbol(Not.class);
		index = rawFormula.indexOf(symbol);
		if (index != -1) {
			if (index != 0)
				throw new RuntimeException("trying to pass "+Not.class+
						" from a non-start line position, stuff will be skipped. Most likely malformed input");
			Not not = new Not();
			not.setChild(parseLogic(rawFormula.substring(index + symbol.length())));
			return not;
		}

		symbol = bindings.getSymbol(LessThanOrEqualTo.class);
		index = rawFormula.indexOf(symbol);
		if (index != -1) {
			LessThanOrEqualTo lessOrEqual = new LessThanOrEqualTo();
			lessOrEqual.setFirstChild(parseMath(rawFormula.substring(0, index)));
			lessOrEqual.setSecondChild(parseMath(rawFormula.substring(index + symbol.length())));
			return lessOrEqual;
		}

		symbol = bindings.getSymbol(GreaterThanOrEqualTo.class);
		index = rawFormula.indexOf(symbol);
		if (index != -1) {
			GreaterThanOrEqualTo greaterOrEqual = new GreaterThanOrEqualTo();
			greaterOrEqual.setFirstChild(parseMath(rawFormula.substring(0, index)));
			greaterOrEqual.setSecondChild(parseMath(rawFormula.substring(index + symbol.length())));
			return greaterOrEqual;
		}

		symbol = bindings.getSymbol(LessThan.class);
		index = rawFormula.indexOf(symbol);
		if (index != -1) {
			LessThan less = new LessThan();
			less.setFirstChild(parseMath(rawFormula.substring(0, index)));
			less.setSecondChild(parseMath(rawFormula.substring(index + symbol.length())));
			return less;
		}

		symbol = bindings.getSymbol(GreaterThan.class);
		index = rawFormula.indexOf(symbol);
		if (index != -1) {
			GreaterThan greater = new GreaterThan();
			greater.setFirstChild(parseMath(rawFormula.substring(0, index)));
			greater.setSecondChild(parseMath(rawFormula.substring(index + symbol.length())));
			return greater;
		}

		symbol = bindings.getSymbol(NotEqualTo.class);
		index = rawFormula.indexOf(symbol);
		if (index != -1) {
			NotEqualTo notEqual = new NotEqualTo();
			notEqual.setFirstChild(parseMath(rawFormula.substring(0, index)));
			notEqual.setSecondChild(parseMath(rawFormula.substring(index + symbol.length())));
			return notEqual;
		}

		symbol = bindings.getSymbol(EqualTo.class);
		index = rawFormula.indexOf(symbol);
		if (index != -1) {
			EqualTo equalTo = new EqualTo();
			equalTo.setFirstChild(parseMath(rawFormula.substring(0, index)));
			equalTo.setSecondChild(parseMath(rawFormula.substring(index + symbol.length())));
			return equalTo;
		}

		throw new RuntimeException(rawFormula+" could not be parsed as a "+Formula.class);
	}

	public Term parseMath(String rawFormula) {

		String symbol = bindings.getSymbol(Addition.class);
		int index = rawFormula.indexOf(symbol);
		if (index != -1) {
			Addition add = new Addition();
			add.setFirstChild(parseMath(rawFormula.substring(0, index)));
			add.setSecondChild(parseMath(rawFormula.substring(index + symbol.length())));
			return add;
		}

		symbol = bindings.getSymbol(Subtraction.class);
		index = rawFormula.indexOf(symbol);
		if (index != -1) {
			Subtraction sub = new Subtraction();
			sub.setFirstChild(parseMath(rawFormula.substring(0, index)));
			sub.setSecondChild(parseMath(rawFormula.substring(index + symbol.length())));
			return sub;
		}

		symbol = bindings.getSymbol(Multiply.class);
		index = rawFormula.indexOf(symbol);
		if (index != -1) {
			Multiply mul = new Multiply();
			mul.setFirstChild((Constant) parseMath(rawFormula.substring(0, index)));
			mul.setSecondChild(parseMath(rawFormula.substring(index + symbol.length())));
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
