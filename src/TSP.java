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
	
	public ArrayList<Point> removeIntersects(Stack<Point> path) {
		ArrayList<Point> originalPath = new ArrayList<Point>();
		ArrayList<Point> newPath = new ArrayList<Point>();
		while(!path.isEmpty()) {
			originalPath.add(path.pop());
		}
		for (int i = 0; i < originalPath.size() - 1; i++) {
			Point[] edge1 = {originalPath.get(i), originalPath.get(i+1)};
			for(int j = 0; j < originalPath.size() - 1; j++) {
				Point[] edge2 = {originalPath.get(j), originalPath.get(j+1)};
				boolean intersect = Line2D.linesIntersect(edge1[0].getX(),
						edge1[0].getY(), edge1[1].getX(), edge1[1].getY(),
						edge2[0].getX(), edge2[0].getY(), edge2[1].getX(),
						edge2[1].getY());
				if(intersect) {
					Point tmp =  originalPath.get(i + 1);
					originalPath.set(i + 1, originalPath.get(j));
					originalPath.set(j, tmp);
					
					newPath = switchDirection(i+1, j, originalPath);
				}
				return newPath;
			}
		}
		return newPath;

	}
	
}
