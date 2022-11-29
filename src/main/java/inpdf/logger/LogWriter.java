package inpdf.logger;

import org.apache.logging.log4j.*;

public class LogWriter {
	private static final Logger logger = LogManager.getLogger(LogWriter.class);
	
	public static void log(String message, Level level) {
		logger.atLevel(level).log(message);
	}
}