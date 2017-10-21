package steiner;

import java.awt.Point;

public class Edge implements Comparable<Edge>{

	private Point p , q ;
	private double poids ; 
	private int etiquete;
	
	public Edge(Point x ,Point y) {
		this.p=x ;
		this.q=y ;
		this.poids = x.distance(y);
		etiquete=-1 ; // tag
	}


	@Override
	public int compareTo(Edge o) {
		
		if (this.poids== o.poids) {
			return 0 ;
		}
		else return (this.poids>o.poids) ? 1:-1 ;
	}
	
	public double getPoids() {
		return poids;
	}
	
	public int getEtiquetes() {
		return etiquete;
	}
	
	public void setEtiquetes(int etiquetes) {
		this.etiquete = etiquetes;
	}
	
	public void setPoids(int poids) {
		this.poids = poids;
	}
	
	 public Point getP() {
		return p;
	}
	 
	 public Point getQ() {
		return q;
	}
}
