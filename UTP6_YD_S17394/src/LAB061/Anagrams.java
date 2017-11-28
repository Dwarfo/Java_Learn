/**
 *
 *  @author Yelchaninov Denys S17394
 *
 */

package LAB061;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

import LAB062.Purchase;

public class Anagrams {
	
	private List<String> words = new ArrayList<>();
	
	private Map<String, List<String>> anagramy = new HashMap<String, List<String>>();
	
	public Anagrams (String fileName) {
		try {
			Scanner sc = new Scanner(new FileReader(fileName));
			while (sc.hasNext()) {
				String word = sc.next();
				String keySort = keySort(word);
				if (anagramy.getOrDefault(keySort, null) != null) {
					List<String> list = anagramy.get(keySort);
					list.add(word);
				} else {
					List<String> list = new ArrayList<>();
					list.add(word);
					anagramy.put(keySort, list);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public List<List<String>> getSortedByAnQty() {
		List<List<String>> anagramList = new ArrayList<>();
		anagramy.forEach((key,val) -> anagramList.add(val));
		anagramList.sort((e1,e2) -> {
			return e2.size() - e1.size();
		});
		return anagramList;
	}

	public String getAnagramsFor(String word) {
		String keySort = keySort(word);
		if (anagramy.getOrDefault(keySort, null) != null) {
			return word + ": " + anagramy.getOrDefault(keySort, null).stream().filter(e -> !e.equals(word)).collect(Collectors.toList()).toString();
		} else {
			return word + ": " + anagramy.getOrDefault(keySort,null);
		}
	}
	
	private String keySort(String word) {
		char[] tmp = word.toCharArray();
		Arrays.sort(tmp);
		String keySort = new String(tmp);
		return keySort;
	}
	
}  
