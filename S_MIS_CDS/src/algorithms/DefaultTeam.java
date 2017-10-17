package algorithms;

import java.awt.Point;
import java.util.ArrayList;

import s_mis.Algorithms;
import s_mis.Coloring;
import s_mis.PointWithColor;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class DefaultTeam {

	
	public ArrayList<Point> calculConnectedDominatingSet(ArrayList<Point> pts, int edgeThreshold) {

				ArrayList<PointWithColor> points = Algorithms.coloringProcess(pts, Coloring.WHITE);
				ArrayList<PointWithColor> MIS=new ArrayList<>();
				MIS = Algorithms.computeMisRef16(pts,edgeThreshold);
				
				System.out.println("Size : " + MIS.size()) ;
				
				//ArrayList<Point> MIS = stableMax(pts, edgeThreshold);
				 
				Utils util = new Utils();
				
				Tree2D arbre = util.calculSteiner(pts, edgeThreshold, Algorithms.convertion(MIS));
				//Tree2D arbre = util.calculSteiner(pts, edgeThreshold, (MIS));
		
				
				ArrayList<Point> mcds = Algorithms.unfold(arbre, new ArrayList<>());
				//System.out.println("size of MCDS: "+ mcds.size());
				
				//ArrayList<Point> mcds = s_mis.Algorithms.algorithmA(result, points, edgeThreshold);
				System.out.println("MCDS : "+mcds.size());
				
			
				
				return Algorithms.convertion(MIS) ;

//		ArrayList<Point> result = Algorithms.stableMax(points, edgeThreshold);
//		ArrayList<PointWithColor> blackPoint = Algorithms.coloringProcess(result,Coloring.BLACK);
//		ArrayList<PointWithColor> greyPoint = Algorithms.tagPointGrey(points, result);
//		ArrayList<PointWithColor> blueTag = new ArrayList<>();
//		blueTag = Algorithms.algoLi(blackPoint, greyPoint, points, edgeThreshold);
//		System.out.println(blueTag.size());
//
//		System.out.println(result.size());
//		for(PointWithColor p : blueTag)
//			result.add(p);
//		return result;

	}

	//FILE PRINTER
	private void saveToFile(String filename,ArrayList<Point> result){
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
	private void printToFile(String filename,ArrayList<Point> points){
		try {
			PrintStream output = new PrintStream(new FileOutputStream(filename));
			int x,y;
			for (Point p:points) output.println(Integer.toString((int)p.getX())+" "+Integer.toString((int)p.getY()));
			output.close();
		} catch (FileNotFoundException e) {
			System.err.println("I/O exception: unable to create "+filename);
		}
	}

	//FILE LOADER
	private ArrayList<Point> readFromFile(String filename) {
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
}
