
public class Test {

	public static void main(String[] args) {
		Image soupCan = new Image("flowers.jpeg");
		int[][] dither = soupCan.dither();
		TSP solver = new TSP(dither);
		solver.findShortestPath();
	}
	
}
