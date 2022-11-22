package inpdf.logger;

import java.io.IOException;
//import java.lang.System.Logger.Level;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.Level;

public class LogWriter {
	LogWriter() throws SecurityException, IOException {
        Logger logger = Logger.getLogger(LogWriter.class.getName());
        
        FileHandler fileHandler = new FileHandler("app.log", true);
        logger.addHandler(fileHandler);

        if (logger.isLoggable(Level.INFO)) {
            logger.info("Information message");
        }

        if (logger.isLoggable(Level.WARNING)) {
            logger.warning("Warning message");
        }

    }
}
