/**
 *
 *  @author Yelchaninov Denys S17394
 *
 */

package LAB02;

import java.util.*;

public class Main {
  public Main() {
    List<Integer> src1 = Arrays.asList(1, 2, 3, 4, 5, 6);
    System.out.println(test1(src1));

    List<String> src2 = Arrays.asList("a", "zzzz", "vvvvvvv","aaaadsdsa" );
    System.out.println(test2(src2));
  }

  public List<Integer> test1(List<Integer> src) {
    Selector<Integer> sel = new TooBigInt(3); 
    Mapper<Integer,Integer> map = new IntMapper(4);   /*<-- definicja mappera; bez lambda-wyrażeń; nazwa zmiennej map */
    return  ListCreator.<Integer,Integer>collectFrom(src).when(sel).mapEvery(map);
  }

public List<Integer> test2(List<String> src) {
	Selector<String> sel = new TooLongString(5); 
    Mapper<String,Integer> map = new StringMapper(4);   /*<-- definicja mappera; bez lambda-wyrażeń; nazwa zmiennej map */
    return  ListCreator.<String,Integer>collectFrom(src).when(sel).mapEvery(map);
     
  }

  public static void main(String[] args) {
    new Main();
    
  }
}
