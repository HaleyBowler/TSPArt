
import java.awt.Color;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Stack;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
 
/**
 * This program demonstrates how to draw lines using Graphics2D object.
 * @author www.codejava.net
 *
 */
public class LinesDrawingExample extends JFrame {
 
    public LinesDrawingExample() {
        super("Lines Drawing Demo");
 
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }
 
    void drawLines(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

		Image soupCan = new Image("download.jpeg");
		soupCan.dither();
		Image dithered = new Image("dithered.png");
		int[][] pixel = new int[dithered.getWidth()][dithered.getHeight()];
		for (int i = 0; i < dithered.getWidth(); i++) {
			for (int j = 0; j < dithered.getHeight(); j++) {
				Color color = new Color(dithered.getRGB(i, j));
				pixel[i][j] = (int) (color.getGreen() * .7 + color.getRed()
						* .2 + color.getBlue() * .1);
				
			}
		}
	      
		TSP solver = new TSP(pixel);
		Stack<Point> path = solver.findShortestPath();
		ArrayList<Point> newPath = new ArrayList<Point>();
		while(!path.isEmpty()) {
			newPath.add(path.pop());
		}
		//ArrayList<Point> finalPath = solver.removeIntersections(newPath);
		/*
		for(Point point : newPath) {
			System.out.print(point + " ");
		}
		System.out.println(" ");
		for(Point point : finalPath) {
			System.out.print(point + " ");
		}
		*/
		for(int i = newPath.size() - 1; i > 0; i--) {
			Point point1 = newPath.get(i);
			Point point2 = newPath.get(i-1);
			g2d.draw(new Line2D.Double(point1.getX(), point1.getY(), point2.getX(), point2.getY()));
		}
    }
 
    public void paint(Graphics g) {
        super.paint(g);
        drawLines(g);
    }
 
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LinesDrawingExample().setVisible(true);
            }
        });
    }
    
}

