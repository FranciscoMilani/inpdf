package inpdf.logger;

import org.apache.logging.log4j.*;

import inpdf.Main;
public class LogWriter {
	private static final Logger logger = LogManager.getLogger(Main.class);
	
	public static void log(String message, Level level) {
		logger.atLevel(level).log(message);
	}
}