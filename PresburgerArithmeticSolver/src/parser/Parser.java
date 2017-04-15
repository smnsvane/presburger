package parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import graph.logic.Formula;
import graph.logic.Not;
import graph.logic.binary.And;
import graph.logic.binary.Implies;
import graph.logic.binary.Or;
import graph.math.comparator.EqualTo;
import graph.math.comparator.GreaterThan;
import graph.math.comparator.GreaterThanOrEqualTo;
import graph.math.comparator.LessThan;
import graph.math.comparator.LessThanOrEqualTo;
import graph.math.comparator.NotEqualTo;
import graph.math.quantifier.Exists;
import graph.math.quantifier.Forall;
import graph.math.Constant;
import graph.math.Term;
import graph.math.Multiply;
import graph.math.Variable;
import graph.math.binary.Addition;
import graph.math.binary.Subtraction;

public class Parser {

	public Formula parseLogic(String rawFormula, Formula parent) {

		int index = rawFormula.indexOf(Forall.symbol);
		if (index == 0) {
			int dotIndex = rawFormula.indexOf(".", Forall.symbol.length());
			String varSymbol = rawFormula.substring(Forall.symbol.length(), dotIndex);
			Forall all = new Forall(varSymbol);
			all.setChild(parseLogic(rawFormula.substring(dotIndex + 1), all));
			return all;
		}

		index = rawFormula.indexOf(Exists.symbol);
		if (index == 0) {
			int dotIndex = rawFormula.indexOf(".", Exists.symbol.length());
			String varSymbol = rawFormula.substring(Exists.symbol.length(), dotIndex);
			Exists exists = new Exists(varSymbol);
			exists.setChild(parseLogic(rawFormula.substring(dotIndex + 1), exists));
			return exists;
		}

		index = rawFormula.indexOf(Implies.symbol);
		if (index != -1) {
			Implies implies = new Implies();
			implies.setFirstChild(parseLogic(rawFormula.substring(0, index), implies));
			implies.setSecondChild(parseLogic(rawFormula.substring(index + Implies.symbol.length()), implies));
			return implies;
		}

		index = rawFormula.indexOf(Or.symbol);
		if (index != -1) {
			Or or = new Or();
			or.setFirstChild(parseLogic(rawFormula.substring(0, index), or));
			or.setSecondChild(parseLogic(rawFormula.substring(index + Or.symbol.length()), or));
			return or;
		}

		index = rawFormula.indexOf(And.symbol);
		if (index != -1) {
			And and = new And();
			and.setFirstChild(parseLogic(rawFormula.substring(0, index), and));
			and.setSecondChild(parseLogic(rawFormula.substring(index + And.symbol.length()), and));
			return and;
		}

		index = rawFormula.indexOf(Not.symbol);
		if (index != -1) {
			// TODO: index should be '0' or there is formula that is being skipped
			Not not = new Not();
			not.setChild(parseLogic(rawFormula.substring(index + Not.symbol.length()), not));
			return not;
		}

		index = rawFormula.indexOf(LessThanOrEqualTo.symbol);
		if (index != -1) {
			LessThanOrEqualTo lessOrEqual = new LessThanOrEqualTo();
			lessOrEqual.setFirstChild(parseMath(rawFormula.substring(0, index), lessOrEqual));
			lessOrEqual.setSecondChild(parseMath(rawFormula.substring(index + LessThanOrEqualTo.symbol.length()), lessOrEqual));
			return lessOrEqual;
		}

		index = rawFormula.indexOf(GreaterThanOrEqualTo.symbol);
		if (index != -1) {
			GreaterThanOrEqualTo greaterOrEqual = new GreaterThanOrEqualTo();
			greaterOrEqual.setFirstChild(parseMath(rawFormula.substring(0, index), greaterOrEqual));
			greaterOrEqual.setSecondChild(parseMath(rawFormula.substring(index + GreaterThanOrEqualTo.symbol.length()), greaterOrEqual));
			return greaterOrEqual;
		}

		index = rawFormula.indexOf(LessThan.symbol);
		if (index != -1) {
			LessThan less = new LessThan();
			less.setFirstChild(parseMath(rawFormula.substring(0, index), less));
			less.setSecondChild(parseMath(rawFormula.substring(index + LessThan.symbol.length()), less));
			return less;
		}

		index = rawFormula.indexOf(GreaterThan.symbol);
		if (index != -1) {
			GreaterThan greater = new GreaterThan();
			greater.setFirstChild(parseMath(rawFormula.substring(0, index), greater));
			greater.setSecondChild(parseMath(rawFormula.substring(index + GreaterThan.symbol.length()), greater));
			return greater;
		}

		index = rawFormula.indexOf(NotEqualTo.symbol);
		if (index != -1) {
			NotEqualTo notEqual = new NotEqualTo();
			notEqual.setFirstChild(parseMath(rawFormula.substring(0, index), notEqual));
			notEqual.setSecondChild(parseMath(rawFormula.substring(index + NotEqualTo.symbol.length()), notEqual));
			return notEqual;
		}

		index = rawFormula.indexOf(EqualTo.symbol);
		if (index != -1) {
			EqualTo equalTo = new EqualTo();
			equalTo.setFirstChild(parseMath(rawFormula.substring(0, index), equalTo));
			equalTo.setSecondChild(parseMath(rawFormula.substring(index + EqualTo.symbol.length()), equalTo));
			return equalTo;
		}

		throw new RuntimeException(rawFormula+" could not be parsed as a "+Formula.class);
	}

	public Term parseMath(String rawFormula, Object parent) {

		int index = rawFormula.indexOf(Addition.symbol);
		if (index != -1) {
			Addition add = new Addition();
			add.setFirstChild(parseMath(rawFormula.substring(0, index), add));
			add.setSecondChild(parseMath(rawFormula.substring(index + Addition.symbol.length()), add));
			return add;
		}

		index = rawFormula.indexOf(Subtraction.symbol);
		if (index != -1) {
			Subtraction sub = new Subtraction();
			sub.setFirstChild(parseMath(rawFormula.substring(0, index), sub));
			sub.setSecondChild(parseMath(rawFormula.substring(index + Addition.symbol.length()), sub));
			return sub;
		}

		index = rawFormula.indexOf(Multiply.symbol);
		if (index != -1) {
			Multiply mul = new Multiply();
			mul.setFirstChild((Constant) parseMath(rawFormula.substring(0, index), mul));
			mul.setSecondChild(parseMath(rawFormula.substring(index + Addition.symbol.length()), mul));
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
