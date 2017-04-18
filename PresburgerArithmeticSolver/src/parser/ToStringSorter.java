package parser;

import java.util.Comparator;

public class ToStringSorter implements Comparator<Object> {

	@Override
	public int compare(Object n1, Object n2) {
		return n2.toString().compareTo(n1.toString());
	}
}
