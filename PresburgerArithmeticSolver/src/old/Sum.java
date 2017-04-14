package old;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Sum implements MathEval
{
	public List<Number> numbers = new ArrayList<Number>();
	public void add(Number math)
	{
		int index = numbers.indexOf(math);
		if (index == -1)
			numbers.add(math);
		else
			numbers.get(index).constant += math.constant;
	}
	public Number remove(String variable)
	{
//		int index = numbers.indexOf(math);
		int index=0;//??
		if (index == -1)
			return null;
		return numbers.remove(index);
	}
	public void multiply(int constant)
	{
		for (Number n : numbers)
			n.constant *= constant;
	}
	@Override
	public double eval(Map<String, Integer> map) {
		// TODO Auto-generated method stub
		return 0;
	}
}
