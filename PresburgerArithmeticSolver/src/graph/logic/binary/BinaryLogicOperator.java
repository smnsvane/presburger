package graph.logic.binary;

import graph.BinaryBranch;
import graph.logic.Logic;

import java.util.Iterator;

public abstract class BinaryLogicOperator implements BinaryBranch<Logic>, Logic {

	private Logic child1, child2;
	@Override
	public Logic getFirstChild() { return child1; }
	@Override
	public Logic getSecondChild() { return child2; }
	@Override
	public void setFirstChild(Logic child) { child1 = child; }
	@Override
	public void setSecondChild(Logic child) { child2 = child; }

	@Override
	public Iterator<Logic> iterator() {
		return new Iterator<Logic>() {
			int nextCount = 0;
			@Override
			public Logic next() {
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
