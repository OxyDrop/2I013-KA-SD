// ### WORLD OF CELLS ### 
// created by nicolas.bredeche(at)upmc.fr
// date of creation: 2013-1-12

package landscapegenerator;

import java.util.Random;

public class PerlinNoiseLandscapeGenerator {
	
		public static final int ENVIRONMENT_WATER	= 1;
		public static final int ENVIRONMENT_SAND	= 1;
		public static final int ENVIRONMENT_FOREST	= 1;
		
		private static final double SCALING			=  1;
		private static final double ALTITUDE_RATIO 	=  0.3;
		private static final double FEATURE_SIZE 	=  64;
		private static final double FOREST_ALTITUDE = 0.6;
		public static final double WATER_ALTITUDE	= 0.10;
		private static final float AMPLITUDE = 70f;
		private static final int OCTAVES = 3;
		private static final float ROUGHNESS = 0.3f;
		
		private static Random random = new Random();
		private static int seed =  random.nextInt(1000000000);
	
		/************************************************************
		 *  DEBUT TENTATIVE ECRITURE GENERATEUR ALEATOIRE 
		 * **********************************************************/
	public static float generateHeight(int x, int z)
	{
		float total = 0;
		float d = (float) Math.pow(2,OCTAVES-1);
		
		for(int i=0;i<OCTAVES;i++){
			float freq = (float) (Math.pow(2,i)/d);
			float amp = (float) Math.pow(ROUGHNESS,i) * AMPLITUDE;
			total += getInterpolatedNoise(x*freq, z*freq) * amp;
		}
		return total;
	}
	
	public static float getNoise(int x, int z)
	{
		random.setSeed(x*49632+z*325176+seed);
		return random.nextFloat()*2f-1f;
	}
	
	private static float getSmoothNoise(int x, int z)
	{
		float corners =  (getNoise(x-1,z-1) + getNoise(x+1,z-1) + getNoise(x-1,z+1) 
				+ getNoise(x+1,z+1))/16f;
		
		float sides = (getNoise(x-1,z) + getNoise(x+1,z) + getNoise(x,z-1) 
				+ getNoise(x,z+1))/8f;
		
		float center = getNoise(x,z)/4f;
		
		return corners + sides + center;
		
	}
	
	private static float getInterpolatedNoise(float x, float z)
	{
		int intX = (int) x;
		int intZ = (int) z;
		float fracX = x - intX;
		float fracZ = z - intZ;
		
		float v1 = getSmoothNoise(intX,intZ);
		float v2 = getSmoothNoise(intX+1,intZ);
		float v3 = getSmoothNoise(intX,intZ+1);
		float v4 = getSmoothNoise(intX+1,intZ+1);
		float i1 = interpolate(v1,v2,fracX);
		float i2 = interpolate(v3,v4,fracX);
		
		return interpolate(i1,i2,fracZ);
	}
	
	private static float interpolate(float a, float b, float blend)
	{
		double theta = blend * Math.PI;
		float f = (float)(1f - Math.cos(theta)) * 0.5f;
		
		return a * (1f - f) + b * f;
	}
	
	public static double[][] generatePNL(int dx, int dy, double scaling, double altRatio)
	{
		double landscape[][] = new double[dx][dy];	
		float pnlgh = PerlinNoiseLandscapeGenerator.generateHeight(dx,dy);
		for (int x = 0; x < dx; x++)
			for (int y = 0; y < dy; y++){
				landscape[x][y] = pnlgh;
				landscape[x][y] -= altRatio;
				landscape[x][y] *= 0.5;
				landscape[x][y] *= scaling;
			}
		return landscape;
	}
	
	/************************************************************
		 *  FIN TENTATIVE ECRITURE GENERATEUR ALEATOIRE 
	* **********************************************************/
	
	/***********************UTILISER CETTE METHODE****************/
    public static double[][] generatePerlinNoiseLandscape ( int dx, int dy, double scaling, double altRatio, int perlinLayer )
    {
    	
		double landscape[][] = new double[dx][dy];
    	double[] minMax = GenerateAltitude(dx, dy, landscape);
		double minValue = minMax[0];
		double maxValue = minMax[1];
		double normalizeFactor = 1.0 / (maxValue - minValue);
		
		for (int x = 0; x < dx; x++)
			for (int y = 0; y < dy; y++)
			{
				landscape[x][y] = landscape[x][y] - minValue;
				landscape[x][y] *= normalizeFactor; // [0;1]
				landscape[x][y] = landscape[x][y] - altRatio;
				landscape[x][y] *= scaling;
			}
    	landscape = LandscapeToolbox.scaleAndCenter(landscape, scaling, altRatio);
    	landscape = LandscapeToolbox.smoothLandscape(landscape);

		return landscape;
    }
	
	private static double[] GenerateAltitude(final int dx, final int dy, double[][] elevation) 
	{
		OpenSimplexNoise landscape = new OpenSimplexNoise((long)(Math.random() * 1000));
		double min, max;
		
		min = Double.MAX_VALUE;
		max = Double.MIN_VALUE;
		
		for (int x = 0; x < dx; x++) 
		{
			for (int y = 0; y < dy; y++) 
			{
				elevation[x][y] = landscape.eval(x / FEATURE_SIZE, y / FEATURE_SIZE, 0);
				if (elevation[x][y] < min)
				{
					min = elevation[x][y];
				}
				else if (elevation[x][y] > max)
				{
					max = elevation[x][y];
				}
			}
		}
		
		return new double[] {min, max};
	}
}

