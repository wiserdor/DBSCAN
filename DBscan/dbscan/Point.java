package dbscan;

public class Point {
	private double x;
	private double y;
	private double z;

	/**
	 * 
	 * @param a
	 * @param b
	 * @param c
	 */
	public Point(double a, double b, double c) {
		x = a;
		y = b;
		z = c;
	}
	//Copy c'tor
	public Point(Point point) {
		this.x=point.getX();
		this.y=point.getY();
		this.z=point.getZ();
	}

	/**
	 * 
	 * @return
	 */
	public double getX() {
		return x;
	}

	/**
	 * 
	 * @return
	 */
	public double getY() {
		return y;
	}

	public double getZ() {
		return z;
	}
}
