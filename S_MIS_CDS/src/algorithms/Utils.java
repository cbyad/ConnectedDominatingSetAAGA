package algorithms;



import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Utils {

	public int[][] calculShortestPaths(ArrayList<Point> points,int edgeThreshold) {
		int[][] paths = new int[points.size()][points.size()];
		for (int i = 0; i < paths.length; i++) {
			for (int j = 0; j < paths.length; j++) {
				paths[i][j] = i;
			}
		}
		double[][] distance = new double[points.size()][points.size()];
		// 1664 1337 OxBADC0DE
		for (int i = 0; i < paths.length; i++) {
			for (int j = 0; j < paths.length; j++) {
				if (distancePoint(points.get(i), points.get(j)) < edgeThreshold){
					distance[i][j] = distancePoint(points.get(i), points.get(j));
					paths[i][j]=j;
				}
				else
					distance[i][j] = Double.POSITIVE_INFINITY;
			}
		}

		for (int k = 0; k < points.size(); k++) {
			for (int i = 0; i < points.size(); i++) {
				for (int j = 0; j < points.size(); j++) {
					if (distance[i][k] + distance[k][j] < distance[i][j]) {
						distance[i][j] = distance[i][k] + distance[k][j];
						paths[i][j] = paths[i][k];
					}
				}
			}
		}
		return paths;
	}

	public Tree2D calculSteiner(ArrayList<Point> points, int edgeThreshold, ArrayList<Point> hitPoints) {
		int[][] paths = calculShortestPaths(points, edgeThreshold);
		ArrayList<CouplePoint> k = exercice2_list(hitPoints);
		ArrayList<Point> p = new ArrayList<Point>(hitPoints);
		int debut;
		int fin;
		for(CouplePoint cp : k){
			debut = points.indexOf(cp.getP1());
			fin = points.indexOf(cp.getP2());
			while(paths[debut][fin]!=fin){
				p.add(points.get(paths[debut][fin]));
				debut = paths[debut][fin];
			}
			
		}

    return exercice2(p);
  }

	public double distancePoint(Point p1, Point p2) {
		double distance;
		distance = Math.sqrt(Math.pow(p1.getX() - p2.getX(), 2)
				+ Math.pow(p1.getY() - p2.getY(), 2));
		return distance;
	}

public Tree2D exercice2(ArrayList<Point> points) {
	final Map<CouplePoint, Double> map = new HashMap<>();
	double value;
	ArrayList<CouplePoint> solution = new ArrayList<>();
	for (Point p : points) {
		for (Point p2 : points) {
			CouplePoint couple = new CouplePoint(p, p2);
			value = distancePoint(p, p2);
			map.put(couple, value);
		}
	}

	final List<Entry<CouplePoint, Double>> entries = new ArrayList<Entry<CouplePoint, Double>>(
			map.entrySet());
	Collections.sort(entries, new Comparator<Entry<CouplePoint, Double>>() {
		public int compare(final Entry<CouplePoint, Double> e1,
				final Entry<CouplePoint, Double> e2) {
			return e1.getValue().compareTo(e2.getValue());
		}
	});

	ArrayList<Point> points2 = (ArrayList<Point>) points.clone();
	int cycle[] = new int[points2.size()];
	int i;

	for (i = 0; i < points2.size(); i++) {
		cycle[i] = i;
	}

	int i1;
	int i2;
	int temp;
	while (!entries.isEmpty()) {
		i1 = points2.indexOf(entries.get(0).getKey().getP1());
		i2 = points2.indexOf(entries.get(0).getKey().getP2());
		if (cycle[i1] == cycle[i2]) {
			entries.remove(0);
		} else {
			solution.add(entries.get(0).getKey());
			entries.remove(0);
			temp = cycle[i2];
			for (i = 0; i < points2.size(); i++) {
				if (cycle[i] == temp) {
					cycle[i] = cycle[i1];
				}
			}
		}
	}
	
	Point pSol = solution.get(1).getP1();
	return edgeToTree(pSol ,solution );
}

public ArrayList<CouplePoint> exercice2_list(ArrayList<Point> points) {
	final Map<CouplePoint, Double> map = new HashMap<>();
	double value;
	ArrayList<CouplePoint> solution = new ArrayList<>();
	for (Point p : points) {
		for (Point p2 : points) {
			CouplePoint couple = new CouplePoint(p, p2);
			value = distancePoint(p, p2);
			map.put(couple, value);
		}
	}

	final List<Entry<CouplePoint, Double>> entries = new ArrayList<Entry<CouplePoint, Double>>(
			map.entrySet());
	Collections.sort(entries, new Comparator<Entry<CouplePoint, Double>>() {
		public int compare(final Entry<CouplePoint, Double> e1,
				final Entry<CouplePoint, Double> e2) {
			return e1.getValue().compareTo(e2.getValue());
		}
	});

	ArrayList<Point> points2 = (ArrayList<Point>) points.clone();
	int cycle[] = new int[points2.size()];
	int i;

	for (i = 0; i < points2.size(); i++) {
		cycle[i] = i;
	}

	int i1;
	int i2;
	int temp;
	while (!entries.isEmpty()) {
		i1 = points2.indexOf(entries.get(0).getKey().getP1());
		i2 = points2.indexOf(entries.get(0).getKey().getP2());
		if (cycle[i1] == cycle[i2]) {
			entries.remove(0);
		} else {
			solution.add(entries.get(0).getKey());
			entries.remove(0);
			temp = cycle[i2];
			for (i = 0; i < points2.size(); i++) {
				if (cycle[i] == temp) {
					cycle[i] = cycle[i1];
				}
			}
		}
	}
	
	return solution;
}
public Tree2D edgeToTree(Point p , ArrayList<CouplePoint> l){
	
	ArrayList<Tree2D> children =  new ArrayList<Tree2D>() ; 
	
	if (l.isEmpty()) {
		return  new Tree2D(p, children) ;
	}
	
	else {
		
		ArrayList<CouplePoint> voisins = recupAretes(p,l);
		l.removeAll(voisins);
		
		for (CouplePoint e : voisins) {
			if(e.getP1()==p){
				children.add(edgeToTree((e.getP2()), l));
			}
			else
				children.add(edgeToTree((e.getP1()), l));
			
		}
		
	}
	return new Tree2D(p, children) ;
	
}

 public ArrayList<CouplePoint> recupAretes(Point p , ArrayList<CouplePoint> l){
	 
	 ArrayList< CouplePoint> listeAretes = new ArrayList<CouplePoint>() ;
	 
	 for (CouplePoint liste : l) {
		 if (p==liste.getP1() || p == liste.getP2()) {
			 listeAretes.add(liste);
		}
		 
	}
	 return listeAretes ;
 }
}
