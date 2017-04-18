package parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import engine.Simplifier;
import engine.VariableReplacer;
import graph.Formula;
import graph.VariableAssignment;

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

			Parser p = new Parser();
			Formula root = p.parse(line);
			System.out.println("parsed as: "+root);

			Formula simplifiedGraphRoot = new Simplifier(root).go();
			System.out.println("simplified the passed graph, result: "+simplifiedGraphRoot);

			VariableAssignment assignment =
					new VariableAssignment()
					.put("x", 1)
					.put("y", 1);
			Formula assignedRoot = new VariableReplacer(simplifiedGraphRoot, assignment).go();
			System.out.println("replacing variables with the assignment "+assignment+" result: "+assignedRoot);
			Formula simplifiedAssignedGraphRoot = new Simplifier(simplifiedGraphRoot).go();
			System.out.println("simplified the assigned graph, result: "+simplifiedAssignedGraphRoot);
			simplifiedAssignedGraphRoot = new Simplifier(simplifiedAssignedGraphRoot).go();
			System.out.println("simplified again, result: "+simplifiedAssignedGraphRoot);
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
