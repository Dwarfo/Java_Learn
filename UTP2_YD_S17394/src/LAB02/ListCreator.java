/**
 *
 *  @author Yelchaninov Denys S17394
 *
 */

package LAB02;
import java.util.ArrayList;
import java.util.List;

public class ListCreator<T,P> {
	
	public List<T> finalList;
	
	private ListCreator (List<T> list){
		this.finalList = list;
	}
	
	
	public static <T,P> ListCreator<T,P> collectFrom(List<T> list){
		return new ListCreator<T,P>(list);
	}
	
	
	
	public ListCreator<T,P> when(Selector<T> selector){
		/*int i = 0;
		while(i< finalList.size()) {
			T element = finalList.get(i);
			if(selector.select(element)) {
				finalList.remove(i);
				i--;
			}
			else i++;
		}*/
		List<T> newList = new ArrayList<T>();
		for(int i = 0; i < finalList.size(); i++)
		{
			T element = finalList.get(i);
			if(selector.select(element))
			{
				newList.add(element);
			}
		}
		finalList = newList;
		
		return this;
	}

	public List<P> mapEvery(Mapper<T,P> mapper){
		
		List<P> newList = new ArrayList<P>();
		for(int i = 0; i < finalList.size(); i++)
		{
			T element = finalList.get(i);
			P mappedElement = mapper.map(element);
			newList.add(mappedElement);
		}
		return newList;
	}
	
	
}  
