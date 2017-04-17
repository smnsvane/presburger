package graph;

public interface Node {

	boolean equals(Object obj);
	String getSymbol();
	Node simplify();
	Node copy();
}
