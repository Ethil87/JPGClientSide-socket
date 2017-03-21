package screencapture;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.imageio.ImageIO;

public class MakeScrn extends Thread {
	
	SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd hh mm ss a");
	BufferedImage img;
	
	
	
    public BufferedImage getImg() {
		return img;
	}

	public void setImg(BufferedImage img) {
		this.img = img;
	}

	
	public MakeScrn() {
		super();
		// TODO Auto-generated constructor stub
	}
    
    public void run() {
        try {
			setImg(CreateImg());
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

	public BufferedImage CreateImg() throws Exception {
        Calendar now = Calendar.getInstance();
        Robot robot = new Robot();
        BufferedImage screenShot = robot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
        ImageIO.write(screenShot, "JPG", new File("C:\\TEST\\"+formatter.format(now.getTime())+"_Client.jpg"));
        System.out.println(formatter.format(now.getTime()));
        
        return screenShot;
    }
    
}
