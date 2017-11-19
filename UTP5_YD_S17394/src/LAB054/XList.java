package LAB054;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public class XList <T> extends ArrayList{
	
	public XList(T... args) {
		this.addAll(Arrays.asList(args));
	}
	
	public XList(Collection col) {
		this.addAll(col);
	}
	
	public static <T> XList<T> of(Object...args) {
		XList list = new XList(Arrays.asList(args));
		return list;
	}
	public static <T> XList<T> of(Set set) {
		XList list = new XList(set);
		return list;
	}

	public static <T> XList<T> charsOf(String str) {
	
		Character[] characters = str.chars().mapToObj(c -> (char)c).toArray(Character[]::new); 
		XList charList = new XList(characters);
		
		return charList;
	}

	public static <T> XList<T> tokensOf(String str) {
		
		XList tokenedList = new XList(str.split("\\s"));
		return tokenedList;
	}
	
	public static <T> XList<T> tokensOf(String str, String token) {

		XList tokenedList = new XList(str.split(token));
		return tokenedList;
	}

	public XList<T> union(Collection<T> list2) {
		XList<T> newList = new XList<>(this);
		boolean a = newList.addAll(list2);
		return newList;
	}
	public XList<T> union(T...args) {
		XList<T> newList = new XList<>(this);
		boolean a = newList.addAll(Arrays.asList(args));
		return newList;
	}

	public XList diff(Collection m3) {
		
		XList<T> newList = new XList<>(this);
		boolean a = newList.removeAll(m3);
		return newList;
	}

	public XList unique() {
		XList uniqueList = new XList(this);
		
		for(int i = 0; i < uniqueList.size() ; i++) {
			Object o1 = uniqueList.get(i);
			for(int j=0;j<uniqueList.size();j++)
			{
				Object o2 = uniqueList.get(j);
				if(i != j)
					if(o1.equals(o2)) {
						uniqueList.remove(j);
					}
			}
		}
		return uniqueList;
	}

	public XList combine() {
		
		XList<XList> newList = new XList();
		int iteratorSize = 12;
		/*for(int i = 0; i < this.size() ; i++) {
			int size = ((ArrayList) this.get(i)).size();
			
			*/
		ArrayList list1 = new XList((Collection)this.get(0));
		ArrayList list2 = new XList((Collection)this.get(1));
		ArrayList list3 = new XList((Collection)this.get(2));
		
			//XList smallList = new XList();
			int a1 = 0;
			int a2 = 0;
			int a3 = 0;
			int i = 0;
			boolean refresh1 = false;
			boolean refresh2 = false;
			boolean refresh3 = false;
			while (i < 12) {
				/*XList smallList = new XList();
				smallList.add(list1.get(a1));
				smallList.add(list2.get(a2));
				smallList.add(list3.get(a3));
				
				newList.add(smallList);*/
				
				if(a1 == 2) {
					a2++;
					a1 = 0;
				}
				if(a2 == 3) {
					a3++;
					a2=0;
				}
				if(a3 == 2) {
					a3 = 0;
				}
				XList smallList = new XList();
				smallList.add(list1.get(a1));
				smallList.add(list2.get(a2));
				smallList.add(list3.get(a3));
				
				newList.add(smallList);
				a1++;
				
				
				i++;
				
			}
			
		
		
		return newList;
	}

	@FunctionalInterface
	interface Function3<T> {
	    public Object apply(Object obj);
	}
	
	public XList<String> collect(Function3 func) {
	
		
		/*for(int i = 0; i < newList.size(); i++) {
			newList.set(i, func.apply(newList.get(i)));
		}*/
		XList<String> newList = new XList (func.apply(this));
		return newList;
	}
	
	
	
	public String join(String token) {
		String result = "";
		for (Object e: this) {
			result += e.toString();
			result += token;
		}
		result = result.substring(0, result.length() - token.length());
		return result;
	}

	public String join() {
		return (String) this.join("");
	}

	@FunctionalInterface
	interface ElementIndex <Q, T> { 
		public void apply (Q a, T b);
	}

	public void forEachWithIndex(ElementIndex<T,Integer> func) {
		for (int i = 0; i < this.size(); i++) {
			func.apply((T) this.get(i), i);
		}		
	}


	
	/*public void forEachWithIndex(Function2<Integer,Integer,Object> func) {
		
		XList<T> newestList = new XList(this);
		int size = newestList.size();
		for(int i = 0;i < size;i++) {
			func.apply((Integer) this.get(i),i);	
		}
		//return newestList;
		
	}*/

	
}

	

	
