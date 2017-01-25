package fileHandler;

import java.io.IOException;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//import javax.persistence.EntityManager;
//import javax.persistence.EntityManagerFactory;
//import javax.persistence.Persistence;

import interfaces.FileHandler;
import interfaces.PaymentReceiver;
import interfaces.Persister;
import interfaces.FileReader;

public class Betalningsservice implements FileHandler, PaymentReceiver, Persister, FileReader {

	private String accountNumber;
	private String count;
	private Date paymentDate;
	private String currency;
	private BigDecimal amount;
	private String reference;
	private String fileName;
	private String pathFilename;
	private String encoding;
	private List<String> lines;
	public String dbName;

	public Betalningsservice() {
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getPathFilename() {
		return pathFilename;
	}

	public void setPathFilename(String pathFilename) {
		this.pathFilename = pathFilename;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	@Override
	public void startPaymentBundle(String accountNumber, Date paymentDate, String currency) {
		// TODO Auto-generated method stub

	}

	@Override
	public void payment(BigDecimal amount, String reference) {
		// TODO Auto-generated method stub

	}

	@Override
	public void endPaymentBundle() {
		// TODO Auto-generated method stub

	}

	@Override
	public void checkFileName(String fileName) {
		// TODO implement regex pattern to check that filename ends with
		// "_betalningsservice.txt"
		// use the $ character to ensure the filename ends with
		// _betalningsservice.txt$
	}

	@Override
	public void regex(Pattern pattern, Matcher matcher) {
		// TODO implement regex pattern for type Heltal \d+
		// \d stands for any digit, + means at least one character, more allowed
		// TODO implement regex pattern for type Decimaltal ,{1}
		// for type decimaltal match both \d+ and also comma, only one allowed
		// TODO implement regex pattern for type Sträng \w+
		// \w stands for any alphanumeric character [A-Za-z0-9_], + means at
		// least one character, more allowed
		// Pattern p = Pattern.compile("\\d+_(\\w+)\\.txt");
		// Matcher m = p.matcher("33232_abcTextFile.txt");
		// if (m.matches()) {
		// if (m.group(1).equals("abFile")) {
		// // Do something
		// } else if (m.group(1).equals("abcTextFile")) {
		// // Do something else
		// } else {
		// // Unknown filename, handle it
		// }
		// } else {
		// // Unknown file format, handle it
		// }

	}

	@Override
	public void fileQualityControl(List<String> lines) {

		Scanner scanner = new Scanner((Readable) lines);

		for (String firstLine : lines) {
			String posttype = firstLine.substring(0, 1).trim();
			char pty = posttype.charAt(0);

			for (String secondLine : lines) {
				System.out.println(secondLine);

				switch (pty) {

				case 'O': // behandlar rader av typen "Öppningspost"
					System.out.println("O");
					setAccountNumber(secondLine.substring(2, 17).trim());

					String dot = secondLine.substring(17, 31).trim();
					if (dot.contains(",")) {
						dot = dot.replace(',', '.');
					}

					setAmount(new BigDecimal(dot));
					setCount(secondLine.substring(31, 40).trim());

					int year = Integer.parseInt(secondLine.substring(40, 44));
					int month = Integer.parseInt(secondLine.substring(44, 46));
					int day = Integer.parseInt(secondLine.substring(46, 48));

					String anotherdate = secondLine.substring(40, 48).trim();
					String dashDate = anotherdate.substring(0, 4) + "-" + anotherdate.substring(4, 6) + "-"
							+ anotherdate.substring(6);
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
					LocalDate paymntDate = LocalDate.parse(anotherdate, formatter);

					// Date localDate = LocalDate.of(year, month, day);
					// setPaymentDate(localDate);
					// TODO investigate why date cannot be parsed
					currency = secondLine.substring(48, 51).trim();

					break;
				// ______________________________________

				case 'B': // Behandlar rader av typen "Betalningspost"
					System.out.println("B");

					String type = secondLine.substring(0, 1).trim();
					String am = secondLine.substring(1, 15).trim();
					if (am.contains(",")) {
						am = am.replace(',', '.');
					}
					setAmount(new BigDecimal(am));

					setReference(secondLine.substring(15).trim());

					break;

				// ______________________________________

				default:
					System.out.println("//TODO throw and handle exception when file does not match specification. \n"
							+ "This is a situation that cannot be recovered from and requires external handling.");
				}
				transactionPersist(dbName);
			}
		}

	}

	@Override
	public void transactionPersist(String dbName) {

		// TODO debug Eclipse :)

		EntityManagerFactory emf = Persistence.createEntityManagerFactory(dbName);
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		Betalningsservice betSer = new Betalningsservice();
		betSer.setName("new Name");
		em.persist(betSer);
		em.getTransaction().commit();

	}

	@Override
	public List<String> readFile(String pathFilename, String encoding) {
		try {
			lines = Files.readAllLines(Paths.get(pathFilename), Charset.forName(encoding));
		} catch (IOException e) {
			e.printStackTrace(System.out);
		}
		for (String line : lines) {
			System.out.println(line);
		}

		// encoding = ISO-8859-1
		return lines;
	}

}