import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Implementation of ConcordanceDataManagerInterface
 * 
 * @author Derek Luong
 *
 */
public class ConcordanceDataManager implements ConcordanceDataManagerInterface {

	@Override
	public ArrayList<String> createConcordanceArray(String input) {
		String[] totalWords = input.split("[\\n\\s+]");
		ConcordanceDataStructure dataStructure = new ConcordanceDataStructure("Testing", totalWords.length);
		String[] lines = input.split("\\n");
		for (int i=0; i<lines.length; i++) {
			String line = lines[i];
			String[] words = line.split("\\s+");
			for (int j=0; j<words.length; j++) {
				if (isGoodWord(words[j])) {
					dataStructure.add(words[j], i+1);
				}
			}
		}
		return dataStructure.showAll();
	}

	@Override
	public boolean createConcordanceFile(File input, File output)
			throws FileNotFoundException {
		String text = "";
		Scanner scan = new Scanner(input);
		while (scan.hasNext())
		{
			text += scan.nextLine()+"\n";
		}
		scan.close();
		
		ArrayList<String> words = createConcordanceArray(text);
		PrintWriter outFile = new PrintWriter(output);
		for(int i=0; i<words.size(); i++) {
			outFile.print(words.get(i)+"\n");
		}
		outFile.close();
		return true;
	}
	
	private boolean isGoodWord(String word) {
		String[] badWords = new String[] {"to", "the", "is", "of", "a", "an", "on", "or", "by"};
		for (int i=0; i<badWords.length; i++) {
			if (badWords[i].equalsIgnoreCase(word)) {
				return false;
			}
		}
		return true;
	}

}
