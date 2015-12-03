
public class Test {

	public static void main(String[] args) {
		Image soupCan = new Image("image.png");
		int[][] dither = soupCan.dither();
		TSP solver = new TSP(dither);
		solver.findShortestPath();
	}
	
}
