package inpdf.utils;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class InpdfUtils {
    static final Path resPath = Path.of("src/main/resources").toAbsolutePath();
    
	public static String getTimeFormatted() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		String time = LocalDateTime.now().format(formatter);
		return time;
	}
	
	public static void setButtonImage(JButton button, String imageName, String placeHolder, int size) {
		ImageIcon icon = new ImageIcon();
		
		try {
			BufferedImage img = ImageIO.read(Thread.currentThread().getContextClassLoader().getResource("resources/" + imageName + ".png"));
			icon.setImage(img.getScaledInstance(size, size, Image.SCALE_SMOOTH));
			button.setIcon(icon);
		} catch (Exception ex) {	
			try {
				BufferedImage img = ImageIO.read(InpdfUtils.class.getResource("/"+ imageName + ".png"));
				icon.setImage(img.getScaledInstance(size, size, Image.SCALE_SMOOTH));
				button.setIcon(icon);
			} catch (IOException e) {
				e.printStackTrace();
				button.setText(placeHolder);
			}
		}
		
	}
}