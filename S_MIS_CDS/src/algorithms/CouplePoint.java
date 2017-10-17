package algorithms;

import java.awt.Point;

public class CouplePoint {
	private Point p1;
	private Point p2;

	public CouplePoint(Point p1, Point p2) {
		this.setP1(p1);
		this.setP2(p2);
	}

	public Point getP1() {
		return p1;
	}

	public void setP1(Point p1) {
		this.p1 = p1;
	}

	public Point getP2() {
		return p2;
	}

	public void setP2(Point p2) {
		this.p2 = p2;
	}

}
