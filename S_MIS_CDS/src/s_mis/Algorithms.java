package s_mis;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

import steiner.Tree2D;

public class Algorithms {

	/**
	 * 
	 * @param udg
	 * @param seuil
	 * @return list of black nodes (MIS)
	 * @throws InterruptedException 
	 */
	public static ArrayList<PointWithColor> computeMisRef16(ArrayList<Point> udg , int seuil) {	

		ArrayList<PointWithColor> whiteNode = coloringProcess(udg, Coloring.WHITE);
		ArrayList<PointWithColor> MIS = new ArrayList<>();
		ArrayList<PointWithColor> candidat = new ArrayList<>();
		
		Collections.shuffle(whiteNode);
		candidat.add(whiteNode.get(0));

		while(!candidat.isEmpty()){
			PointWithColor random =candidat.remove(0);
			if(random.getColor()==Coloring.GRAY) 
				continue ;

			random.setColor(Coloring.BLACK);
			MIS.add(random);
			
			for(PointWithColor pt: whiteNode){ // set all random neighour in gray
				if (pt.distance(random)< seuil)
					pt.setColor(Coloring.GRAY);		
			}

			for(PointWithColor pt : whiteNode){
				if (pt.distance(random)< seuil){
					for(PointWithColor ptv : whiteNode){
						if(ptv.distance(pt)< seuil && ptv.getColor()==Coloring.WHITE && !candidat.contains(ptv)){
							candidat.add(ptv);
						}
					}
				}
			}	
		}	
		
		//be sure that node are really black before apply s_mis on
		for(PointWithColor p : MIS){ 
			p.setColor(Coloring.BLACK);
		}
		
		return MIS;
	}
	

	/**
	 * 
	 * @param udg
	 * @param domSet
	 * @param edgeThreshold
	 * @return new MCDS with some nodes removed
	 */
	public static ArrayList<Point> optimise(ArrayList<Point> udg, ArrayList<Point> domSet, int edgeThreshold){
        ArrayList<Point> copyDomSet = new ArrayList<>(domSet);
        ArrayList<Point> pointsWithoutDomSet = new ArrayList<>(udg);
        pointsWithoutDomSet.removeAll(domSet);
        
        for(Point p : domSet) {
            ArrayList<Point> neighbor = neighbor(p, domSet, edgeThreshold);
            if(neighbor.size()==1) {
                if(neighbor(p, pointsWithoutDomSet, edgeThreshold).size()<1) {
                    copyDomSet.remove(p);
                }
                
                Point parent = neighbor.get(0);
                ArrayList<Point> leafNeighbor = neighbor(p, pointsWithoutDomSet, edgeThreshold);
                if(!leafNeighbor.isEmpty()) {
                    boolean toRemove = false;
                    for(Point tmp : leafNeighbor) {
                        if(tmp.distanceSq(parent) > edgeThreshold*edgeThreshold) {
                            toRemove = false;
                            break;
                        }
                        else
                            toRemove = true;
                    }
                    if(toRemove)
                        copyDomSet.remove(p);
                }
            }
        }
        return copyDomSet;
    }

	/**
	 * 
	 * @param points colored
	 * @return normal point 
	 */
	public static ArrayList<Point> convertion(ArrayList<PointWithColor> points){
		ArrayList<Point> result = new ArrayList<>();
		for(PointWithColor p : points )
			result.add(p.getP());
		return result ;
	}

	/**
	 * 
	 * @param udg
	 * @param color
	 * @return udg colored with color 
	 */
	public static ArrayList<PointWithColor> coloringProcess(ArrayList<Point> udg , Coloring color){
		ArrayList<PointWithColor> udgColored =new ArrayList<>();
		for (Point p : udg) 
			udgColored.add(new PointWithColor(p, color));
		return udgColored;
	}


	/**
	 * 
	 * @param tree
	 * @param result
	 * @return list of Point from a tree 
	 */
	public static ArrayList<Point> unfold(Tree2D tree , ArrayList<Point> result){
		result.add(tree.getRoot());
		if(tree.getSubTrees().size() == 0) 
			return result;

		for(int i = 0; i < tree.getSubTrees().size();i++)
			unfold(tree.getSubTrees().get(i),result);

		return result;

	}


	
	/**
	 * 
	 * @param points
	 * @param edgeThreshold
	 * @return MIS 
	 */
	@SuppressWarnings("unchecked")
	public static ArrayList<Point> stableMax(ArrayList<Point> points, int edgeThreshold){

		ArrayList<Point> copy = (ArrayList<Point>) points.clone();
		ArrayList<Point> result = new ArrayList<>();
		ArrayList<Point> teste = (ArrayList<Point>) points.clone();

		Point pointMax = copy.get(0);
		int degreMax = getDegre(pointMax, copy, edgeThreshold);
		
		while(!teste.isEmpty()){
			pointMax = copy.get(0);
			degreMax = getDegre(pointMax, copy, edgeThreshold);
			for(Point p: teste){
				if(degreMax <= getDegre(p, copy, edgeThreshold)){
					degreMax = getDegre(p, copy, edgeThreshold);
					pointMax = p;
				}		
			}
			result.add(pointMax);
			copy.remove(pointMax);
			teste.remove(pointMax);
			if(!isValidStable(result, edgeThreshold)){
				result.remove(pointMax);
				copy.add(pointMax);
			}
		}
		return result;
	}


