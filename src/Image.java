import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Image {
	public BufferedImage img;

	public Image(String filename) {
		try {
			BufferedImage orginalImage = ImageIO.read(new File(filename));
			img = (BufferedImage) orginalImage.getScaledInstance(500, 500, 0);
			/*
			BufferedImage blackAndWhiteImg = new BufferedImage(
			        orginalImage.getWidth(), orginalImage.getHeight(),
			        BufferedImage.TYPE_BYTE_GRAY);
			    
			    Graphics2D graphics = blackAndWhiteImg.createGraphics();
			    graphics.drawImage(orginalImage, 0, 0, null);

			    ImageIO.write(blackAndWhiteImg, "png", new File("blackandwhite.png")); 
			    img = ImageIO.read(new File("blackandwhite.png"));
			    */

		} catch (IOException e) {
		}
	}
	
	
	public int[][] dither() {
		int[][] pixel = new int[img.getHeight()][img.getWidth()];
		for (int i = 0; i < pixel.length; i++) {
			for (int j = 0; j < pixel[i].length; j++) {
				Color color = new Color(img.getRGB(j, i));
				//pixel[i][j] = img.getRGB(j, i);
				/*pixel[i][j] = (thecolor.getRed() + thecolor.getBlue() + thecolor
						.getGreen()) / 3;*/
				pixel[i][j] = (int)(color.getGreen()*.7+color.getRed()*.2+color.getBlue()*.1);
			
			}
		}
		for (int i = 0; i < pixel.length; i++) {
			for (int j = 0; j < pixel[i].length; j++) {
				int oldPixel = pixel[i][j];
				int newPixel = 255;
				if (oldPixel < 128){
					newPixel=0;
				}
				//int newPixel = oldPixel/256;
				pixel[i][j] = newPixel;
				int error = oldPixel - newPixel;
				if(i < pixel.length - 1)
					pixel[i+1][j] = pixel[i+1][j] + (int)(error * (7.0/16));
				if(j < pixel[i].length - 1 && i > 1)
					pixel[i-1][j+1] = pixel[i-1][j+1] + (int)(error * (3.0/16));
				if(j < pixel[i].length - 1)
					pixel[i][j+1] = pixel[i][j+1] + (int)(error * (5.0/16));
				if(j < pixel[i].length - 1 && i < pixel.length - 1)
				pixel[i+1][j+1] = pixel[i+1][j+1] + (int)(error * (1.0/16));
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

}
