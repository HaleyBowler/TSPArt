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
		Image soupCan = new Image("can.jpeg");
		int[][] dither = soupCan.dither();
		TSP solver = new TSP(dither);
		Stack<Point> path = solver.findShortestPath();
		//ArrayList<Point> newPath = solver.removeIntersects(path);
		for(int i = path.size() - 1; i > 0; i--) {
			Point point1 = path.get(i);
			Point point2 = path.get(i-1);
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
