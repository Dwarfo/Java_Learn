/**
 *
 *  @author Yelchaninov Denys S17394
 *
 */

package LAB062;

import java.util.ArrayList;

public class Purchase {
	final String id_klienta;
	String nazwisko;
	String imie;
	final String nazwa_towaru;
	double cena;
	final double zakupiona_ilosc;
	
	public Purchase(String s){
		String[] props = s.split(";");
		id_klienta = props[0];
		nazwisko = props[1].split(" ")[0];
		imie = props[1].split(" ")[1];
		nazwa_towaru = props[2];
		cena = Double.parseDouble(props[3]);
		zakupiona_ilosc = Double.parseDouble(props[4]);
	}
	
	public String toString() {
		return id_klienta + ";" + nazwisko + " " + imie + ";" + nazwa_towaru + ";" + String.valueOf(cena) + ";" + String.valueOf(zakupiona_ilosc);
	}
	
	public Double kosht() {
		return cena * zakupiona_ilosc;
	}
}

