import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Image {
	public BufferedImage img;

	public Image(String filename) {
		try {
			img = ImageIO.read(new File(filename));

		} catch (IOException e) {
		}
	}
	
	public int[][] dither() {
		int[][] pixel = new int[img.getHeight()][img.getWidth()];
		for (int i = 0; i < pixel.length; i++) {
			for (int j = 0; j < pixel[i].length; j++) {
				pixel[i][j] = img.getRGB(j, i);
			}
		}
		for (int i = 0; i < pixel.length; i++) {
			for (int j = 0; j < pixel[i].length; j++) {
				int oldPixel = pixel[i][j];
				int newPixel = oldPixel/256;
				pixel[i][j] = newPixel;
				int error = oldPixel - newPixel;
				if(i < pixel.length - 1)
					pixel[i+1][j] = pixel[i+1][j] + (error * (7/16));
				if(j < pixel[i].length - 1 && i > 1)
					pixel[i-1][j+1] = pixel[i-1][j+1] + (error * (3/16));
				if(j < pixel[i].length - 1)
					pixel[i][j+1] = pixel[i][j+1] + (error * (5/16));
				if(j < pixel[i].length - 1 && i < pixel.length - 1)
				pixel[i+1][j+1] = pixel[i+1][j+1] + (error * (1/16));
			}
		}
		return pixel;
	}
/*	
	public static BufferedImage getImageFromArray(int[] pixels, int width, int height){
		BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
		WritableRaster raster = (WritableRaster) image.getData();
		raster.setPixels(0, 0, width, height, pixels);
		return image;
	}
*/
	
	public static void writeImage(String Name, int[][] color) {
	    String path = Name + ".png";
	    BufferedImage image = new BufferedImage(color.length, color[0].length, BufferedImage.TYPE_INT_RGB);
	    for (int x = 0; x < color.length; x++) {
	        for (int y = 0; y < color[0].length; y++) {
	            image.setRGB(x, y, color[x][y]);
	        }
	    }

	    File ImageFile = new File(path);
	    try {
	        ImageIO.write(image, "png", ImageFile);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	public static void main(String[] args) {
		Image soupCan = new Image("flowers.jpeg");
		int[][] dither = soupCan.dither();
		writeImage("pretty", dither);
		
	}
}
