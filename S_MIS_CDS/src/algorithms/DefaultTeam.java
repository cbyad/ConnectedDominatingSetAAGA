package algorithms;

import java.awt.Point;
import java.util.ArrayList;

import s_mis.Algorithms;
import s_mis.Coloring;
import s_mis.GeneratorRandom;
import s_mis.PointWithColor;
;

public class DefaultTeam {

	
	public ArrayList<Point> calculConnectedDominatingSet(ArrayList<Point> pts, int edgeThreshold) {

				ArrayList<PointWithColor> points = Algorithms.coloringProcess(pts, Coloring.WHITE);
				ArrayList<PointWithColor> MIS=new ArrayList<>();
				MIS = Algorithms.computeMisRef16(pts,edgeThreshold);
				
				System.out.println(pts.size());
				System.out.println("Size : " + MIS.size()) ;
				
				//ArrayList<Point> MIS = stableMax(pts, edgeThreshold);
				 
				Utils util = new Utils();
				
				Tree2D arbre = util.calculSteiner(pts, edgeThreshold, Algorithms.convertion(MIS));
				//Tree2D arbre = util.calculSteiner(pts, edgeThreshold, (MIS));
		
				
				ArrayList<Point> mcds = Algorithms.unfold(arbre, new ArrayList<>());
				//System.out.println("size of MCDS: "+ mcds.size());
				
				//ArrayList<Point> mcds = s_mis.Algorithms.algorithmA(result, points, edgeThreshold);
				System.out.println("MCDS : "+mcds.size());
				
//				
				
				
//				return Algorithms.convertion(MIS) ;
				return mcds;

				
				
				
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
	
}
