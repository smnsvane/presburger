package parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import graph.NodeBranch;
import graph.logic.Logic;
import graph.logic.Not;
import graph.logic.binary.And;
import graph.logic.binary.Implies;
import graph.logic.binary.Or;
import graph.math.comparator.EqualTo;
import graph.math.Constant;
import graph.math.Math;
import graph.math.Variable;
import graph.math.binary.Addition;

public class Parser {

	public NodeBranch<?> root = null;

	public Object parse(String rawFormula, NodeBranch<?> parent) {

		int index = rawFormula.indexOf(Implies.symbol);
		if (index != -1) {
			Implies implies = new Implies();
			implies.setFirstChild((Logic) parse(rawFormula.substring(0, index), implies));
			implies.setSecondChild((Logic) parse(rawFormula.substring(index + Implies.symbol.length()), implies));
			return implies;
		}

		index = rawFormula.indexOf(Or.symbol);
		if (index != -1) {
			Or or = new Or();
			or.setFirstChild((Logic) parse(rawFormula.substring(0, index), or));
			or.setSecondChild((Logic) parse(rawFormula.substring(index + Or.symbol.length()), or));
			return or;
		}

		index = rawFormula.indexOf(And.symbol);
		if (index != -1) {
			And and = new And();
			and.setFirstChild((Logic) parse(rawFormula.substring(0, index), and));
			and.setSecondChild((Logic) parse(rawFormula.substring(index + And.symbol.length()), and));
			return and;
		}

		index = rawFormula.indexOf(Not.symbol);
		if (index != -1) {
			// TODO: index should be '0' or there is formula that is being skipped
			Not not = new Not();
			not.setChild((Logic) parse(rawFormula.substring(index + Not.symbol.length()), not));
			return not;
		}

		index = rawFormula.indexOf(EqualTo.symbol);
		if (index != -1) {
			EqualTo equalTo = new EqualTo();
			equalTo.setFirstChild((Math) parse(rawFormula.substring(0, index), equalTo));
			equalTo.setSecondChild((Math) parse(rawFormula.substring(index + EqualTo.symbol.length()), equalTo));
			return equalTo;
		}

		index = rawFormula.indexOf(Addition.symbol);
		if (index != -1) {
			Addition add = new Addition();
			add.setFirstChild((Math) parse(rawFormula.substring(0, index), add));
			add.setSecondChild((Math) parse(rawFormula.substring(index + Addition.symbol.length()), add));
			return add;
		}

		// no other symbol matches at this point
		// look for constant and variable matches
		Pattern varPattern = Pattern.compile("([0-9]*)([A-Za-z][0-9]*)");
		Matcher m = varPattern.matcher(rawFormula);
		if (m.find()) {
			String factorString = m.group(1);
			Variable var;
			if (factorString.isEmpty())
				var = new Variable(1, m.group(2));
			else {
				int factor = Integer.parseInt(factorString);
				var = new Variable(factor, m.group(2));
			}
			return var;
		}

		int number = Integer.parseInt(rawFormula);
		Constant constant = new Constant(number);
		return constant;
	}
}
