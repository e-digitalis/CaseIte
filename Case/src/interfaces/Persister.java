package interfaces;

import java.math.BigDecimal;

public interface Persister {
	
	   /**
     * Called to create and manage entity manager and factory.
     * I assume you work with Java EE and have chosen injections to persist data
     * @param dbName Name of database
     */
    public void transactionPersist(String dbName);

}