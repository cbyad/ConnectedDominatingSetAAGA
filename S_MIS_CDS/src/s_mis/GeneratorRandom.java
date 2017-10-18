package s_mis;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Random;


/**
 * 
 * @author cb_mac
 * Testbets geneneration;
 */
public class GeneratorRandom {
	private static int largeur=900;
	private static int longueur=1400;
	private  static int rayon=largeur/2;
	private static int instancesMax=100;
	
	public static double density(ArrayList<Point> udg){
		return udg.size()/(Math.PI*rayon*rayon);
	}

	/**
	 * 
	 * @param nbPoints
	 * @param edgeThreshold
	 * @return an instance of points
	 */
	@SuppressWarnings("unchecked")
	public static ArrayList<Point> instanceGenerator(int nbPoints, int edgeThreshold){
		ArrayList<Point> result = new ArrayList<>();
		Point centre = new Point(longueur/2,largeur/2);

		Random generator = new Random();
		generator.setSeed(System.currentTimeMillis());

		while(result.size()<nbPoints){
			int x = generator.nextInt(longueur);
			int y = generator.nextInt(largeur);

			Point candidate = new Point(x,y);
			if(!isInCircle(candidate, centre) && !result.contains(candidate)) 
				continue;

			result.add(candidate);				
			if(result.size()==nbPoints){
				ArrayList<Point> resultCopy = (ArrayList<Point>)result.clone();
				for(Point p : resultCopy){
					while(Algorithms.neighbor(p, resultCopy, edgeThreshold).size()==0){
						result.remove(p);
						p= new Point(generator.nextInt(longueur), generator.nextInt(largeur));
						result.add(p);
					}
				}				
			}
		}
		return result;	
	}
	
	public static void allInstancesGenerator(int nbPoints , int edgeThreshold){
		for(int i=0 ;i<instancesMax; i++)
			printToFile("../S_MIS_CDS/tests/"+nbPoints+"/input_"+nbPoints+"_"+i+".points", 
					instanceGenerator(nbPoints, edgeThreshold));
			
	}

	public static boolean isInCircle(Point p , Point centre){
		return (p.distance(centre)<=rayon)?true:false;
	}

	public static void saveToFile(String filename,ArrayList<Point> result){
		int index=0;
		try {
			while(true){
				BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(filename+Integer.toString(index)+".points")));
				try {
					input.close();
				} catch (IOException e) {
					System.err.println("I/O exception: unable to close "+filename+Integer.toString(index)+".points");
				}
				index++;
			}
		} catch (FileNotFoundException e) {
			printToFile(filename+Integer.toString(index)+".points",result);
		}
	}
	
	public static  void printToFile(String filename,ArrayList<Point> points){
		try {
			PrintStream output = new PrintStream(new FileOutputStream(filename));
			for (Point p:points) output.println(Integer.toString((int)p.getX())+" "+Integer.toString((int)p.getY()));
			output.close();
		} catch (FileNotFoundException e) {
			System.err.println("I/O exception: unable to create "+filename);
		}
	}

	//FILE LOADER
	private static ArrayList<Point> readFromFile(String filename) {
		String line;
		String[] coordinates;
		ArrayList<Point> points=new ArrayList<Point>();
		try {
			BufferedReader input = new BufferedReader(
					new InputStreamReader(new FileInputStream(filename))
					);
			try {
				while ((line=input.readLine())!=null) {
					coordinates=line.split("\\s+");
					points.add(new Point(Integer.parseInt(coordinates[0]),
							Integer.parseInt(coordinates[1])));
				}
			} catch (IOException e) {
				System.err.println("Exception: interrupted I/O.");
			} finally {
				try {
					input.close();
				} catch (IOException e) {
					System.err.println("I/O exception: unable to close "+filename);
				}
			}
		} catch (FileNotFoundException e) {
			System.err.println("Input file not found.");
		}
		return points;
	}
	
	public static void main(String[] args) {
		//allInstancesGenerator(XXXXXXX, 55);
	}
	
}
