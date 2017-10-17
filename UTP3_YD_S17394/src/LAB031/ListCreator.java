package LAB031;

import java.util.ArrayList;
import java.util.List;
import java.util.function.*;

public class ListCreator {

public List<String> finalList;
	
	private ListCreator (List<String> list){
		this.finalList = list;
	}
		
	public static ListCreator collectFrom(List<String> list){
		return new ListCreator(list);
	}
	
	public ListCreator when(Predicate<String> func) {
		List<String> newList = new ArrayList<String>();
		for(int i = 0; i < finalList.size(); i++)
		{
			String element = finalList.get(i);
			if(func.test(element))
			{
				newList.add(element);
			}
		}
		finalList = newList;
		
		return this;
		
		
	}
	
	public List<String> mapEvery(Function<String,String> func) {
		
		for(int i = 0; i < finalList.size(); i++)
		{
			finalList.set(i,func.apply(finalList.get(i)));
			
		}
		
		return finalList;
	}
}
