import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

/**
 * This is the Concordance Data Structure Class. It is the data structure class that is used with 
 * the Concordance Data Manager class. This is a hash table with buckets. Your hash table with be an 
 * array of linked lists of ConcordanceDataElements. Use the hashcode for an ConcordanceDataElement 
 * to place in the hashtable. Do not enter duplicate words or duplicate line numbers for a word. 
 * There should be two constructors. The first one takes in an integer which represents the estimated 
 * number of words in the text. Determine the size of the table by using a loading factor of 1.5 and 
 * a 4K+3 prime. Example if you estimated 500 words, 500/1.5 = 333. The next 4K+3 prime over 333 is 347. 
 * So you would make the table a length of 347. The other constructor will take in a String and an int. 
 * The string will be "Testing" and the int will be the size of the hash table. This is used only for testing.
 * 
 * @author Derek Luong
 *
 */
public class ConcordanceDataStructure implements ConcordanceDataStructureInterface {

	private ArrayList<ConcordanceDataElement>[] table;
	
	/**
	 * This constructor takes in an integer which represents the estimated number of words in the text.
	 * 
	 * @param size
	 */
	public ConcordanceDataStructure(int size) {
		if (size < 0) {
			size = 0;
		}
		float p = size / 1.5f;
		int prime = (int) p;
		while (true) {
			if (!isPrime(prime)) {
				prime = getNextPrime(prime);
			}
			if ((prime - 3) % 4 == 0) {
				break;
			} else {
				prime = getNextPrime(prime);
			}
		}
		table = new ArrayList[prime];
	}
	
	/**
	 *  Constructor for testing purposes The string will be "Testing" and the int will be the size of the hash table.
	 *  
	 * @param text
	 * @param size
	 */
	public ConcordanceDataStructure(String text, int size) {
		table = new ArrayList[size];
	}
	
	/**
	 * get next prime greater than given number
	 * 
	 * @param number
	 * @return
	 */
	private int getNextPrime(int number) {
		for (int i=number+1; ; i++) {
			if (isPrime(i)) {
				return i;
			}
		}
	}
	
	/**
	 * check whether value is prime number. Number must be greater than 1.
	 * 
	 * @return true if prime, otherwise false
	 */
	private boolean isPrime(int value) {
		if (value < 2) {
			return false;
		}
		
		boolean result = true;
		for (int i=2; i < value; i++) {
			if (value % i == 0) {
				result = false;
				break;
			}
		}
		return result;
	}
	
	@Override
	public int getTableSize() {
		return table.length;
	}

	@Override
	public ArrayList<String> getWords(int index) {
		ArrayList<String> result = new ArrayList<String>();
		ArrayList<ConcordanceDataElement> wordList = table[index];
		if (wordList != null) {
			for (int i=0; i < wordList.size(); i++) {
				result.add(wordList.get(i).getWord());
			}
		}
		return result;
	}

	@Override
	public ArrayList<LinkedList<Integer>> getPageNumbers(int index) {
		ArrayList<LinkedList<Integer>> result = new ArrayList<LinkedList<Integer>>();
		ArrayList<ConcordanceDataElement> wordList = table[index];
		if (wordList != null) {
			for (int i=0; i < wordList.size(); i++) {
				result.add(wordList.get(i).getLineNumbers());
			}
		}
		return result;
	}

	@Override
	public void add(String word, int lineNum) {
		word = word.toLowerCase();
		word = removePunctuationAtTheEnd(word);
		ConcordanceDataElement newWord = new ConcordanceDataElement(word);
		int hashCode = newWord.hashCode();
		int ip = Math.abs(hashCode % table.length);
		ArrayList<ConcordanceDataElement> wordList = table[ip];
		if (wordList == null) {
			newWord.addPage(lineNum);
			wordList = new ArrayList<ConcordanceDataElement>();
			wordList.add(newWord);
			table[ip] = wordList;
		} else {
			int index = wordList.indexOf(newWord);
			if (index < 0) {
				// new word does not exist 
				newWord.addPage(lineNum);
				wordList.add(newWord);
			} else {
				ConcordanceDataElement existingWord = wordList.get(index);
				existingWord.addPage(lineNum);
			}
		}
	}

	@Override
	public ArrayList<String> showAll() {
		ArrayList<ConcordanceDataElement> wordList = new ArrayList<ConcordanceDataElement>();
		for (int i=0; i<table.length; i++) {
			if (table[i] != null) {
				wordList.addAll(table[i]);
			}
		}
		Collections.sort(wordList);
		ArrayList<String> result = new ArrayList<String>();
		for (int i=0; i<wordList.size(); i++) {
			result.add(wordList.get(i).toString());
		}
		return result;
	}
	
	private String removePunctuationAtTheEnd(String word) {
		String result = "";
		char lastChar = word.charAt(word.length() - 1);
		if(!Character.isLetterOrDigit(lastChar)) {
			result = word.substring(0, word.length() - 1);
		} else {
			result = word;
		}
		return result;
	}
	
	public static void main(String[] args) {
		System.out.println("cucumber".hashCode()%20);
//		int result[] = new int[19];
//		int N = 19;
//		int prime = 23;
//		int[] numbers = new int[] {25, 44, 126, 91, 53, 150, 544, 525, 481, 123, 621, 735, 566, 223, 66};
//		for (int i=0; i<numbers.length; i++) {
//			int num = numbers[i];
//			int ip = num % N;
//			int q = num / N;
//			int offset = 0;
//		    if (q%N != 0)
//		        offset = q;
//		    else
//		        offset = prime;
//		    
//		    int pass = 0;
//		    while (pass < N) {
//		    	if (result[ip] == 0) {
//		    		result[ip] = num;
//		    		break;
//		    	} else {
//		    		ip = (ip + offset) % N;
//		    		pass++;
//		    	}
//		    }
//		    System.out.println(num + "==>" + pass);
//
//		}
//		System.out.println("=======================");
//		for (int i=0; i<result.length; i++) {
//			System.out.println(i + "=" + result[i]);
//		}
	}

}
