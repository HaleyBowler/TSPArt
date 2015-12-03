import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

public class Image {
	public BufferedImage img;
	
	private double[] threshold = { 0.25, 0.26, 0.27, 0.28, 0.29, 0.3, 0.31,
            0.32, 0.33, 0.34, 0.35, 0.36, 0.37, 0.38, 0.39, 0.4, 0.41, 0.42,
            0.43, 0.44, 0.45, 0.46, 0.47, 0.48, 0.49, 0.5, 0.51, 0.52, 0.53,
            0.54, 0.55, 0.56, 0.57, 0.58, 0.59, 0.6, 0.61, 0.62, 0.63, 0.64,
            0.65, 0.66, 0.67, 0.68, 0.69 };

	public Image(String filename) {
		try {
			BufferedImage orginalImage = ImageIO.read(new File(filename));
			/*
			 * BufferedImage blackAndWhiteImg = new BufferedImage(
			 * orginalImage.getWidth(), orginalImage.getHeight(),
			 * BufferedImage.TYPE_BYTE_GRAY);
			 * 
			 * Graphics2D graphics = blackAndWhiteImg.createGraphics();
			 * graphics.drawImage(orginalImage, 0, 0, null);
			 * 
			 * ImageIO.write(blackAndWhiteImg, "png", new
			 * File("blackandwhite.png")); img = ImageIO.read(new
			 * File("blackandwhite.png"));
			 */
			img = orginalImage;

		} catch (IOException e) {
		}
	}
/*
	static int scaleRange(int number, int oldMin, int oldMax, int newMin,
			int newMax) {
		return (number / ((oldMax - oldMin) / (newMax - newMin))) + newMin;
	}*/

	//public int[][] dither() {
	public void dither(){
		
		 BufferedImage imRes = new BufferedImage(img.getWidth(),
	                img.getHeight(), BufferedImage.TYPE_INT_RGB);
	        Random rn = new Random();
	        for (int i = 0; i < img.getWidth(); i++) {
	            for (int j = 0; j < img.getHeight(); j++) {

	                int color = img.getRGB(i, j);

	                int red = (color >>> 16) & 0xFF;
	                int green = (color >>> 8) & 0xFF;
	                int blue = (color >>> 0) & 0xFF;

	                double lum = (red * 0.21f + green * 0.71f + blue * 0.07f) / 255;

	                if (lum <= threshold[rn.nextInt(threshold.length)]) {
	                    imRes.setRGB(i, j, 0x000000);
	                } else {
	                    imRes.setRGB(i, j, 0xFFFFFF);
	                }

	            }
	        }
	        try {
	            ImageIO.write(imRes, "jpg", new File("dithered.png"));
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        
		/*
		int[][] pixel = new int[img.getHeight()][img.getWidth()];
		for (int i = 0; i < pixel.length; i++) {
			for (int j = 0; j < pixel[i].length; j++) {
				Color color = new Color(img.getRGB(j, i));
				// pixel[i][j] = img.getRGB(j, i);
				
				 // pixel[i][j] = (thecolor.getRed() + thecolor.getBlue() +
				 // thecolor .getGreen()) / 3;
				 
				pixel[i][j] = (int) (color.getGreen() * .7 + color.getRed()
						* .2 + color.getBlue() * .1);

			}
		}
		for (int i = 0; i < pixel.length; i++) {
			for (int j = 0; j < pixel[i].length; j++) {
				int oldPixel = pixel[i][j];
				int newPixel = 255;
				if (oldPixel < 128) {
					newPixel = 0;
				}
				// int newPixel = oldPixel/256;
				pixel[i][j] = newPixel;
				int error = oldPixel - newPixel;
				if (i < pixel.length - 1)
					pixel[i + 1][j] = pixel[i + 1][j]
							+ (int) (error * (7.0 / 16));
				if (j < pixel[i].length - 1 && i > 1)
					pixel[i - 1][j + 1] = pixel[i - 1][j + 1]
							+ (int) (error * (3.0 / 16));
				if (j < pixel[i].length - 1)
					pixel[i][j + 1] = pixel[i][j + 1]
							+ (int) (error * (5.0 / 16));
				if (j < pixel[i].length - 1 && i < pixel.length - 1)
					pixel[i + 1][j + 1] = pixel[i + 1][j + 1]
							+ (int) (error * (1.0 / 16));
			}
		}
		return pixel; */
	}

	/*
	 * public static BufferedImage getImageFromArray(int[] pixels, int width,
	 * int height){ BufferedImage image = new
	 * BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB); WritableRaster
	 * raster = (WritableRaster) image.getData(); raster.setPixels(0, 0, width,
	 * height, pixels); return image; }
	 */

	public static void writeImage(String Name, int[][] color) {
		String path = Name + ".png";
		BufferedImage image = new BufferedImage(color.length, color[0].length,
				BufferedImage.TYPE_INT_RGB);
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
	/*	int number = 240;
		int retVal = scaleRange(number, 0, 255, 0, 9);
		System.out.println(retVal);*/
		
		//Image d = new Image("flowers.jpeg");
	        //d.readOriginal();
	      //  d.dither();
	}

	public int getHeight() {
		// TODO Auto-generated method stub
		return img.getHeight();
	}

	public int getWidth() {
		// TODO Auto-generated method stub
		return img.getWidth();
	}

	public int getRGB(int j, int i) {
		// TODO Auto-generated method stub
		return img.getRGB(j, i);
	}
	

}
