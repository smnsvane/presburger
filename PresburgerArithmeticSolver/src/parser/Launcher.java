package parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import graph.GraphIterator;
import graph.Node;
import graph.VariableAssignment;
import graph.formula.Formula;
import graph.term.Constant;
import graph.term.Multiply;
import graph.term.Variable;

public class Launcher {

	public static void main(String[] args) {

		Scanner scanner = null;
		try {
			scanner = new Scanner(new File("formulas.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		for (int lineNumber = 1; scanner.hasNextLine(); lineNumber++) {
			String line = scanner.nextLine();
			if (line.charAt(0) == '#')
				continue;
			System.out.println("\nformula "+lineNumber+": "+line);

			line = line.replaceAll("[\\s()]", "");
			System.out.println("shortened to \""+line+"\"");

			Parser p = new Parser();
			Formula parsedRoot = p.parseLogic(line);
			System.out.println("parsed as: "+parsedRoot);

			VariableAssignment assignment =
					new VariableAssignment()
					.put("x", 1)
					.put("y", 1);

			System.out.println("replacing variables with the assignment "+assignment);
			GraphIterator explorer = new GraphIterator(parsedRoot);
			for (Node n : explorer)
				if (n instanceof Variable) {
					Variable v = (Variable) n;

					Constant child1 = new Constant(v.factor);
					Constant child2 = new Constant(assignment.getAssignment(v.variableSymbol));

					Multiply m = new Multiply();
					m.setFirstChild(child1);
					m.setSecondChild(child2);

					explorer.getParent().replaceChild(v, m);
				}
			System.out.println("result: "+parsedRoot);
//			Engine e = new Engine();
//			boolean success = e.applyAssignment(parsedRoot, varAss);
//			System.out.println("Evaluated with the assignment "+varAss+" result was: "+success);
//			
//			varAss.addAssignment("y", null);
//			Formula partiallyAssignedRoot = (Formula) parsedRoot.replaceVariables(varAss);
//			System.out.println("Variables replaced with the assignment "+varAss+" result: "+parsedRoot);
//			
//			Formula simplified = (Formula) partiallyAssignedRoot.simplify();
//			System.out.println("Graph have been simplified, result: "+simplified);
		}
	}
}
