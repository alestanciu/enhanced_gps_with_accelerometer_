package Monopoli;

import TCP.TCPMain;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glLoadIdentity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;



public class Launcher {

	private static int WIDTH = 1028;
	private static int HEIGHT = 700;
	private static int FPS = 60;
	private static String TITTLE = "Monopoli";
	private static TCPMain TCPClient;
	private static ProcessBuilder pb,pb2;
	private static Object pb_1,pb_2;
	private static String stg = null;
	
	public static enum states
	{
		INTRO,
		MENU,
		ACL, ACL2,
		GPS
	};
	
	public static states state = states.INTRO;
	
	private static IntroScreen sc;
	
	public static void main(String[] args) throws Exception
	{
		DisplayClass dc = new DisplayClass(WIDTH,HEIGHT,TITTLE,FPS);
		sc = new IntroScreen();
		dc.create(false);
		
		glClear(GL_COLOR_BUFFER_BIT);
		glClear(GL11.GL_DEPTH_BUFFER_BIT);
		DrawingFunctions.writeString(WIDTH/3, HEIGHT/2, "Waiting for device connection...", 36, Color.red);
		dc.update();
		
		BufferedReader fr = null;
		try
		{
		String[] readed_data = null;
		int data_index = 0;
		String to_read = "";
		fr = new BufferedReader(new FileReader("acl_string.dta"));
		
			while((to_read = fr.readLine()) != null)
			{
				readed_data[data_index] = to_read;
				data_index++;
			}
			fr.close();
		} catch (Exception e) {}
		
		
		TCPClient = new TCPMain();
		TCPClient.Initialize(6789);
		String data1 = "0 0 1";
		
		
		Runtime.getRuntime().addShutdownHook(new Thread() {
	        public void run(){
	            try {
					TCPClient.CloseConnection();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
	    });

	    float x_dir=0,y_dir=0; 
	    String dta = "";
		
		Texture text1 = Render.loadTexture("wood");
		//DrawingFunctions.setBackground(text1);
		while(!Display.isCloseRequested())
		{
			glClear(GL_COLOR_BUFFER_BIT);
			glClear(GL11.GL_DEPTH_BUFFER_BIT);
			glLoadIdentity();
			
			
			
			switch(state)
			{
			case INTRO:
					state = states.MENU;
					break;
			case MENU:
				DrawingFunctions.writeString(10, 10, "Press backspace to get back to menu:", 24, Color.orange);
				DrawingFunctions.writeString(10, 30, "Select options:", 24, Color.orange);
				DrawingFunctions.writeString(10, 50, "1.Live GPS Coords", 24, Color.orange);
				DrawingFunctions.writeString(10, 70, "2.ACL data (until now)", 24, Color.orange);
				DrawingFunctions.writeString(10, 100, "Choosing '2' will stop the program to aquire current data!", 16, Color.red);
				if(TCPClient.isReceiving) DrawingFunctions.writeString(10, 120, "Status:ON", 16, Color.green);
				else DrawingFunctions.writeString(10, 120, "Status:OFF", 16, Color.red);
				stg = TCPClient.ReadData();
				break;
			case ACL:
				stg = TCPClient.ReadData();
				break;
			case GPS:
				dta = TCPClient.ReadData();
				if(!dta.isEmpty()); {data1 = dta;}
				try
				{
				x_dir = Float.valueOf(data1.split(" ")[0]);
				y_dir = Float.valueOf(data1.split(" ")[2]);
				} catch(Exception e) {x_dir = 0f; y_dir = 0f;} 
				DrawingFunctions.writeString(20, 10, "N", 20, Color.red);
				DrawingFunctions.writeString(100, 10, "E", 20, Color.red);
				DrawingFunctions.writeString(20, 30, x_dir+"", 20, Color.red);
				DrawingFunctions.writeString(100, 30, y_dir+"", 20, Color.red);
				break;
			}
			
			if(Keyboard.isKeyDown(Keyboard.KEY_1))
			{
				if(state == states.MENU)
				{
					state = states.GPS;
				}
			}
			if(Keyboard.isKeyDown(Keyboard.KEY_2))
			{
				if(state == states.MENU)
				{
					state = states.ACL;
					TCPClient.isReadingFile = true;
					try  { TCPClient.CloseWriters(); } catch (Exception e) {}
					pb = new ProcessBuilder("Notepad.exe", "acl_string.dta");
					pb_1 = pb.start();
					pb2 = new ProcessBuilder("Notepad.exe", "acl.dta");
					pb_2 = pb2.start();
				}
			}
			if(Keyboard.isKeyDown(Keyboard.KEY_BACK))
			{
				if(state == states.GPS)
				{
					state = states.MENU;
				}
				else if(state == states.ACL)
				{
					try { Process ps = (Process)pb_1; Process ps2 = (Process)pb_2; ps.destroy(); ps2.destroy(); } catch (Exception e) {}
					TCPClient.InitiateWriters();
					TCPClient.isReadingFile = false;
					state = states.MENU;
				}
				else {}
			}
			dc.update();
		}
		dc.destroy();
		//t1.interrupt();
		try  { TCPClient.CloseConnection(); } catch (Exception e) {}
		System.exit(0);
	}

}

