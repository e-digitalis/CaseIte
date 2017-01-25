package additionalMethods;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Betalningsservice {

	public static String open = "O5555 5555555555       4711,17         420110315SEK";

	public static void main(String[] args) {


		// ÖPPNINGSPOSTEN

		String posttype = open.substring(0, 1).trim();
		String accountNumber = open.substring(2, 17).trim();
		

		String dot = open.substring(17, 31).trim();
		if (dot.contains(",")) {
			dot = dot.replace(',', '.');
		}
		
		BigDecimal amount = new BigDecimal(dot);

		String count = open.substring(31, 40).trim();
		String date = open.substring(40, 48).trim();

		String currency = open.substring(48, 51).trim();

		System.out.println("Type " + posttype);
		System.out.println("Account number " + accountNumber);
		System.out.println("Sum " + amount);
		System.out.println("Count " + count);
		System.out.println("Date " + date);
		System.out.println("Currency " + currency);

		// BETALNINGSPOSTER


		String a1 = "B          30001234567890";
		String a2 = "B          10002345678901";
		String a3 = "B        300,103456789012";
		String a4 = "B        400,074567890123";

		String[] array = { a1, a2, a3, a4 };
		
		for (String str : array) {
			System.out.println("_______________");
			String type = str.substring(0, 1).trim();
			String am = str.substring(1, 15).trim();
			if (am.contains(",")) {
				am = am.replace(',', '.');
			}
			String reference = str.substring(15).trim();

			BigDecimal amount2 = new BigDecimal(am);

			System.out.println(type);
			System.out.println(am);
			System.out.println(reference);
			System.out.println(amount2);
			
		}

	}

}

/*
 * Position Fält Datatyp Kommentar 1 Posttyp Sträng Fast värde O (stora
 * bokstaven o, ej siffran 0). 2-16 Kontonummer Sträng Anger kontonummer för
 * mottagande konto, inklusive clearingnummer. Inleds med clearingnummer, följt
 * av ett mellanslag, följt av kontonumret. Clearingnummer och kontonummer får
 * bara innehålla siffrorna 0-9 och får inte avdelas med andra tecken. 17-30
 * Summa Decimalt Anger summan av beloppsfälten i betalningsposterna i filen.
 * 31-40 Antal Heltal Anger antal betalningsposter i filen. 41-48
 * Betalningsdatum Datum Anger betalningsdatum för betalningarna i filen. 49-51
 * Valuta Sträng Anger valuta för betalningarna i filen
 */
