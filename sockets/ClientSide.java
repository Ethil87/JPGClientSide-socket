package sockets;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;

public class ClientSide extends Thread {

	SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd HH mm ss");
	
	
	public void sendImage(BufferedImage bufferedImage) {
	    try {
	    	int port = 13371;
	    	String ipadr = "127.0.0.1";
			Socket socket = new Socket(ipadr, port);
	        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
	        out.writeChar('I'); // as image,
	        
	        //BufferedInputStream bis = new BufferedInputStream(socket.getInputStream());
	        //DataInputStream dis = new DataInputStream(bis);

	        //ImageIO.write(bufferedImage, "jpg", (ImageOutputStream) f);
	       // DataInputStream dis = new DataInputStream(new FileInputStream(f));
	        
	        
	        File f = new File("C:\\TEST\\test.jpg");
	        ImageIO.write(bufferedImage, "jpg", f);
	        DataInputStream dis = new DataInputStream(new FileInputStream(f));
	        ByteArrayOutputStream ao = new ByteArrayOutputStream();
	        //ImageIO.write(bufferedImage, "jpg", ao);
	        int read = 0;
	        byte[] buf = new byte[1024];
	        while ((read = dis.read(buf)) > -1) {
	            ao.write(buf, 0, read);
	        }
	        out.writeLong(ao.size());
	        out.write(ao.toByteArray());
	        out.flush();
	        out.close();
	        dis.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

	
	public void clientSender(BufferedImage img) throws Exception{
		synchronized(this) {
		int port = 13371;
		String ipadr = "192.168.2.38";
		Socket socket = new Socket(ipadr, port);
	    OutputStream outputStream = socket.getOutputStream();
	
	    BufferedImage image = img; //ImageIO.read(new File("C:\\TEST\\test.jpg"));
	
	    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
	    ImageIO.write(image, "jpg", byteArrayOutputStream);
	    //
	    byte[] size = ByteBuffer.allocate(4).putInt(byteArrayOutputStream.size()).array();
	    
	    
	    outputStream.write(size);
	    outputStream.write(byteArrayOutputStream.toByteArray());
	    outputStream.flush();
	    System.out.println("Flushed: " + System.currentTimeMillis());
	
	    //Thread.sleep(240000);
	    System.out.println("Closing: " + System.currentTimeMillis());
	    socket.close();
	}}
	
	
	public BufferedImage CreateImg() throws Exception {
		synchronized(this) {
        Calendar now = Calendar.getInstance();
        Robot robot = new Robot();
        BufferedImage screenShot = robot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
        //ImageIO.write(screenShot, "JPG", new File("C:\\TEST\\"+formatter.format(now.getTime())+"_Client.jpg"));
        System.out.println(formatter.format(now.getTime()));
        
        return screenShot;
    }}
}
