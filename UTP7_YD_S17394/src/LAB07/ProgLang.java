package LAB07;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ProgLang {

 private Map<String, Set<String>> progMap = new LinkedHashMap<String, Set<String>>();
 private Map<String, Set<String>> langMap = new LinkedHashMap<String, Set<String>>();
 private BufferedReader br;
 
 public ProgLang(String fileName) {
  readFile(fileName);
 }
 
 public void readFile(String fileName) {
  try {
   br = new BufferedReader(new FileReader(fileName));
   String linia;
   while ((linia = br.readLine()) != null)
    put(linia);
  } catch (Exception ex) {
   ex.printStackTrace();
  }
 }
 
 private void put(String line) {
  String[] list = line.split("\t");
  for (int i = 0; i < list.length; i++) {
   String s = list[i];
   if (i == 0) {
    if (langMap.getOrDefault(s, null) != null) {
     
    } else {
     Set<String> l = new LinkedHashSet<>();
     langMap.put(s, l);
    }
    
   } else {
    if (langMap.getOrDefault(list[0], null) != null) {
     Set<String> l= langMap.get(list[0]);
     l.add(s);
    } 
    if (progMap.getOrDefault(s, null) != null) {
     Set<String> l = progMap.get(s);
     l.add(list[0]);
    } else {
     Set<String> l = new LinkedHashSet<>();
     l.add(list[0]);
     this.progMap.put(s, l);
    }
   }
  }
 }
 
 public Map<String, Set<String>> getLangsMapSortedByNumOfProgs () {
  return langMap.entrySet().stream()
    .sorted(Map.Entry.comparingByKey((k1, k2) -> k1.compareTo(k2)))
    .sorted(Map.Entry.comparingByValue((v1,v2) -> v2.size() - v1.size()))
    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
    (oldValue, newValue) -> oldValue, LinkedHashMap::new));
 }
 
 public Map<String, Set<String>> getProgsMapSortedByNumOfLangs () {
  return progMap.entrySet().stream()
    .sorted(Map.Entry.comparingByKey((k1, k2) -> k1.compareTo(k2)))
    .sorted(Map.Entry.comparingByValue((v1,v2) -> v2.size() - v1.size()))
    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
    (oldValue, newValue) -> oldValue, LinkedHashMap::new));
 }
 
 public Map<String, Set<String>>  getProgsMapForNumOfLangsGreaterThan(int threshold) {
  return progMap.entrySet().stream()
    .filter(row -> row.getValue().size() > threshold)
    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
    (oldValue, newValue) -> oldValue, LinkedHashMap::new));
 }
 
 public Map<String, Set<String>> getLangsMap() {
  return langMap; 
 }
 
 public Map<String, Set<String>> getProgsMap() {
  return progMap; 
 }
 
 public static <K,V>  Map<K, V> sorted(Map<K,V> srcMap, Comparator<Map.Entry<K,V>> comp) {
	 return srcMap.entrySet().stream()
			    .sorted(comp)
			    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
			    (oldValue, newValue) -> oldValue, LinkedHashMap::new));
 }
 
 public static <K,V>  Map<K, V> filtered(Map<K,V> srcMap, Predicate<Map.Entry<K,V>> p) {
	 return srcMap.entrySet().stream()
    		 				.filter(p)
    		 				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
    		    (oldValue, newValue) -> oldValue, LinkedHashMap::new));
 }

}