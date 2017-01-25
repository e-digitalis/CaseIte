package interfaces;

import java.math.BigDecimal;
import java.util.List;
import java.util.regex.*;

public interface FileHandler {

	/**
	 * Called to check that the file name contains the correct characters/follows the correct structure.
	 * Method should invoke regex() whenever applicable - e.g. for file names and/or data within files
	 * @param fileName The name of the file
	 * @param regex The regular expression to be checked
	 */	
	public void checkFileName(String fileName);
	
	/**
	 * Regular expression and pattern matcher
	 * @param pattern The regular expression to be checked
	 * @param matcher The match engine
	 */	
	public void regex(Pattern pattern, Matcher matcher);
	
	/**
	 * Check that the file follows the appropriate pattern for files of this type 
	 * @param typeOfPost The type of post to be checked, e.g. clearingNumber, accountNumber or reference
	 * @param dataType e.g. String, double, int
	 * @param substringPosition Position of substring to be checked
	 */
	public void fileQualityControl(List<String> lines);
	

}

