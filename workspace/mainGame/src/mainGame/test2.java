package mainGame;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;

public class test2 {

	public static void main(String[] args) {
		try {
			Display.create();
		} catch (LWJGLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
