package parser;

import java.util.Comparator;

import graph.Node;
import graph.term.Constant;
import graph.term.Variable;

public class NodeSorter implements Comparator<Node> {

	@Override
	public int compare(Node n1, Node n2) {

		if (n1 instanceof Variable && n2 instanceof Constant)
			return -1;

		if (n1 instanceof Constant && n2 instanceof Variable)
			return 1;

		if (n1 instanceof Constant && n2 instanceof Constant) {
			Constant c1 = (Constant) n1;
			Constant c2 = (Constant) n2;
			return c1.getValue() - c2.getValue();
		}

		if (n1 instanceof Variable && n2 instanceof Variable) {
			Variable v1 = (Variable) n1;
			Variable v2 = (Variable) n2;

			if (v1.getVariableSymbol().equals(v2.getVariableSymbol()))
				return v1.getFactor() - v2.getFactor();

			return v1.getVariableSymbol().compareTo(v2.getVariableSymbol());
		}

		return n1.getSymbol().compareTo(n2.getSymbol());
	}
}
