import java.awt.Point;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Stack;

public class TSP {
	public ArrayList<Point> blackPoints;
	public TSP(int[][] image) {
		blackPoints = new ArrayList<Point>();
		for(int i = 0; i < image.length; i++) {
			for(int j = 0; j < image[i].length; j++) {
				if (image[i][j] == 0) {
					blackPoints.add(new Point(i, j));
				}
			}
		}
		//System.out.println(blackPoints.size());
	}
	public Stack<Point> findShortestPath() {
		Stack<Point> path = new Stack<Point>();

		Point currPoint = blackPoints.get(0);
		
		while(blackPoints.size() > 0) {
			blackPoints.remove(currPoint);
			int shortestDist = 0;
			for(int i = 0; i < blackPoints.size(); i++) {
				if(currPoint.distance(blackPoints.get(i)) < currPoint.distance(blackPoints.get(shortestDist))) {
					shortestDist = i;
				}
			}
			currPoint = blackPoints.get(shortestDist);
			path.push(blackPoints.get(shortestDist));
			blackPoints.remove(shortestDist);
			
			
		}

		return path;
	}
	public ArrayList<Point> switchDirection(int start, int end, 
			ArrayList<Point> path) {
		ArrayList<Point> tempList = new ArrayList<Point>();
		for (int x = start + 1; x < end; x++) {
			tempList.add(0, path.get(x));
		}
		for (int x = start + 1; x < end; x++) {
			path.set(x, tempList.get(0));
			tempList.remove(0);
		}
		return path;
	}

	
}
