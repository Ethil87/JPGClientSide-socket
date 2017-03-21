package mainclass;

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JFrame;

import screencapture.MakeScrn;
import sockets.ClientSide;

public class MainClass {
	
	static Calendar now = Calendar.getInstance();
	static SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd HH mm ss");
	
	public static void makeFolder(String folder) {

		if (new File("C:\\TEST\\" + folder).exists() == true) {
			System.out.println("Directory already exist");
		} else {
			File f = new File("C:\\TEST\\" + folder);
			try {
				if (f.mkdir()) {
					System.out.println("Directory Created");
				} else {
					System.out.println("Directory is not created");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) throws Exception {		
		int k = 0;
		while (k < 360) {
			try {
				ClientSide cs = new ClientSide();
				cs.clientSender(cs.CreateImg());
				k++;
				Thread.sleep(10000);
			} catch (Exception e) {
				makeFolder("Exceptions");
				FileWriter fstream = new FileWriter("C:\\TEST\\Exceptions\\Errors.txt", true);
				//BufferedWriter out = new BufferedWriter(fstream);
				PrintWriter pw = new PrintWriter(fstream);
				pw.write(formatter.format(now.getTime())+ " >> " + e.toString());
				pw.println();
				pw.close();
				fstream.close();
				e.printStackTrace();
			}
		}

	}
}
