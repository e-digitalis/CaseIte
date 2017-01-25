package fileHandler;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// appropriate persistence imports 

import interfaces.FileHandler;
import interfaces.PaymentReceiver;
import interfaces.Persister;
import interfaces.FileReader;

//@Entity //Assuming Java EE and DI are standard
//@Table(name="transactions_Inbetalningstjänsten")
public class Inbetalningstjänsten implements FileHandler, PaymentReceiver, Persister, FileReader {

	private String accountNumber;
	private int clearingNumber;
	private Date paymentDate;
	private String currency;
	private BigDecimal amount;
	private BigDecimal sum;
	private String reference;
	private int count;
	private String fileName;
	private String pathFilename;
	private String encoding;
	private List<String> lines;
	public String dbName;

	// @Id
	// @Column(name="transaction_id")
	// @GeneratedValue(strategy=GenerationType.IDENTITY)
	// public Long getTransactionId(){ //assuming both getters and setters are
	// in place
	// return getTransactionId;
	// }

	public Inbetalningstjänsten() {

	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public int getClearingNumber() {
		return clearingNumber;
	}

	public void setClearingNumber(int clearingNumber) {
		this.clearingNumber = clearingNumber;
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

	public BigDecimal getSum() {
		return sum;
	}

	public void setSum(BigDecimal transaction) {
		this.sum = transaction;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
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

	public List<String> getLines() {
		return lines;
	}

	public void setLines(List<String> lines) {
		this.lines = lines;
	}

	@Override
	public void startPaymentBundle(String accountNumber, Date paymentDate, String currency) {
		transactionPersist(dbName);

	}

	@Override
	public void payment(BigDecimal amount, String reference) {
		transactionPersist(dbName);

	}

	@Override
	public void endPaymentBundle() {
		transactionPersist(dbName);

	}

	@Override
	public void checkFileName(String fileName) {
		// TODO implement regex pattern to check that filename ends with
		// "_inbetalningstjansten.txt"
		// use the $ character to ensure the filename ends with
		// _inbetalningstjansten.txt$

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
			String posttype = firstLine.substring(0, 2).trim();

			for (String secondLine : lines) {
				System.out.println(secondLine);

				switch (posttype) {
				case "00": // Behandlar rader av typen "öppningspost"
					int postOpen = Integer.parseInt(secondLine.substring(0, 2));
					setClearingNumber(Integer.parseInt(secondLine.substring(10, 14)));
					setAccountNumber(secondLine.substring(14, 24).trim());
					break;

				case "30": // Behandlar rader av typen "betalningspost"
					int startFixed = Integer.parseInt(secondLine.substring(0, 2));
					String numberToBe = secondLine.substring(2, 22).trim();
					numberToBe = numberToBe.substring(0, 18) + "." + numberToBe.substring(18, numberToBe.length());
					setAmount(new BigDecimal(numberToBe));
					setReference(secondLine.substring(40, 50).trim());
					break;

				case "99": // Behandlar rader av typen "slutpost"
					int postClose = Integer.parseInt(secondLine.substring(0, 2));
					String dot = secondLine.substring(2, 22).trim();
					dot = dot.substring(0, 18) + "." + dot.substring(18, dot.length());
					setSum(new BigDecimal(dot));
					setCount(Integer.parseInt(secondLine.substring(31, 38).trim()));
					break;

				default:
					System.out.println("//TODO throw and handle exception when file does not match specification. \n"
							+ "This is a situation that cannot be recovered from and requires external handling.");
				}
				transactionPersist(dbName);

			}
		}
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
		return lines;

		// encoding = ISO-8859-1
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

}
