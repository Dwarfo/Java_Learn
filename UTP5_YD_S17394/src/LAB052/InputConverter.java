package LAB052;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class InputConverter <T> {

	private T value;
	public InputConverter(T value){
		this.value = value; 
	}
	
	public <S> S convertBy(Function... funcs) {
		Object r = value;
		for (Function f : funcs) {
			r =  f.apply(r);
		}
		return (S) r;
	}
	
}
