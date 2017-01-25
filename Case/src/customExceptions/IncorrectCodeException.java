package customExceptions;

class IncorrectCodeException extends Exception {

	/**
	 * Custom exception used when certain data is set from the beginning, i.e. "Fast värde 00" to handle cases when data does not match specification
	 */
	
	//TODO implement in handling classes
	
	private static final long serialVersionUID = 1L;
	String codeInFile;

	IncorrectCodeException(String codeInFile) {
		this.codeInFile = codeInFile;
	}

	public String toString() {
		return ("The code in the file was = " + codeInFile);
	}
}

class CustomException {
	public static void main(String args[]) {
		try {
			throw new IncorrectCodeException("Custom");

		} catch (IncorrectCodeException exp) {
			System.out.println("30 was the expected code.");
			System.out.println(exp);
		}
	}
}
