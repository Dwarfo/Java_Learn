/**
 *
 *  @author Yelchaninov Denys S17394
 *
 */

package LAB02;


public interface Mapper<T, P> {
	// Uwaga: interfejs musi być sparametrtyzowany
	public P map(T mapped);
}  
