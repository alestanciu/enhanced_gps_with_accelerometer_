package Monopoli;

import static org.lwjgl.opengl.GL11.*;

import java.awt.Font;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.Texture;

public class IntroScreen {
	
	
	public IntroScreen()
	{

		/*
		glEnable(GL_TEXTURE_2D);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		int height = Display.getHeight();
		int width = Display.getWidth();
		glBegin(GL_QUADS);
			glColor3f(0, 1, 0);
			glVertex2f(0,0);
			glVertex2f(width,0);
			glVertex2f(width,height);
			glVertex2f(0,height);
		glEnd();
		glDisable(GL_TEXTURE_2D);
		*/
	}
	
	
	public static void ShowFrame()
	{
		
		//DrawingFunctions.writeString(20,20,"x=",18,Color.red);
		//DrawingFunctions.writeString(20,40,"y=",18,Color.red);
		DrawingFunctions.drawCarObject(500, 700, 50, new Vector3f(0,1,0));
		//DrawingFunctions.setBackground(tx);
	}
	
	

}
