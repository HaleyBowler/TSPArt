
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Line2D;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
 
/**
 * This program demonstrates how to draw lines using Graphics2D object.
 * @author www.codejava.net
 *
 */
public class LinesDrawingExample extends JFrame {
	
	static String result;
	static int numPoints;
 
    public LinesDrawingExample() {
        super("Lines Drawing Demo");
        setSize(500, 500);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }
 
    void drawLines(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        Image undithered = new Image(result);
		undithered.dither();
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
		newPath.add(newPath.get(0));
		ArrayList<Point> finalPath = solver.removeIntersections(newPath);

		for(int i = finalPath.size() - 1; i > 0; i--) {
			Point point1 = finalPath.get(i);
			Point point2 = finalPath.get(i-1);
			g2d.draw(new Line2D.Double(point1.getX()+50, point1.getY()+50, point2.getX()+50, point2.getY()+50));
		}
    }
 
    public void paint(Graphics g) {
        super.paint(g);
        drawLines(g); 
      
    }
    
 
    public static void main(String[] args) {
    	JFrame frame = new JFrame();
        result = JOptionPane.showInputDialog(frame, "Enter the name of the file:");
        String points="";
        points = JOptionPane.showInputDialog(frame, "Enter the number of points: ");
        numPoints=Integer.parseInt(points);
        
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LinesDrawingExample().setVisible(true);
                
            }
        });
       
    }
}

