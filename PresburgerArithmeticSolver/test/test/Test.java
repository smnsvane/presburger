package test;

import static org.junit.Assert.*;

import parser.Parser;

public class Test {

	private Parser p = new Parser();

	@org.junit.Test
	public void testFormula01() {
		String simplifiedFormula = "~(0 = x + 1)".replaceAll("[\\s()]", "");
		String parsing = p.parseLogic(simplifiedFormula, null).toString();
		assertEquals("~0=x+1", parsing);
	}

	@org.junit.Test
	public void testFormula02() {
		String simplifiedFormula = "x + 1 = y + 1 -> x = y".replaceAll("[\\s()]", "");
		String parsing = p.parseLogic(simplifiedFormula, null).toString();
		assertEquals("x+1=y+1->x=y", parsing);
	}

	@org.junit.Test
	public void testFormula03() {
		String simplifiedFormula = "~(0 = x + 1)".replaceAll("[\\s()]", "");
		String parsing = p.parseLogic(simplifiedFormula, null).toString();
		assertEquals("~0=x+1", parsing);
	}

	@org.junit.Test
	public void testFormula04() {
		String simplifiedFormula = "x + 0 = x".replaceAll("[\\s()]", "");
		String parsing = p.parseLogic(simplifiedFormula, null).toString();
		assertEquals("x+0=x", parsing);
	}

	@org.junit.Test
	public void testFormula05() {
		String simplifiedFormula = "x + (y + 1) = (x + y) + 1".replaceAll("[\\s()]", "");
		String parsing = p.parseLogic(simplifiedFormula, null).toString();
		assertEquals("x+y+1=x+y+1", parsing);
	}

	@org.junit.Test
	public void testFormula06() {
		String simplifiedFormula = "(y + y = x) / (y + y + 1 = x)".replaceAll("[\\s()]", "");
		String parsing = p.parseLogic(simplifiedFormula, null).toString();
		assertEquals("y+y=x/y+y+1=x", parsing);
	}

	@org.junit.Test
	public void testFormula07() {
		String simplifiedFormula = "Ax. Ey. x + 1 = 2y & x > 0 / y < 2".replaceAll("[\\s()]", "");
		String parsing = p.parseLogic(simplifiedFormula, null).toString();
		assertEquals("Ax.Ey.x+1=2y&x>0/y<2", parsing);
	}

	@org.junit.Test
	public void testFormula08() {
		String simplifiedFormula = "10 > 11 / 3 + 4 < 15 - 6".replaceAll("[\\s()]", "");
		String parsing = p.parseLogic(simplifiedFormula, null).toString();
		assertEquals("10>11/3+4<15-6", parsing);
	}

	@org.junit.Test
	public void testFormula09() {
		String simplifiedFormula = "Ax. Ey. x + 1 = 2y & x > 0 / y < 2".replaceAll("[\\s()]", "");
		String parsing = p.parseLogic(simplifiedFormula, null).toString();
		assertEquals("Ax.Ey.x+1=2y&x>0/y<2", parsing);
	}

	@org.junit.Test
	public void testFormula10() {
		String simplifiedFormula = "10 > 11 / 3 + 4 < 5 * 3 - 6".replaceAll("[\\s()]", "");
		String parsing = p.parseLogic(simplifiedFormula, null).toString();
		assertEquals("10>11/3+4<5*3-6", parsing);
	}

	@org.junit.Test
	public void testFormula11() {
		String simplifiedFormula = "Ex. (3 < x & x + 2y <= 6 & y < 0)".replaceAll("[\\s()]", "");
		String parsing = p.parseLogic(simplifiedFormula, null).toString();
		assertEquals("Ex.3<x&x+2y<=6&y<0", parsing);
	}

	@org.junit.Test
	public void testFormula12() {
		String simplifiedFormula = "3 < x & x +2y <= 6 & y < 0".replaceAll("[\\s()]", "");
		String parsing = p.parseLogic(simplifiedFormula, null).toString();
		assertEquals("3<x&x+2y<=6&y<0", parsing);
	}
}
