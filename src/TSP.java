import java.awt.Point;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Stack;

public class TSP {
	public ArrayList<Point> blackPoints;

	public TSP(int[][] image) {
		blackPoints = new ArrayList<Point>();
		for (int i = 0; i < image.length; i++) {
			for (int j = 0; j < image[i].length; j++) {
				if (image[i][j] == 0) {
					blackPoints.add(new Point(i, j));
				}
			}
		}
		long seed = System.nanoTime();
		Collections.shuffle(blackPoints, new Random(seed));
		LinesDrawingExample temp = new LinesDrawingExample();
		blackPoints.subList(temp.numPoints, blackPoints.size() - 1).clear();
	}

	public Stack<Point> findShortestPath() {
		Stack<Point> path = new Stack<Point>();

		Point currPoint = blackPoints.get(0);

		while (blackPoints.size() > 0) {
			blackPoints.remove(currPoint);
			int shortestDist = 0;
			for (int i = 0; i < blackPoints.size(); i++) {
				if (currPoint.distance(blackPoints.get(i)) < currPoint.distance(blackPoints.get(shortestDist))) {
					shortestDist = i;
				}
			}
			currPoint = blackPoints.get(shortestDist);
			path.push(blackPoints.get(shortestDist));
			blackPoints.remove(shortestDist);

		}

		return path;
	}
	
	public ArrayList<Point> removeIntersections(ArrayList<Point> path) {
		ArrayList<Point> newPath = new ArrayList<Point>();
		for(Point point : path) {
			newPath.add(point);
		}
		int pathSize = path.size();
		boolean swapped = false;
		while (!swapped) {
			boolean swapped2 = false;
			for(int i = 0; i < pathSize; i++) {
				for(int j = i + 1; j < pathSize; j++) {
					if(i != j) {
					if(swapBetter(i, j, newPath)) {
						int cnt = 0;
						for(Point point : swap(i, j, newPath)) {
							if (cnt < 5000)
								newPath.set(cnt, point);
							cnt++;
						}
						swapped2 = true;
					}
					}
				}
			}
			swapped = !swapped2; 
		}
		return newPath; 
	}

	private boolean swapBetter(int i, int j, ArrayList<Point> path) {
		if(i == 0 && j == path.size() - 1) {
			return false;
		}
		Point a = path.get(i);
		Point b = (i != 0) ? path.get(i - 1) : path.get(path.size() - 1);
		Point c = path.get(j);
		Point d = (j != path.size() - 1) ? path.get(j + 1) : path.get(0); 
		
		double currDist1 = a.distance(b);
		double currDist2 = c.distance(d);
		
		double newDist1 = b.distance(c);
		double newDist2 = a.distance(d);
		
		/* if(Line2D.linesIntersect(a.getX(), a.getY(), b.getX(), b.getY(), c.getX(), c.getY(), d.getX(), d.getY())) {
			return true;
		} */
		
		if(currDist1 + currDist2 > newDist1 + newDist2) {
			//System.out.println("Swapped");
			return true;
		}	
		return false;
	}

	private ArrayList<Point> swap(int i, int j, ArrayList<Point> path) {
		ArrayList<Point> newPath = new ArrayList<Point>();
		for(int k = 0; k < i; k++) {
			newPath.add(path.get(k));
		}
		for(int k = j; k >= i; k--) {
			newPath.add(path.get(k));
		}
		for(int k = j + 1; k < path.size() - 1; k++) {
			newPath.add(path.get(k));
		}
		
		return newPath; 
	}
}
/*
	public ArrayList<Point> removeIntersections(ArrayList<Point> p) {
		ArrayList<Point> path = p;
		// Get path size
		int pathSize = path.size();
		// Get path length
		// int dist = findDist(path);
		// repeat until no improvements are made
		double minchange = -1;
		while (minchange < 0) {
			minchange = 0;
			for (int i = 1; i < pathSize - 1; i++) {
				for (int j = 1; j < pathSize - 1; j++) {
					if (i != j) {
						double change = path.get(i).distance(path.get(j)) + path.get(i + 1).distance(path.get(j + 1))
								- path.get(i).distance(path.get(i + 1)) - path.get(j).distance(path.get(j + 1));
						if (minchange > change) {
							minchange = change;
							path = swap(path, i, j);
						}
					}
				}
			}
		}
		return path;
	}
	public ArrayList<Point> swap(ArrayList<Point> path, int i, int j) {
		ArrayList<Point> newPath = new ArrayList<Point>();
		if(i < j) {
		for(int k = 0; k < i; k++) {
			newPath.add(path.get(k));
		}
		for(int k = j; k == i; k--) {
			newPath.add(path.get(k));
		}
		for(int k = j + 1; k < path.size() - 1; k++) {
			newPath.add(path.get(k));
		}
		} else {
			for(int k = 0; k < j; k++) {
				newPath.add(path.get(k));
			}
			for(int k = i; k == j; k--) {
				newPath.add(path.get(k));
			}
			for(int k = i + 1; k < path.size() - 1; k++) {
				newPath.add(path.get(k));
			}
		}
		return newPath; 
	}
}
*/
/*
	public ArrayList<Point> removeIntersections(ArrayList<Point> path) {
		//Get path size
		int pathSize = path.size();
		
		//repeat until no improvements are made
		int improve = 0;
		
		ArrayList<Point> newPath = new ArrayList<Point>();
		
		while(improve < 3) {
			int bestDistance = calculateDistance(path);
			for(int i = 0; i < pathSize - 1; i++) {
				for(int j = 0; j < pathSize; j++) {
					if(i != j) {
					ArrayList<Point> newRoute = twoOptSwap(path, i, j);
					int newDistance = calculateDistance(newRoute);
					if(newDistance < bestDistance) {
						newPath = newRoute;
						bestDistance = newDistance;
						improve = 0;
					}
					}
				}
			}
			improve++;
		}
		return newPath;
	}
	
	private ArrayList<Point> twoOptSwap(ArrayList<Point> path, int i, int j) {
		ArrayList<Point> newPath = new ArrayList<Point>();
		if(i < j) {
		for(int k = 0; k < i; k++) {
			newPath.add(path.get(k));
		}
		for(int k = j; k == i; k--) {
			newPath.add(path.get(k));
		}
		for(int k = j + 1; k < path.size() - 1; k++) {
			newPath.add(path.get(k));
		}
		} else {
			for(int k = 0; k < j; k++) {
				newPath.add(path.get(k));
			}
			for(int k = i; k == j; k--) {
				newPath.add(path.get(k));
			}
			for(int k = i + 1; k < path.size() - 1; k++) {
				newPath.add(path.get(k));
			}
		}
		return newPath; 
	}
	private int calculateDistance(ArrayList<Point> path) {
		int dist = 0;
		for(int i = 0; i < path.size() - 1; i++) {
			Point point1 = path.get(i);
			Point point2 = path.get(i+1);
			dist += point1.distance(point2);
		}
		return dist;
	}
	}
*/
