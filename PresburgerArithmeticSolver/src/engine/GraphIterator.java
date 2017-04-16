package engine;

import java.util.ArrayList;
import java.util.Iterator;

import graph.Branch;
import graph.Node;
import graph.SingleChildBranch;
import graph.TwoChildrenBranch;
import graph.formula.Formula;

public class GraphIterator implements Iterable<Node> {

	private int index = 0;
	private ArrayList<Node> list = new ArrayList<>();
	private Node getLast() { return list.get(index-1); }
	private Branch parent = null;
	public Branch getParent() { return parent; }
	public GraphIterator(Formula root) { list.add(root); }
	private void refillIterator(Branch branch) {
		parent = branch;
		index = 0;
		list.clear();
		if (branch instanceof SingleChildBranch<?>) {
			SingleChildBranch<?> last = (SingleChildBranch<?>) branch;
			list.add(last.getChild());
		}
		if (branch instanceof TwoChildrenBranch<?, ?>) {
			TwoChildrenBranch<?, ?> last = (TwoChildrenBranch<?, ?>) branch;
			list.add(last.getFirstChild());
			list.add(last.getSecondChild());
		}
	}

	@Override
	public Iterator<Node> iterator() {
		return new Iterator<Node>() {
			@Override
			public boolean hasNext() {
				if (index < list.size())
					return true;
				return getLast() instanceof Branch;
			}
			@Override
			public Node next() {
				if (index < list.size())
					return list.get(index++);
				if (getLast() instanceof Branch)
					refillIterator((Branch) getLast());
				return next();
			}
		};
	}
}
