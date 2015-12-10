import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class Image {
	public BufferedImage img;
	
	private double[] threshold = { 0.25, 0.26, 0.27, 0.28, 0.29, 0.3, 0.31,
            0.32, 0.33, 0.34, 0.35, 0.36, 0.37, 0.38, 0.39, 0.4, 0.41, 0.42,
            0.43, 0.44, 0.45, 0.46, 0.47, 0.48, 0.49, 0.5, 0.51, 0.52, 0.53,
            0.54, 0.55, 0.56, 0.57, 0.58, 0.59, 0.6, 0.61, 0.62, 0.63, 0.64,
            0.65, 0.66, 0.67, 0.68, 0.69 };

	public Image(String filename) {
		try {
			BufferedImage originalImage = ImageIO.read(new File(filename));
			int newHeight=0, newWidth=0;
			if (originalImage.getHeight()>100){
				newHeight=400;
			}
			if (originalImage.getWidth()>100){
				newWidth=400;
			}
			if (originalImage.getHeight()>100 || originalImage.getWidth()>100){
			
			BufferedImage resized = new BufferedImage(newWidth, newHeight, originalImage.getType());
			Graphics2D g = resized.createGraphics();
			g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
			    RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g.drawImage(originalImage, 0, 0, newWidth, newHeight, 0, 0, originalImage.getWidth(),
			    originalImage.getHeight(), null);
			g.dispose();
			img=resized;
			} else {
				img=originalImage;
			}

		} catch (IOException e) {
			
		}
	}

	/*
	 * Random dithering using threshhold matrix
	 * Basic Idea: https://en.wikipedia.org/wiki/Ordered_dithering
	 */
	public void dither(){
		
		 BufferedImage imRes = new BufferedImage(img.getWidth(),
	                img.getHeight(), BufferedImage.TYPE_INT_RGB);
	        Random rn = new Random();
	        for (int i = 0; i < img.getWidth(); i++) {
	            for (int j = 0; j < img.getHeight(); j++) {

	                int color = img.getRGB(i, j);

	                //Convert to grayscale
	                //http://stackoverflow.com/questions/9131678/convert-a-rgb-image-to-grayscale-image-reducing-the-memory-in-java
	                int red = (color >>> 16) & 0xFF;
	                int green = (color >>> 8) & 0xFF;
	                int blue = (color >>> 0) & 0xFF;
	                
	                //Make dark points darker and light points lighter to simplify
	                if(red < 128) {
	            		red = red / 2;
	            	} else {
	            		red = red + ((255 - red)/2);
	            	}
	                if(green < 128) {
	            		green = green / 2;
	            	} else {
	            		green = green + ((255 - green)/2);
	            	}
	                if(blue < 128) {
	            		blue = blue / 2;
	            	} else {
	            		blue = blue + ((255 - blue)/2);
	            	}

	                //Calculate luminance in range 0.0 to 1.0; using SRGB luminance constants
	                //http://www.johndcook.com/blog/2009/08/24/algorithms-convert-color-grayscale/
	                double lum = (red * 0.21f + green * 0.71f + blue * 0.07f) / 255;
	                
	                //Check against random point in threshold matrix to determine if point is black or white
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
	        

	}

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
		
	}

	public int getHeight() {
		return img.getHeight();
	}

	public int getWidth() {
		return img.getWidth();
	}

	public int getRGB(int j, int i) {
		return img.getRGB(j, i);
	}
	

}
