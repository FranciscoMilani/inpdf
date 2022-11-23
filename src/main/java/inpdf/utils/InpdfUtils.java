package inpdf.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class InpdfUtils {
	public static String getTimeFormatted() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		String time = LocalDateTime.now().format(formatter);
		return time;
	}
}
