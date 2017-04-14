package parser;

import graph.Node;
import graph.logic.binary.And;
import graph.logic.binary.Implies;
import graph.logic.binary.Or;
import graph.math.comparator.EqualTo;

public class Parser {

	public void parse(String rawFormula) {
		find(new Implies(null), rawFormula);
		find(new Or(null), rawFormula);
		find(new And(null), rawFormula);
		find(new EqualTo(null), rawFormula);
	}
	
	public void find(Node n, String line) { find(n, line, 0); }
	public void find(Node n, String line, int offset) {
		int index = line.indexOf(n.getIdentifier(), offset);
		if (index != -1) {
			System.out.println("found "+n.getClass()+" at index "+index);
			find(n, line, index + 1);
		}
	}
}
