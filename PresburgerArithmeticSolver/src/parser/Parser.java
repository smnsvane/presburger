package parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import graph.Formula;
import graph.Term;
import graph.formula.And;
import graph.formula.Divisable;
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
import graph.term.Variable;

public class Parser {

	public Formula parse(String rawFormula) {
		Formula root = parseLogic(rawFormula);
		root.validate();
		return root;
	}

	private Formula parseLogic(String rawFormula) {

		rawFormula = removeEnclosingBrackets(rawFormula.trim()).trim();

		String symbol = SymbolBinding.getSymbol(Forall.class);
		if (rawFormula.substring(0, symbol.length()).equals(symbol)) {
			int dotIndex = rawFormula.indexOf(".", symbol.length());
			String varSymbol = rawFormula.substring(symbol.length(), dotIndex);
			Forall all = new Forall(varSymbol, parseLogic(rawFormula.substring(dotIndex + 1)));
			return all;
		}

		symbol = SymbolBinding.getSymbol(Exists.class);
		if (rawFormula.substring(0, symbol.length()).equals(symbol)) {
			int dotIndex = rawFormula.indexOf(".", symbol.length());
			String varSymbol = rawFormula.substring(symbol.length(), dotIndex);
			Exists exists = new Exists(varSymbol, parseLogic(rawFormula.substring(dotIndex + 1)));
			return exists;
		}

		String symbolForImplies = SymbolBinding.getSymbol(Implies.class);
		int indexForImplies = findSymbolIndexInDepthZero(symbolForImplies, rawFormula);
		String symbolForOr = SymbolBinding.getSymbol(Or.class);
		int indexForOr = findSymbolIndexInDepthZero(symbolForOr, rawFormula);
		String symbolForAnd = SymbolBinding.getSymbol(And.class);
		int indexForAnd = findSymbolIndexInDepthZero(symbolForAnd, rawFormula);
		if (indexForImplies != -1 &&
				(indexForOr == -1 || indexForImplies < indexForOr) &&
				(indexForAnd == -1 || indexForImplies < indexForAnd)) {
			Implies implies = new Implies(
					parseLogic(rawFormula.substring(0, indexForImplies)),
					parseLogic(rawFormula.substring(indexForImplies + symbolForImplies.length())));
			return implies;
		}
		if (indexForOr != -1 && (indexForAnd == -1 || indexForOr < indexForAnd)) {
			Or or = new Or(
					parseLogic(rawFormula.substring(0, indexForOr)),
					parseLogic(rawFormula.substring(indexForOr + symbolForOr.length())));
			return or;
		}
		if (indexForAnd != -1) {
			And and = new And(
					parseLogic(rawFormula.substring(0, indexForAnd)),
					parseLogic(rawFormula.substring(indexForAnd + symbolForAnd.length())));
			return and;
		}

		symbol = SymbolBinding.getSymbol(Not.class);
		if (rawFormula.substring(0, symbol.length()).equals(symbol)) {
			Not not = new Not(
					parseLogic(rawFormula.substring(symbol.length())));
			return not;
		}

		symbol = SymbolBinding.getSymbol(Divisable.class);
		int index = findSymbolIndexInDepthZero(symbol, rawFormula);
		if (index != -1) {
			Divisable divisable = new Divisable(
					false,
					((Constant) parseMath(rawFormula.substring(0, index))).getValue(),
					parseMath(rawFormula.substring(index + symbol.length())));
			return divisable;
		}

		symbol = SymbolBinding.getSymbol(LessThanOrEqualTo.class);
		index = findSymbolIndexInDepthZero(symbol, rawFormula);
		if (index != -1) {
			LessThanOrEqualTo lessOrEqual = new LessThanOrEqualTo(
					parseMath(rawFormula.substring(0, index)),
					parseMath(rawFormula.substring(index + symbol.length())));
			return lessOrEqual;
		}

		symbol = SymbolBinding.getSymbol(GreaterThanOrEqualTo.class);
		index = findSymbolIndexInDepthZero(symbol, rawFormula);
		if (index != -1) {
			GreaterThanOrEqualTo greaterOrEqual = new GreaterThanOrEqualTo(
					parseMath(rawFormula.substring(0, index)),
					parseMath(rawFormula.substring(index + symbol.length())));
			return greaterOrEqual;
		}

		symbol = SymbolBinding.getSymbol(LessThan.class);
		index = findSymbolIndexInDepthZero(symbol, rawFormula);
		if (index != -1) {
			LessThan less = new LessThan(
					parseMath(rawFormula.substring(0, index)),
					parseMath(rawFormula.substring(index + symbol.length())));
			return less;
		}

		symbol = SymbolBinding.getSymbol(GreaterThan.class);
		index = findSymbolIndexInDepthZero(symbol, rawFormula);
		if (index != -1) {
			GreaterThan greater = new GreaterThan(
					parseMath(rawFormula.substring(0, index)),
					parseMath(rawFormula.substring(index + symbol.length())));
			return greater;
		}

		symbol = SymbolBinding.getSymbol(NotEqualTo.class);
		index = findSymbolIndexInDepthZero(symbol, rawFormula);
		if (index != -1) {
			NotEqualTo notEqual = new NotEqualTo(
					parseMath(rawFormula.substring(0, index)),
					parseMath(rawFormula.substring(index + symbol.length())));
			return notEqual;
		}

		symbol = SymbolBinding.getSymbol(EqualTo.class);
		index = findSymbolIndexInDepthZero(symbol, rawFormula);
		if (index != -1) {
			EqualTo equalTo = new EqualTo(
					parseMath(rawFormula.substring(0, index)),
					parseMath(rawFormula.substring(index + symbol.length())));
			return equalTo;
		}

		throw new RuntimeException(rawFormula+" could not be parsed as a "+Formula.class.getSimpleName());
	}

