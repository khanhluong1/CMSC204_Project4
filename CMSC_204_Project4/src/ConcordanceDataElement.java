import java.util.LinkedList;

/**
 * This class is the data element class for the ConcordanceDataStructure 
 * It consists of a word (String) and a list of page numbers (LinkedList)
 * 
 * @author Derek Luong
 *
 */
public class ConcordanceDataElement implements Comparable<ConcordanceDataElement>{

	private String word;
	private LinkedList<Integer> lineNumbers;
	
	public ConcordanceDataElement(String word) {
		this.word = word;
		lineNumbers = new LinkedList<Integer>();
	}
	
	/**
	 * Add a line number to the linked list if the number doesn't exist in the list
	 * 
	 * @param lineNum - the line number to add to the linked list
	 */
	public void addPage(int lineNum) {
		if (!lineNumbers.contains(lineNum)) {
			lineNumbers.add(lineNum);
		}
	}
	
	@Override
	public int compareTo(ConcordanceDataElement o) {
		if (o == null) {
			return -1;
		}
		return this.word.compareTo(o.getWord());
	}
	
	/**
	 * Returns the hashCode. You may use the String class hashCode method 
	 */
	@Override
	public int hashCode() {
		return word.hashCode();
	}
	
	@Override
	public boolean equals(Object other) {
		if (other == null || !(other instanceof ConcordanceDataElement)) {
			return false;
		}
		ConcordanceDataElement otherElement = (ConcordanceDataElement) other;
		if (word.equals(otherElement.getWord())) {
			return true;
		}
		return false;
	}
	
	/**
	 * Returns the word followed by page numbers Returns a string in the following format: word: page num, page num Example: after: 2,8,15
	 */
	@Override
	public String toString() {
		String result = word + ": ";
		for (int i = 0; i < lineNumbers.size(); i++) {
			result += lineNumbers.get(i) + ",";
		}
		return result.substring(0, result.length() - 1);
	}

	/**
	 * 
	 * @return the word portion of the Concordance Data Element
	 */
	public String getWord() {
		return word;
	}

	public LinkedList<Integer> getLineNumbers() {
		return lineNumbers;
	}
	
}
