package graph.math.comparator;

import java.util.Iterator;

import graph.BinaryBranch;
import graph.logic.Logic;
import graph.math.Math;

public abstract class Comparator implements BinaryBranch<Math>, Logic {

	private Math child1, child2;
	@Override
	public Math getFirstChild() { return child1; }
	@Override
	public Math getSecondChild() { return child2; }
	@Override
	public void setFirstChild(Math child) { child1 = child; }
	@Override
	public void setSecondChild(Math child) { child2 = child; }

	@Override
	public Iterator<Math> iterator() {
		return new Iterator<Math>() {
			int nextCount = 0;
			@Override
			public Math next() {
				nextCount++;
				switch (nextCount) {
				case 1:
					return child1;
				case 2:
					return child2;
				default:
					throw new RuntimeException("Ran out of children");
				}
			}
			@Override
			public boolean hasNext() { return nextCount < 2; }
		};
	}
}
