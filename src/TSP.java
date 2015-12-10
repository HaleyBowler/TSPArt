import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Stack;

public class TSP {
	// Make an ArrayList of black points
	public ArrayList<Point> blackPoints;
	// To access user input
	public LinesDrawingExample temp = new LinesDrawingExample();

	/*
	 * Constructor for TSP Creates list of black points from dithered image
	 */
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
		// Pick random points, number specified by user input
		Collections.shuffle(blackPoints, new Random(seed));
		blackPoints.subList(temp.numPoints - 1, blackPoints.size() - 1).clear();
	}

	/*
	 * Finds approximate shortest path using nearest neighbor heuristic O(n^2)
	 */
	public Stack<Point> findShortestPath() {
		// Stack to store path
		Stack<Point> path = new Stack<Point>();
		// Starting point
		Point currPoint = blackPoints.get(0);
		// while there are points not added to path
		while (blackPoints.size() > 0) {
			blackPoints.remove(currPoint);
			int shortestDist = 0;
			// find next point not yet in path with shortest distance
			for (int i = 0; i < blackPoints.size(); i++) {
				if (currPoint.distance(blackPoints.get(i)) < currPoint.distance(blackPoints.get(shortestDist))) {
					shortestDist = i;
				}
			}
			// Add point to path and remove from ArrayList
			currPoint = blackPoints.get(shortestDist);
			path.push(blackPoints.get(shortestDist));
			blackPoints.remove(shortestDist);
		}
		return path;
	}

	/*
	 * Remove intersections using 2-Opt algorithm O(n^2)
	 */
	public ArrayList<Point> removeIntersections(ArrayList<Point> path) {
		// Create ArrayList for modified path
		ArrayList<Point> newPath = new ArrayList<Point>();
		// Add original points to newPath
		for (Point point : path) {
			newPath.add(point);
		}
		int pathSize = path.size();
		// Check if we need to keep looking for intersections
		boolean swapped = false;
		// While there still might be intersections
		while (!swapped) {
			boolean swapped2 = false;
			for (int i = 0; i < pathSize; i++) {
				for (int j = i + 1; j < pathSize; j++) {
					if (i != j) {
						//Check if it is better to swap end points (intersection ecists)
						if (swapBetter(i, j, newPath)) {
							int cnt = 0;
							//Swap points and set newPath to modified path
							for (Point point : swap(i, j, newPath)) {
								if (cnt < temp.numPoints)
									newPath.set(cnt, point);
								cnt++;
							}
							//Keep checking if intersection was found
							swapped2 = true;
						}
					}
				}
			}
			swapped = !swapped2;
		}
		return newPath;
	}

	/*
	 * Use triangle inequality to determine if there is an intersection 
	 */
	private boolean swapBetter(int i, int j, ArrayList<Point> path) {
		//Return if we are looking at end points
		if (i == 0 || j == path.size() - 1 || i == path.size() - 1 || j == 0) {
			return false;
		}
		
		//get Vi-1, Vi, Vj, Vj+1
		Point a = path.get(i);
		//Point b = (i != 0) ? path.get(i - 1) : path.get(path.size() - 1);
		Point b = path.get(i - 1);
		Point c = path.get(j);
		//Point d = (j != path.size() - 1) ? path.get(j + 1) : path.get(0);
		Point d = path.get(j + 1);
		
		//Return if we are looking at adjacent lines
		if (a.equals(b) || a.equals(d) || b.equals(c) || b.equals(d)) {
			return false;
		}

		//Find current distance
		double currDist1 = a.distance(b);
		double currDist2 = c.distance(d);

		//Find new distance
		double newDist1 = b.distance(c);
		double newDist2 = a.distance(d);

		//If new distance is less than current distance, return true
		if (currDist1 + currDist2 > newDist1 + newDist2) {
			return true;
		}
		return false;
	}

	/*
	 * Make swap according to 2-Opt algorithm
	 */
	private ArrayList<Point> swap(int i, int j, ArrayList<Point> path) {
		ArrayList<Point> newPath = new ArrayList<Point>();
		//Keep V0 to Vi-1 the same
		for (int k = 0; k < i; k++) {
			newPath.add(path.get(k));
		}
		//Reverse Vi to Vj
		for (int k = j; k >= i; k--) {
			newPath.add(path.get(k));
		}
		//Keep Vj+1 to Vn the same
		for (int k = j + 1; k < path.size() - 1; k++) {
			newPath.add(path.get(k));
		}

		return newPath;
	}
}
