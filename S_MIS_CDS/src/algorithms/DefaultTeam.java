package algorithms;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;

import s_mis.Algorithms;
import s_mis.Coloring;
import s_mis.PointWithColor;
import steiner.Steiner;
import steiner.Tree2D;
;

public class DefaultTeam {

	
	public ArrayList<Point> calculConnectedDominatingSet(ArrayList<Point> pts, int edgeThreshold) throws IOException {

				ArrayList<PointWithColor> MIS=new ArrayList<>();
				MIS = Algorithms.computeMisRef16(pts,edgeThreshold);
				
				System.out.println(pts.size());
				System.out.println("Size : " + MIS.size()) ;
				
				Steiner util = new Steiner();
				
				Tree2D arbre = util.calculSteiner(pts, edgeThreshold, Algorithms.convertion(MIS));
				ArrayList<Point> mcds = Algorithms.unfold(arbre, new ArrayList<>());
				System.out.println("MCDS : "+mcds.size());
				return mcds;

//				ArrayList<PointWithColor> blackPoint = new ArrayList<>();
//				blackPoint=Algorithms.computeMisRef16(pts,edgeThreshold);
//				
//				
//				ArrayList<PointWithColor> greyPoint = Algorithms.tagPointGrey(pts, Algorithms.convertion(blackPoint));
//				ArrayList<PointWithColor> blueTag = new ArrayList<>();
//				blueTag = Algorithms.algoLi(blackPoint, greyPoint, pts, edgeThreshold);		
//				
//				for(PointWithColor p : blueTag)
//					blackPoint.add(p);
//
//				return Algorithms.convertion(blackPoint);
//				
//				
				
//		ArrayList<Point> result = Algorithms.stableMax(pts, edgeThreshold);
//		ArrayList<PointWithColor> blackPoint = Algorithms.coloringProcess(result,Coloring.BLACK);
//		ArrayList<PointWithColor> greyPoint = Algorithms.tagPointGrey(pts, result);
//		ArrayList<PointWithColor> blueTag = new ArrayList<>();
//		blueTag = Algorithms.algoLi(blackPoint, greyPoint, pts, edgeThreshold);
//		System.out.println(blueTag.size());
//
//		System.out.println(result.size());
//		for(PointWithColor p : blueTag)
//			result.add(p);
//		return result;

//		ArrayList<PointWithColor> all = Algorithms.coloringProcess(pts,Coloring.WHITE);
//		ArrayList<PointWithColor> blackPoint = Algorithms.computeMisRef16(pts,edgeThreshold);
//
//		for(PointWithColor b : blackPoint){
//			for(PointWithColor q : all){
//				if(b.getP().equals(q.getP()))
//					q.setColor(Coloring.BLACK);
//			}
//		}
//		
//		BufferedWriter bw = new BufferedWriter(new FileWriter("../S_MIS_CDS/result1.txt"));
//		
//		
//		for(PointWithColor p :all){
//			bw.write(p.toString());
//			bw.write("\n");
//		}
//		bw.close();
//		
//		return Algorithms.convertion(blackPoint);
	
	}


	//FILE PRINTER
	
	}

