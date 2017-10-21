package s_mis;

import java.awt.Point;
/**
 * 
 * @author cb_mac
 *
 */
public class PointWithColor extends Point{

	private static final long serialVersionUID = 34567886545671L;
	private Coloring color ;
	private Point point ;
	
	public PointWithColor(Point p,Coloring color) {
		super(p);
		this.color=color;
		this.point=p ;
	}

	public Coloring getColor() {
		return color;}

	public void setColor(Coloring color) {
		this.color = color
		;}
	
	public Point getP(){return this.point;}
	
	@Override
	public String toString() {
		return "["+color+"]";
	}
}
