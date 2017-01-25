package interfaces;

import java.math.BigDecimal;
import java.nio.file.*;
import java.util.List;

public interface FileReader {
	
	
    /**
     * Called to read file.
     * @param amount The payment amount.
     * @param reference The payment reference.
     */
    public List<String> readFile(String pathFilename, String encoding);

}


