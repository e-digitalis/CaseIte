package additionalMethods;

import java.math.BigDecimal;

public class Inbetalningstjänsten {

	public static void main(String[] args) {

		// ÖPPNINGSPOSTEN
		String open = "00000000001234123456789700000000000000000000000000000000000000000000000000000000";

		int postOpen = Integer.parseInt(open.substring(0, 2));
		int clearingNumber = Integer.parseInt(open.substring(10, 14));
		int accountNumber = Integer.parseInt(open.substring(14, 24).trim());

		System.out.println(postOpen);
		System.out.println(clearingNumber);
		System.out.println(accountNumber);

		System.out.println("_______________");

		// BETALNINGSPOSTER

		String a1 = "30000000000000004000000000000000000000009876543210";

		int startFixed = Integer.parseInt(a1.substring(0, 2));
		if (startFixed != 30) {
			System.out.println("//TODO throw and handle exception when file does not match specification. \n"
					+ "This is a situation that cannot be recovered from and requires external handling.");
		}
		String numberToBe = a1.substring(2, 22).trim();
		numberToBe = numberToBe.substring(0, 18) + "." + numberToBe.substring(18, numberToBe.length());
		BigDecimal payment = new BigDecimal(numberToBe);
		String reference = a1.substring(40, 50).trim();

		System.out.println(startFixed);
		System.out.println(payment);
		System.out.println("ref " + reference);

		System.out.println("_______________");
		// SLUTPOSTEN
		String close = "99000000000000015300000000000000000003000000000000000000000000000000000000000000";

		int postClose = Integer.parseInt(close.substring(0, 2));
		String dot = close.substring(2, 22).trim();

		dot = dot.substring(0, 18) + "." + dot.substring(18, dot.length());

		BigDecimal sum = new BigDecimal(dot);
		int count = Integer.parseInt(close.substring(31, 38).trim());

		System.out.println(postClose);
		System.out.println(sum);
		System.out.println(count);

	}

}