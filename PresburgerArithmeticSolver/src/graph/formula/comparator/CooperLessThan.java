package graph.formula.comparator;


import java.util.ListIterator;

import graph.Formula;
import graph.Term;
import graph.TwoChildrenBranch;
import graph.VariableAssignment;
import graph.formula.False;
import graph.formula.True;
import graph.term.Constant;
import graph.term.Sum;
import graph.term.Variable;

public class CooperLessThan extends TwoChildrenBranch<Sum, Sum> implements Formula {

	public CooperLessThan(Sum child1, Sum child2) { super(child1, child2); }
	@Override
	public boolean evaluate(VariableAssignment varAss) {
		return getFirstChild().evaluate(varAss) <
				getSecondChild().evaluate(varAss);
	}
	@Override
	public CooperLessThan negate() {
		CooperLessThan less = new CooperLessThan(getSecondChild(), getFirstChild().add(new Constant(1)));
		return less;
	}
	@Override
	public CooperLessThan copy() { return new CooperLessThan(getFirstChild().copy(), getSecondChild().copy()); }

	public CooperLessThan isolate(String variableSymbol) {

		int varFactor = 0;
		Sum nonVar = getSecondChild().add(getFirstChild().multiply(-1).getChildren());
		nonVar.unlock();
		for (ListIterator<Term> it = nonVar.iterator(); it.hasNext();) {
			Term t = it.next();
			if (t instanceof Variable) {
				Variable v = (Variable) t;
				if (v.getVariableSymbol().equals(variableSymbol)) {
					it.remove();
					varFactor -= v.getFactor(); // to account for the -1 when it was moved to nonVar
				}
			}
		}
		nonVar.lock();

		if (varFactor == 0)
			return new CooperLessThan(new Sum(), nonVar);

		if (varFactor < 0) {
			varFactor *= -1;
			nonVar = nonVar.multiply(-1);
			CooperLessThan less = new CooperLessThan(nonVar, new Sum(new Variable(varFactor, variableSymbol)));
			return less;
		}

		CooperLessThan less = new CooperLessThan(new Sum(new Variable(varFactor, variableSymbol)), nonVar);
		return less;
	}
	@Override
	public Formula reduce() {
		try {
			if (evaluate(null))
				return new True();
			return new False();
		} catch (NullPointerException e) {
			return this;
		}
	}
}
