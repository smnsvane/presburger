package parser;

import graph.Branch;
import graph.Node;
import graph.logic.Not;
import graph.logic.binary.And;
import graph.logic.binary.BinaryLogicOperator;
import graph.logic.binary.Implies;
import graph.logic.binary.Or;
import graph.math.comparator.EqualTo;
import graph.math.quantifier.Exists;
import graph.math.quantifier.Forall;

public class Parser {

	public void parse(String rawFormula) {
		find(new Forall(null, null), rawFormula);
		find(new Exists(null, null), rawFormula);
		find(new Implies(null), rawFormula);
		find(new Or(null), rawFormula);
		find(new And(null), rawFormula);
		find(new Not(null), rawFormula);
		find(new EqualTo(null), rawFormula);
	}

	private Branch root = null;
	private Branch workingParent = null;
	private void putNewNode(Node n) {
		if (root == null) {
			root = (Branch) n;
			workingParent = root;
		} else {
			
		}
	}
	
	public void find(Node n, String line) { find(n, line, 0); }
	public void find(Node n, String line, int offset) {
		int index = line.indexOf(n.getIdentifier(), offset);
		if (index != -1) {
			System.out.println(n.getClass().getSimpleName()+" at index "+index);
			if (n instanceof BinaryLogicOperator) // and / or / implies
				n.getClass().getConstructors()[0].newInstance(arg0)
			find(n, line, index + 1);
		}
	}
}
