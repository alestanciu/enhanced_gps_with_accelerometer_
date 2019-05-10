package Monopoli;

import static org.lwjgl.opengl.GL11.*;

import java.awt.Font;
import java.io.File;

import org.lwjgl.examples.spaceinvaders.Texture;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;

public class DrawingFunctions {
	
	private static int old_x=0,old_y=0;
	
	public DrawingFunctions() {

	}
	
	public static void writeString(int x,int y,String text,int size,Color cl)
	{
		Font awtFont = new Font("Arial", Font.BOLD, size);
		TrueTypeFont font1 = new TrueTypeFont(awtFont, false);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		font1.drawString(x,y,text, cl);
		glDisable(GL_BLEND);
	}
	
	public static void drawCarObject(int x,int y,int dimension, Vector3f color)
	{
		glBegin(GL_TRIANGLES);
			glColor3f(color.x,color.y,color.z);
			glVertex3i(x,y-dimension,0);
			glVertex3i(x-dimension,y+dimension,0);
			glVertex3i(x+dimension,y+dimension,0);
		glEnd();
	}
	
	public static void drawGraphPoint(int x,int y)
	{
		glBegin(GL_LINES);
			glColor3f(255,0,0);
			
			glVertex2f(10,40);
			glVertex2f(10,Display.getHeight()-40);
			glVertex2f(10,Display.getHeight()-40);
			glVertex2f(Display.getWidth()-40,Display.getHeight()-40);
			
			writeString(10, 20, "X", 20, Color.green);
			writeString(Display.getWidth()-20,Display.getHeight()-40, "Y", 20, Color.green);
			
			glVertex2f(old_y,old_x);
			glVertex2f(y, x);
		glEnd();
	}
	
	public static void setBackground()
	{
		glBegin(GL_QUADS);
				glVertex3f(0,0,0);
				glVertex3f(Display.getWidth(),0,0);
				glVertex3f(Display.getWidth(),Display.getHeight(),0);
				glVertex3f(0,Display.getHeight(),0);
		glEnd();
	}
	
	
}
