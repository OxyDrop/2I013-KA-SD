// ### WORLD OF CELLS ### 
// created by nicolas.bredeche(at)upmc.fr
// date of creation: 2013-1-12

package objects.Arbres;

import javax.media.opengl.GL2;
import objects.CommonObject;
import worlds.World;

public class Cactus extends CommonObject {

    public static void displayObjectAt(World myWorld, GL2 gl, int cellState, float x, float y, 
										double height, float offset,float stepX, float stepY, 
										float lenX, float lenY, float normalizeHeight )
    {
        //float smoothFactorAvg = ( smoothFactor[0] + smoothFactor[1] + smoothFactor[2] + smoothFactor[3] ) / 4.f;
		
		gl.glColor3f(0f,0f,0f); //black

    
        if ( cellState > 0 )
        {
    		float altitude = (float)height * normalizeHeight ;
    		
    		//float heightFactor, double heightBooster, float smoothFactor[]
    		
			
            gl.glVertex3f( offset+x*stepX-lenY/16.f, offset+y*stepY+lenY/2.f, altitude + 4.f );
			gl.glVertex3f( offset+x*stepX, offset+y*stepY, altitude );
            gl.glVertex3f( offset+x*stepX+lenY/16.f, offset+y*stepY-lenY/2.f, altitude + 4.f );
			gl.glVertex3f( offset+x*stepX, offset+y*stepY, altitude );
   
            gl.glVertex3f( offset+x*stepX-lenY/2.f, offset+y*stepY+lenY/16.f, altitude + 4.f );
            gl.glVertex3f( offset+x*stepX, offset+y*stepY, altitude );
            gl.glVertex3f( offset+x*stepX+lenY/2.f, offset+y*stepY-lenY/16.f, altitude + 4.f );
			gl.glVertex3f( offset+x*stepX, offset+y*stepY, altitude );
        }
    }

}
