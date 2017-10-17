/**
 *
 *  @author Yelchaninov Denys S17394
 *
 */

package LAB031;


import java.util.*;

public class Main {

  static List<String> getPricesInPLN(List<String> destinations, double xrate) {
    return ListCreator.collectFrom(destinations)
                       .when( (String e)->e.startsWith("WAW"))
                       .mapEvery( e->{
                    	   String dest = e.substring(e.indexOf(" ")+1, e.lastIndexOf(" "));
                    	   Integer cost = (int)(Integer.parseInt(e.substring(e.lastIndexOf(" ")+1))*xrate);
                    	   
                    	   return "to " + dest + " - price in PLN: " + Integer.toString(cost);
                       }
                        );
  }

  public static void main(String[] args) {
    // Lista destynacji: port_wylotu port_przylotu cena_EUR 
    List<String> dest = Arrays.asList(
      "bleble bleble 2000",
      "WAW HAV 1200",
      "xxx yyy 789",
      "WAW DPS 2000",
      "WAW HKT 1000"
    );
    double ratePLNvsEUR = 4.30;
    List<String> result = getPricesInPLN(dest, ratePLNvsEUR);
    for (String r : result) System.out.println(r);
  }
}
