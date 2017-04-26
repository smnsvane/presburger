package graph.formula.comparator;

import java.util.ArrayList;

import graph.Formula;
import graph.Term;
import graph.VariableAssignment;
import graph.term.Constant;
import graph.term.Sum;
import graph.term.Variable;

public class LessThan extends Comparator {

	public LessThan(Term child1, Term child2) { super(child1, child2); }
	@Override
	public boolean evaluate(VariableAssignment varAss) {
		return getFirstChild().evaluate(varAss) <
				getSecondChild().evaluate(varAss);
	}
	@Override
	public Formula negate() {
		GreaterThanOrEqualTo greaterOrEqual = new GreaterThanOrEqualTo(getFirstChild(), getSecondChild());
		return greaterOrEqual;
	}
	@Override
	public LessThan toLessThan() { return this; }
	@Override
	public LessThan isolate() {
		LessThan less = new LessThan(new Constant(0), getSecondChild().toSum().sumDiff(getFirstChild().toSum()));
		return less;
	}
	@Override
	public LessThan copy() { return new LessThan(getFirstChild().copy(), getSecondChild().copy()); }
	public LessThan isolate(String variableSymbol) {
		ArrayList<Term> var = new ArrayList<>();
		ArrayList<Term> nonVar = new ArrayList<>();
		//calling .toSum() in order to be able to iterate over the child's children
		for (Term t1 : getFirstChild().toSum())
			if (t1 instanceof Variable) {
				Variable v = (Variable) t1;
				if (v.getVariableSymbol().equals(variableSymbol))
					var.add(v);
				else
					nonVar.add(v.multiply(-1));
			} else
				nonVar.add(t1.multiply(-1));
		for (Term t2 : getSecondChild().toSum())
			if (t2 instanceof Variable) {
				Variable v = (Variable) t2;
				if (v.getVariableSymbol().equals(variableSymbol))
					var.add(v.multiply(-1));
				else
					nonVar.add(v);
			} else
				nonVar.add(t2);
		Variable v = (Variable) new Sum(var).compact().flatten();
		Sum nonVarSum = new Sum(nonVar);
		LessThan less;
		if (v.getFactor() < 0)
			less = new LessThan(nonVarSum.multiply(-1), v.multiply(-1));
		else
			less = new LessThan(v, nonVarSum);
		return less;
	}
}
