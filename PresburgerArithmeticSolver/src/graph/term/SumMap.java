package graph.term;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import graph.Branch;
import graph.Term;
import graph.VariableAssignment;

public class SumMap extends Branch<Term> implements Term {

	private HashMap<String, Integer> varSymbolToFactor = new HashMap<>();

	public SumMap(Collection<Term> children) {
		for (Term t : children)
			if (t instanceof Constant)
				varSymbolToFactor.put(null, ((Constant) t).getValue() + varSymbolToFactor.get(null));
			else if (t instanceof Variable) {
				Variable v = (Variable) t;
				varSymbolToFactor.put(v.getVariableSymbol(), v.getFactor() + varSymbolToFactor.get(v.getVariableSymbol()));
			} else
				throw new RuntimeException("Only "+Constant.class.getSimpleName()+" and "+
						Variable.class.getSimpleName()+" allowed");
	}

	@Override
	public void validate() {}

	@Override
	public Iterator<Term> iterator() {
		return new Iterator<Term>() {
			Iterator<String> it = varSymbolToFactor.keySet().iterator();
			@Override
			public Term next() {
				String nextSymbol = it.next();
				if (nextSymbol == null)
					return new Constant(varSymbolToFactor.get(null));
				return new Variable(varSymbolToFactor.get(nextSymbol), nextSymbol);
			}
			@Override
			public boolean hasNext() { return it.hasNext(); }
		};
	}

	@Override
	public int evaluate(VariableAssignment assignment) {
		int value = varSymbolToFactor.get(null);
		for (String varSymbol : varSymbolToFactor.keySet())
			if (assignment.hasAssignment(varSymbol))
				value += varSymbolToFactor.get(varSymbol) * assignment.getAssignment(varSymbol);
			else
				throw new RuntimeException("missing variable assignment for "+varSymbol);
		return value;
	}

	@Override
	public Sum toSum() {
		throw new RuntimeException("not implemented");
	}

	@Override
	public Term multiply(int factor) {
		ArrayList<Term> children = new ArrayList<>();
		for (Term t : this)
			children.add(t.multiply(factor));
		return new SumMap(children);
	}

	@Override
	public SumMap copy() { return new SumMap(getChildren()); }

	@Override
	public List<Term> getChildren() {
		ArrayList<Term> children = new ArrayList<>();
		for (Term t : this)
			children.add(t);
		return children;
	}

	@Override
	public void replaceChild(Term victim, Term overtaker) {
		removeChild(victim);
		addChild(overtaker);
	}
	public void removeChild(Term victim) {
		if (isLocked())
			throw new RuntimeException("locked");
		if (victim instanceof Constant)
			if (varSymbolToFactor.get(null) == ((Constant) victim).getValue())
				varSymbolToFactor.remove(null);
			else
				throw new RuntimeException("can't find victim "+victim);
		else if (victim instanceof Variable) {
			Variable v = (Variable) victim;
			if (varSymbolToFactor.get(v.getVariableSymbol()) == v.getFactor())
				varSymbolToFactor.remove(v.getVariableSymbol());
		} else
			throw new RuntimeException("Only "+Constant.class.getSimpleName()+" and "+
					Variable.class.getSimpleName()+" allowed");
	}
	public void addChild(Term child) {
		if (isLocked())
			throw new RuntimeException("locked");
		String symbol = null;
		int factor;
		if (child instanceof Variable) {
			Variable v = (Variable) child;
			symbol = v.getVariableSymbol();
			factor = v.getFactor();
		} else if (child instanceof Constant)
			factor = ((Constant) child).getValue();
		else
			throw new RuntimeException("Only "+Constant.class.getSimpleName()+" and "+
					Variable.class.getSimpleName()+" allowed");
		
		varSymbolToFactor.put(symbol, varSymbolToFactor.get(symbol) + factor);
	}
	public Term getChild(String varSymbol) {
		int factor = varSymbolToFactor.get(varSymbol);
		if (factor == 0)
			throw new RuntimeException("could not find child");
		if (varSymbol == null)
			return new Constant(factor);
		return new Variable(factor, varSymbol);
	}
	@Override
	public String toString() { return "SUM_MAP"+getChildren(); }
}
