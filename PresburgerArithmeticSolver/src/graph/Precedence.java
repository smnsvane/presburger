package graph;

import java.util.ArrayList;

import graph.logic.Not;
import graph.logic.binary.And;
import graph.logic.binary.Or;
import graph.math.binary.Addition;
import graph.math.binary.Subtraction;
import graph.math.comparator.EqualTo;
import graph.math.comparator.GreaterThan;
import graph.math.comparator.GreaterThanOrEqualTo;
import graph.math.comparator.LessThan;
import graph.math.comparator.LessThanOrEqualTo;
import graph.math.comparator.NotEqualTo;
import graph.math.quantifier.Exists;
import graph.math.quantifier.Forall;

public class Precedence {

	public static ArrayList<ArrayList<Class<? extends Node>>> order = new ArrayList<>();

	static {
		addPresendenceGroup(Exists.class, Forall.class);
		addPresendenceGroup(Or.class);
		addPresendenceGroup(And.class);
		addPresendenceGroup(Not.class);
		addPresendenceGroup(EqualTo.class, GreaterThan.class,
				GreaterThanOrEqualTo.class, LessThan.class,
				LessThanOrEqualTo.class, NotEqualTo.class);
		addPresendenceGroup(Addition.class, Subtraction.class);
	}

	@SafeVarargs
	public static final void addPresendenceGroup(Class<? extends Node>...group) {
		ArrayList<Class<? extends Node>> set = new ArrayList<>();
		for (Class<? extends Node> c : group)
			set.add(c);
		order.add(set);
	}
}
