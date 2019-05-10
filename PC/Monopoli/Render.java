package Monopoli;

import java.io.File;
import java.io.FileInputStream;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

public class Render {
	
	Render() {}
	
	public static Texture loadTexture(String name)
	{
		Texture tx = null;
		try {
			tx = TextureLoader.getTexture("PNG",new FileInputStream(new File("res/"+name+".png")));
			return tx;
		} catch (Exception e) { e.printStackTrace();}
		return tx;
	}

}
