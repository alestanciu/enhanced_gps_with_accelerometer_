package Monopoli;

import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Cursor;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.*;
import org.lwjgl.util.Dimension;

public class DisplayClass {

	private static int WIDTH = 1028;
	private static int HEIGHT = 768;
	private static int FPS = 60;
	private static String tittle = "App1";
	private static boolean isFullscreen = false;
	
	public DisplayClass(int width,int height,String tittle,int fps)
	{
				this.WIDTH=width;
				this.HEIGHT=height;
				this.tittle = tittle;
				this.FPS=fps;
	}
	
	public void create(boolean fullscreen) throws LWJGLException
	{
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		int width = gd.getDisplayMode().getWidth();
		int height = gd.getDisplayMode().getHeight();
		
		if(fullscreen) Display.setFullscreen(true);
		else Display.setDisplayMode(new DisplayMode(WIDTH,HEIGHT));
		Display.setTitle(tittle);
		Display.setVSyncEnabled(true);
		Display.create();
		GL11.glEnable(GL11.GL_TEXTURE_2D);               

		glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
		glOrtho(0, Display.getWidth(),Display.getHeight(), 0, 1, -1);
		glMatrixMode(GL11.GL_MODELVIEW);
	}
	
	
	public void update()
	{
		Display.update();
		Display.sync(FPS);
	}
	
	public void destroy()
	{
		Display.destroy();
	}
	
	public void HideCursor()
	{
		Cursor emptyCursor;
	    int min = org.lwjgl.input.Cursor.getMinCursorSize();
	    IntBuffer tmp = BufferUtils.createIntBuffer(min * min);
	    try
	    {
	    	emptyCursor = new org.lwjgl.input.Cursor(min, min, min / 2, min / 2, 1, tmp, null);
		    Mouse.setNativeCursor(emptyCursor);
	    } catch(Exception e) { }
	}
	
	public void toggleFullscreen()
	{
		try
		{
			destroy();
			create(!isFullscreen);
			isFullscreen = !isFullscreen;
		} catch(LWJGLException e) { e.printStackTrace(); }
	}
}