	private Term parseMath(String rawFormula) {

		rawFormula = removeEnclosingBrackets(rawFormula.trim()).trim();

		String symbol = SymbolBinding.getSymbol(Addition.class);
		int index = findSymbolIndexInDepthZero(symbol, rawFormula);
		if (index != -1) {
			Addition add = new Addition(
					parseMath(rawFormula.substring(0, index)),
					parseMath(rawFormula.substring(index + symbol.length())));
			return add;
		}
		//FIXME: addition and subtraction have to be parsed with same precedence
		symbol = SymbolBinding.getSymbol(Subtraction.class);
		index = findSymbolIndexInDepthZero(symbol, rawFormula);
		if (index != -1) {
			Subtraction sub = new Subtraction(
					parseMath(rawFormula.substring(0, index)),
					parseMath(rawFormula.substring(index + symbol.length())));
			return sub;
		}

		symbol = SymbolBinding.getSymbol(Product.class);
		index = findSymbolIndexInDepthZero(symbol, rawFormula);
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

		int number = Integer.parseInt(rawFormula.trim());
		Constant constant = new Constant(number);
		return constant;
	}

	private int findSymbolIndexInDepthZero(String symbol, String rawFormula) {
		int depth = 0;
		int lastPossibleMatchIndex = rawFormula.length() - symbol.length();
		for (int i = 0; i < rawFormula.length(); i++) {
			char charInRawFormula = rawFormula.charAt(i);
			if (charInRawFormula == '(')
				depth++;
			else if (charInRawFormula == ')') {
				depth--;
				if (depth < 0)
					throw new RuntimeException("')' at index='"+i+
							"' not matched by preceding '(' in the substring "+rawFormula);
			} else if (depth == 0 && i <= lastPossibleMatchIndex) {
				String symbolCandidate = rawFormula.substring(i, i + symbol.length());
				if (symbolCandidate.equals(symbol))
					return i;
			}
		}
		if (depth > 0)
			throw new RuntimeException("More '(' than ')' in the substring "+rawFormula);
		return -1;
	}
	private String removeEnclosingBrackets(String rawFormula) {
		int indexOfLastChar = rawFormula.length() - 1;
		if (rawFormula.charAt(0) == '(' && rawFormula.charAt(indexOfLastChar) == ')') {
			int depth = 1;
			for (int i = 1; i < indexOfLastChar; i++) {
				char charInRawFormula = rawFormula.charAt(i);
				if (charInRawFormula == '(')
					depth++;
				else if (charInRawFormula == ')') {
					depth--;
					if (depth == 0) // first and last bracket in the line does not match each other
						return rawFormula;
				}
			}
			return rawFormula.substring(1, indexOfLastChar);
		}
		return rawFormula;
	}
}
