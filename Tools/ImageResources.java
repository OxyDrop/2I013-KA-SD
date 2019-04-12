package Tools;

import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.ByteBuffer;
import javax.imageio.ImageIO;
import javax.media.opengl.GLException;
import javax.swing.ImageIcon;
import jogamp.opengl.glu.mipmap.Image;
 
public class ImageResources {
     
    private final int width;
    private final int height;
    private final ByteBuffer buffer;
     
    public ImageResources(ByteBuffer buffer, int width, int height){
        this.buffer = buffer;
        this.width = width;
        this.height = height;
    }
     
    public int getWidth(){
        return width;
    }
     
    public int getHeight(){
        return height;
    }
     
    public ByteBuffer getBuffer(){
        return buffer;
    }
	
	public static BufferedImage createBuffImage(String path)
	{
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(path));
		} catch (IOException ex) {}
		return img;
	}
	
	public static ImageIcon createImageIcon(String path)
	{
		ImageIcon temp = new ImageIcon(getURL(path));
		return temp;
	}
	
	public static Texture createTexture(String path)
	{
		Texture res = null;
		try 
		{
			res = TextureIO.newTexture(getURL(path), true, "png");
		} 
		catch (IOException | GLException ex)
		{
			ex.printStackTrace();
			System.err.println("Error, couldn't load texture > "+ex.getMessage());
		}
		return res;
	}

	
	public static URL getURL(String path)
	{
		return Toolkit.getDefaultToolkit().getClass().getResource(path);
	}
	
	
}
