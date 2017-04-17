package engine;

import java.util.ArrayList;

import graph.Branch;
import graph.Node;

public abstract class Engine {

	private ArrayList<Branch<Node>> unvisited = new ArrayList<>();
	private Branch<Node> current;

	private Branch<Node> root;
	public Branch<Node> getRoot() { return root; }
	public Engine(Branch<Node> root) {
		this.root = root;
		unvisited.add(root);
	}

	private boolean readyToGiveNext = true;

	public boolean hasNext() {
		return !unvisited.isEmpty();
	}
	public Branch<Node> next() {
		if (!readyToGiveNext)
			throw new RuntimeException("done() should be called before next() (except at first call to next())");
		current = unvisited.remove(0);
		readyToGiveNext = false;
		current.unlock();
		return current;
	}
	@SuppressWarnings("unchecked")
	public void done() {
		current.lock();
		for (Node child : current)
			if (child instanceof Branch<?>)
				unvisited.add((Branch<Node>) child);
		readyToGiveNext = true;
	}
}
