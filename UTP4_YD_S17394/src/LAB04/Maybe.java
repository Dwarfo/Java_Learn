package LAB04;

import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class Maybe<T> {
	
	private T mObject;
	
	public Maybe (T object) {
		this.mObject = object;
	}

	public static<T> Maybe<T> of(T object){
		return new Maybe<T>(object);	
	}

	public String toString(){
		return this.mObject == null ? "Maybe is empty" : "Maybe has value" + mObject;
	}
	
	void ifPresent(Consumer<T> cons){
		if(this.mObject != null){
			cons.accept(this.mObject);
		}
	}
	
	public<Q> Maybe<Q> map(Function<T,Q> func){
		if(mObject != null)
			return new Maybe<Q>(func.apply(mObject));
		else
			return new Maybe<Q>(null);
	}
	
	public T get(){
		if(this.mObject != null)
			return this.mObject;
		else
			throw new NoSuchElementException("maybe is empty");
	} 
	
	public boolean isPresent(){
		if(this.mObject != null)
			return true;
		else
			return false;	
	}
	
	public T orElse(T value){
		if(this.mObject!=null)
			return this.mObject;
		else
			return value;
	}
	
	public Maybe<T> filter(Predicate<T> pred){
		if(this.mObject == null || pred.test(this.mObject))
			return this;
		else
			return new Maybe<T>(null);
	}
	
}
