package greTopics;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class TopicGenerator {

	/**
	 * Prints a list of issue or argument essay topics for the GRE
	 * 
	 * @param type - string type of either issue or argument
	 * @return list of all topics
	 * @throws FileNotFoundException - invalid input (not issue or argument)
	 */
	public static ArrayList<String> topicList(String type) throws FileNotFoundException {
		File file = null;
		// want to give the option to have issue or argument (i or a)
		if ((type.toLowerCase().contains("issue") && !type.toLowerCase().contains("argument"))
				|| type.toLowerCase().equals("i")) {
			file = new File("src/greTopics/issue.txt");
		} else if ((type.toLowerCase().contains("argument") && !type.toLowerCase().contains("issue"))
				|| type.toLowerCase().equals("a")) {
			file = new File("src/greTopics/argument.txt");
		} else {
			throw new FileNotFoundException("Must specify \"issue\" or \"argument\"");
		}
		Scanner scr = new Scanner(file);
		ArrayList<String> list = new ArrayList<>();
		String s = "";
		while (scr.hasNextLine()) {
			String line = scr.nextLine();
			// lot of empty lines
			if (line.equals("")) {
				continue;
			}
			// every topic ends with a paragraph beginning with "Write".
			if (line.substring(0, 5).equals("Write")) {
				s += "\n" + line;
				list.add(s);
				// reset for the loop
				s = "";
			} else {
				s += line + "\n";
			}
		}
		scr.close();
		return list;
	}

	/**
	 * Generates a random topic out of the entire selection for either the issue or
	 * the argument essay.
	 * 
	 * @param type - string type of either issue or argument
	 * @return random topic
	 * @throws FileNotFoundException - invalid input (not issue or argument)
	 */
	public static String generateTopic(String type) throws FileNotFoundException {
		Random rng = new Random();
		ArrayList<String> list = topicList(type);
		return list.get(rng.nextInt(list.size()));
	}

	/**
	 * Generates multiple topics at once (no repeats)
	 * 
	 * @param type   - string type of either issue or argument
	 * @param amount - number of topics to generate
	 * @return random topic
	 * @throws FileNotFoundException    - invalid input (not issue or argument)
	 * @throws IllegalArgumentException - too many topics or not enough topics (less
	 *                                  than 1)
	 */
	public static String generateTopic(String type, int amount) throws FileNotFoundException {
		ArrayList<String> list = topicList(type);
		// want to make sure there is not too many topics to generate or too little.
		if (amount > list.size()) {
			throw new IllegalArgumentException(
					"Too many topics. The maximum amount of topics you can generate is " + list.size());
		}
		if (amount < 1) {
			throw new IllegalArgumentException("Must generate at least one topic");
		}
		Random rng = new Random();
		String s = "";
		// making string consisting of all the topics numbered
		for (int i = 1; i <= amount; i++) {
			int topicIndex = rng.nextInt(list.size());
			s += "(" + i + ") " + list.get(topicIndex);
			list.remove(topicIndex);
			s += "\n\n\n\n";
		}
		return s;
	}

	public static void main(String[] args) throws FileNotFoundException {
		System.out.println(generateTopic("argument"));
	}
}