	/**
	 * 
	 * @param a MIS candidate
	 * @param edgeThreshold
	 * @return true if is a MIS
	 */
	public static boolean isValidStable(ArrayList<Point>  candidat , int edgeThreshold){
		if(candidat == null)
			return true;
		
		for(Point p1: candidat){
			for(Point p2: candidat){
				if(p1.equals(p2)) continue;
				if(p1.distance(p2) < edgeThreshold){
					return false;
				}
			}
		}
		return true;
	}


	/**
	 * 
	 * @param p
	 * @param vertices
	 * @param edgeThreshold
	 * @return number of neighbor of a point
	 */
	public static int getDegre(Point p, ArrayList<Point> vertices, int edgeThreshold){
		return neighbor(p, vertices, edgeThreshold).size();
	}

	
	/**
	 * 
	 * @param p
	 * @param vertices
	 * @param edgeThreshold
	 * @return list of neighbor
	 */
	public static ArrayList<Point>  neighbor(Point p, ArrayList<Point> vertices, int edgeThreshold){
		ArrayList<Point> result = new ArrayList<Point>();
		for (Point point:vertices) 
			if (point.distance(p)<edgeThreshold && !point.equals(p)) 
				result.add((Point)point.clone());

		return result;
	}

	@SuppressWarnings("unchecked")
	public static ArrayList<PointWithColor> algoLi(ArrayList<PointWithColor> black, ArrayList<PointWithColor> grey, ArrayList<Point> points, int edgeThreshold){
		ArrayList<PointWithColor> blackNeighbor;
		ArrayList<PointWithColor> blue = new ArrayList<>();
		ArrayList<PointWithColor> greyCopy = (ArrayList<PointWithColor>) grey.clone();
		for(int i = 5; i > 1; i--){
			greyCopy = (ArrayList<PointWithColor>) grey.clone();
			for(PointWithColor pt:greyCopy){
				blackNeighbor = neighborTagRetour(pt, black, edgeThreshold);
				if(blue.isEmpty()){// cas initial
					if(blackNeighbor.size()>=i){
						blue.add(new PointWithColor(pt,Coloring.BLUE));
						grey.remove(pt);
					}
					else continue;
				}
				else{//cas compliqué
					if(countGraph(black, blackNeighbor, blue, edgeThreshold)>=i){
						blue.add(new PointWithColor(pt,Coloring.BLUE));
						grey.remove(pt);
					}
				}
			}
			
		}
		return blue;
	}
	
	public static ArrayList<PointWithColor> constructGraph(ArrayList<PointWithColor> graph, ArrayList<PointWithColor> black, ArrayList<PointWithColor> blue, PointWithColor p, int edgeThreshold){
		if(graph.contains(p)){
			return graph;
		}
		if(p.getColor() == Coloring.BLACK){
			graph.add(p);
			for(PointWithColor p2 : neighborTagRetour(p, blue, edgeThreshold)){
				constructGraph(graph, black, blue, p2, edgeThreshold);
			}
		}
		else if(p.getColor() == Coloring.BLUE){
			graph.add(p);
			for(PointWithColor p2 : neighborTagRetour(p, black, edgeThreshold)){
				constructGraph(graph, black, blue, p2, edgeThreshold);
			}
		}
		return graph;
	}
	
	public static int countGraph(ArrayList<PointWithColor> black, ArrayList<PointWithColor> blackNeighbor, ArrayList<PointWithColor> blue, int edgeThreshold){
		ArrayList<PointWithColor> blueAndBlack = new ArrayList<>(black);
		blueAndBlack.addAll(blue);
		HashMap<PointWithColor, ArrayList<PointWithColor>> graphs = new HashMap<>();
		boolean contenu = false;
		for(PointWithColor p: blackNeighbor){
			ArrayList<PointWithColor> graph = new ArrayList<>();
			contenu = false;
			for(PointWithColor p2 : blackNeighbor){
				if(graphs.containsKey(p2)&&graphs.get(p2).contains(p)){
					contenu = true;
					continue;
				}
			}
			if(contenu)
				continue;
			graphs.put(p, constructGraph(graph, black, blue, p, edgeThreshold));
		}
		return graphs.size();
	}

	@SuppressWarnings("unchecked")
	public static ArrayList<PointWithColor> tagPointGrey(ArrayList<Point> points, ArrayList<Point> stable){
		ArrayList<Point> notStable = (ArrayList<Point>) points.clone();
		notStable.removeAll(stable);
		ArrayList<PointWithColor> notStableRes = new ArrayList<>();
		for(Point p : notStable){
			notStableRes.add(new PointWithColor(p, Coloring.GRAY));
		}
		return notStableRes;
	}
	
	public static ArrayList<Point> neighbor2Seuil(Point p, ArrayList<Point> vertices, int seuil){
		ArrayList<Point> result = new ArrayList<Point>();
		for (Point point:vertices) 
			if (point.distance(p)<(2*seuil) && !point.equals(p)) 
				result.add((Point)point.clone());

		return result;
	}

	public static ArrayList<Point> neighborTag(Point p, ArrayList<PointWithColor> vertices, int seuil){
		ArrayList<Point> result = new ArrayList<Point>();
		for (PointWithColor point:vertices) 
			if (point.distance(p)<seuil && !point.equals(p)) 
				result.add((Point)point.clone());

		return result;
	}

	public static ArrayList<PointWithColor> neighborTagRetour(PointWithColor p, ArrayList<PointWithColor> vertices, int seuil){
		ArrayList<PointWithColor> result = new ArrayList<PointWithColor>();
		for (PointWithColor point:vertices)
			if (point.distance(p)<seuil && !point.equals(p)) 
				result.add(point);

		return result;
	}

}
