/**
 *
 *  @author Yelchaninov Denys S17394
 *
 */

package LAB032;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/*<-- niezbędne importy */

public class Main {

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
    List<String> result = dest.stream().filter(e->e.startsWith("WAW"))
    		.map(e->{
    			String de = e.substring(e.indexOf(" ")+1, e.lastIndexOf(" "));
    			Integer cost = (int)(Integer.parseInt(e.substring(e.lastIndexOf(" ")+1))*ratePLNvsEUR);
    			return "to " + de + " - price in PLN: " + Integer.toString(cost);
    		}).collect(Collectors.toList());
    /*<-- tu należy dopisać fragment
     * przy czym nie wolno używać żadnych własnych klas, jak np. ListCreator
     * ani też żadnych własnych interfejsów
     * Podpowiedź: należy użyć strumieni
     */

    for (String r : result) System.out.println(r);
  }
}
