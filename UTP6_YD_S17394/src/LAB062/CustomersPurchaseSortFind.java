/**
 *
 *  @author Yelchaninov Denys S17394
 *
 */

package LAB062;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class CustomersPurchaseSortFind {
	
	private List<Purchase> purchaseList = new ArrayList<>();
	
	public void readFile(String fileName) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			String linia;
			while ((linia = br.readLine()) != null)
				purchaseList.add(new Purchase(linia));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void showSortedBy(String field) {
		switch (field){
		case "Nazwiska": 
			printNazwiska();
			break;
		case "Koszty":
			printKoszty();
			break;
		}
	}
	
	private void printNazwiska() {
		System.out.println();
		System.out.println("Nazwiska");
		purchaseList.stream()
					.sorted((a, b) -> !a.nazwisko.equals(b.nazwisko) ? a.nazwisko.compareTo(b.nazwisko) : a.id_klienta.compareTo(b.id_klienta))
					.forEach(c -> System.out.println(c));
	}
	
	private void printKoszty() {
		System.out.println();
		System.out.println("Koszty");
		purchaseList.stream()
		.sorted((a, b) -> Math.abs(a.kosht()-b.kosht()) > 0.1 ? Double.compare(b.kosht(), a.kosht()) : a.id_klienta.compareTo(b.id_klienta) )
		.forEach(c -> System.out.println(c.toString() + " (koszt: " + String.valueOf(c.kosht()) + ")"));
	
	}
	
	public void showPurchaseFor(String customerId) {
		System.out.println();
		System.out.println("Klient " + customerId);
		purchaseList.stream()
		.filter(a -> a.id_klienta.equals(customerId) )
		.forEach(c -> System.out.println(c));
	}
	
}

