package inpdf.utils;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import inpdf.Main;

public class InpdfUtils {
    static final Path resPath = Path.of("src/main/resources").toAbsolutePath();
    
	public static String getTimeFormatted() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		String time = LocalDateTime.now().format(formatter);
		return time;
	}
	
	public static ImageIcon getPngImage(String imageName, int size) {
		try {
			BufferedImage img = ImageIO.read(Thread.currentThread().getContextClassLoader().getResource("resources/" + imageName + ".png"));
			return new ImageIcon(img.getScaledInstance(size, size, Image.SCALE_SMOOTH));
		} catch (Exception ex) {
			
			try {
				BufferedImage img = ImageIO.read(InpdfUtils.class.getResource("/"+ imageName + ".png"));
				return new ImageIcon(img.getScaledInstance(size, size, Image.SCALE_SMOOTH));
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			ex.printStackTrace();
			return null;
		}
	}
}
