package dbscan;

import java.util.Iterator;
import java.util.Vector;

public class Utility {
	dbscan owner;
	public Vector<Point> newList = new Vector<Point>();

	Utility(dbscan obj) {
		owner = obj;
	}

	public Vector<Point> VisitList = new Vector<Point>();

	public double getDistance(Point p, Point q) {
		try {
			double dx = p.getX() - q.getX();

			double dy = p.getY() - q.getY();

			double dz = p.getZ() - q.getZ();

			double distance = Math.sqrt(dx * dx + dy * dy + dz * dz);

			return distance;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return (0);
		}
	}

	/**
	 * neighbourhood points of any point p
	 **/

	public Vector<Point> getNeighbours(Point p) {
		Point q = null;

		Vector<Point> neigh = new Vector<Point>();
		Iterator<Point> points = owner.pointList.iterator();
		while (points.hasNext()) {
			q = points.next();
			if (getDistance(p, q) <= owner.e) {
				neigh.add(q);
			}
		}
		return neigh;
	}

	public void Visited(Point d) {
		VisitList.add(d);
	}

	public boolean isVisited(Point c) {
		if (VisitList.contains(c)) {
			return true;
		} else {
			return false;
		}
	}

	public Vector<Point> Merge(Vector<Point> a, Vector<Point> b) {
		Iterator<Point> it5 = b.iterator();
		while (it5.hasNext()) {
			Point t = it5.next();
			if (!a.contains(t)) {
				a.add(t);
			}
		}
		return a;
	}

	// Returns PointsList to DBscan.java

	public Vector<Point> getList() {
		// Implementation of reading list from file
		newList.clear();

		// read from file

		return newList;
	}

	public Boolean equalPoints(Point m, Point n) {
		if ((m.getX() == n.getX()) && (m.getY() == n.getY()) && (m.getZ() == n.getZ()))
			return true;
		else
			return false;
	}
}
