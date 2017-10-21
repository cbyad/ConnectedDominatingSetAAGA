package steiner;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;

public class Steiner {

	public int[][] calculShortestPaths(ArrayList<Point> points,int edgeThreshold) {
		/* floyd warshall */

		int[][] paths = new int[points.size()][points.size()];
		double[][] dist = new double[points.size()][points.size()];

		for (int i = 0; i < dist.length; i++) {
			for (int j = 0; j < dist.length; j++) {
				paths[i][j] = j;

				if (isEdge(points.get(i), points.get(j), edgeThreshold)) {
					dist[i][j] = points.get(i).distance(points.get(j));
				} 
				else {
					dist[i][j] = Double.POSITIVE_INFINITY;
				}
			}
		}
		for (int k = 0; k < paths.length; k++) {
			for (int i = 0; i < paths.length; i++) {
				for (int j = 0; j < paths.length; j++) {
					if (dist[i][j] > dist[i][k] + dist[k][j]) {
						paths[i][j] = paths[i][k];
						dist[i][j] = dist[i][k] + dist[k][j];
					}
				}
			}
		}
		return paths;
	}

	public Tree2D calculSteiner(ArrayList<Point> points, int edgeThreshold, ArrayList<Point> hitPoints) {
		int[][] paths = calculShortestPaths(points, edgeThreshold);
		int tmp, dest;

		ArrayList<Edge> k = kruskalList(hitPoints);
		ArrayList<Point> p = new ArrayList<Point>(hitPoints);

		for(Edge e: k) {
			if(!isEdge(e.getP(), e.getQ(), edgeThreshold)) {
				tmp = points.indexOf(e.getP());
				dest = points.indexOf(e.getQ());
				while(paths[tmp][dest] != dest) {
					tmp = paths[tmp][dest];
					p.add(points.get(tmp));
				}
			}
		}
		return kruskal(p);
	}

	public boolean isEdge(Point p, Point q, int edgeThreshold) {
		return (p.distance(q) <= edgeThreshold);
	}

	public Tree2D kruskal(ArrayList<Point> points) {

		ArrayList<Point> remaining = new ArrayList<Point>(points);
		ArrayList<Edge> edges = new ArrayList<Edge>();

		/* listes des arcs */
		for (int i = 0; i < remaining.size(); i++) {
			Point p = remaining.get(i);

			for (int j = i + 1; j < remaining.size(); j++) {

				Point q = remaining.get(j);
				edges.add(new Edge(p, q));
			}
		}
		/* trie des arcs */
		Collections.sort(edges);

		/* nouvel liste d'arcs */
		ArrayList<Edge> solution = new ArrayList<Edge>();
		int cpt = 0;
		for (int i = 0; i < edges.size(); i++) {

			int tagP = getTags(edges.get(i).getP(), edges);
			int tagQ = getTags(edges.get(i).getQ(), edges);

			if (tagP==-1 && tagQ==-1) // p et q non marqués
			{
				edges.get(i).setEtiquetes(cpt++);
				solution.add(edges.get(i));
			}

			else if (tagP == -1) { // p non  marqué
				edges.get(i).setEtiquetes(tagQ);
				solution.add(edges.get(i));
			}

			else if (tagQ == -1) { // q non marqué
				edges.get(i).setEtiquetes(tagP);
				solution.add(edges.get(i));
			}

			else if (tagP!=-1 && tagQ!=-1 ){ // p && q marqué  

				if (tagP!=tagQ) {  // tag diff
					edges.get(i).setEtiquetes(tagP);
					solution.add(edges.get(i));

					for (Edge sol : solution) {
						if (tagQ==getTags(sol.getP(), solution) || tagQ==getTags(sol.getQ(), solution)) {
							sol.setEtiquetes(tagP);
						}
					}
				}
				if (tagP==tagQ) {// tag egaux 
					/* rien a faire car cree un cycle*/
				}
			}
		}
		/*convertion en arbre */
		Point pSol = solution.get(1).getP();
		return edgeToTree(pSol ,solution ) ;
	}

	public int getTags(Point p, ArrayList<Edge> edges) {
		for (Edge edge : edges) {
			if (p == edge.getP() || p == edge.getQ()) {
				return edge.getEtiquetes();
			}
		}
		return -1;
	}

	public Tree2D edgeToTree(Point p , ArrayList<Edge> l){
		ArrayList<Tree2D> children =  new ArrayList<Tree2D>() ; 
		if (l.isEmpty()) {
			return  new Tree2D(p, children) ;
		}
		else {
			ArrayList<Edge> voisins = recupAretes(p,l);
			l.removeAll(voisins);

			for (Edge e : voisins) {
				children.add(edgeToTree((e.getP()==p? e.getQ():e.getP()), l));
			}
		}
		return new Tree2D(p, children) ;
	}

	public ArrayList<Edge> recupAretes(Point p , ArrayList<Edge> l){
		ArrayList< Edge> listeAretes = new ArrayList<Edge>() ;

		for (Edge liste : l) {
			if (p==liste.getP() || p == liste.getQ()) {
				listeAretes.add(liste);
			}
		}
		return listeAretes ;
	}

	public ArrayList<Edge> kruskalList(ArrayList<Point> points) {

		ArrayList<Point> remaining = new ArrayList<Point>(points);
		ArrayList<Edge> edges = new ArrayList<Edge>();

		/* listes des arcs */
		for (int i = 0; i < remaining.size(); i++) {
			Point p = remaining.get(i);
			for (int j = i + 1; j < remaining.size(); j++) {
				Point q = remaining.get(j);
				edges.add(new Edge(p, q));
			}
		}

		/* trie des arcs */
		Collections.sort(edges);

		/* nouvel liste d'arcs */
		ArrayList<Edge> solution = new ArrayList<Edge>();
		int cpt = 0;
		for (int i = 0; i < edges.size(); i++) {

			int tagP = getTags(edges.get(i).getP(), edges);
			int tagQ = getTags(edges.get(i).getQ(), edges);

			if (tagP==-1 && tagQ==-1) // p et q non marqués
			{
				edges.get(i).setEtiquetes(cpt++);
				solution.add(edges.get(i));
			}

			else if (tagP == -1) { // p non  marqué
				edges.get(i).setEtiquetes(tagQ);
				solution.add(edges.get(i));
			}

			else if (tagQ == -1) { // q non marqué
				edges.get(i).setEtiquetes(tagP);
				solution.add(edges.get(i));
			}

			else if (tagP!=-1 && tagQ!=-1 ){ // p && q marqué  

				if (tagP!=tagQ) {  // tag diff
					edges.get(i).setEtiquetes(tagP);
					solution.add(edges.get(i));

					for (Edge sol : solution) {
						if (tagQ==getTags(sol.getP(), solution) || tagQ==getTags(sol.getQ(), solution)) {
							sol.setEtiquetes(tagP);
						}
					}
				}
				if (tagP==tagQ) {// tag egaux 
					/* rien a faire car cree un cycle*/
				}
			}
		}
		return solution;
	}
}
