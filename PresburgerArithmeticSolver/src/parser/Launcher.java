package parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

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
			System.out.println("parsed as: "+p.parseLogic(line, null));
		}
	}
}
