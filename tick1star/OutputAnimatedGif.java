package uk.ac.cam.dab80.oop.tick1star;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.Graphics;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOInvalidTreeException;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataNode;
import javax.imageio.stream.FileImageOutputStream;

public class OutputAnimatedGif {

	private FileImageOutputStream output;
	private ImageWriter writer;

	public OutputAnimatedGif(String file) throws IOException {
		this.output = new FileImageOutputStream(new File(file)); 
		this.writer = ImageIO.getImageWritersByMIMEType("image/gif").next();
		this.writer.setOutput(output);
		this.writer.prepareWriteSequence(null);
	}

	private BufferedImage makeFrame(boolean[][] world) {
		int height = world.length;
		int width = world[0].length;
	    BufferedImage image = new BufferedImage(width*20, height*20, BufferedImage.TYPE_INT_RGB);
	

		Graphics g = image.getGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, width*20, height*20);
		g.setColor(Color.GRAY);
		for (int y = 0; y <= height; y++){
			g.drawLine(0,y*20,width*20,y*20);
		}
		for (int x = 0; x<=width; x++){
			g.drawLine(x*20,0,x*20,height*20);
		}
		g.setColor(Color.BLACK);
		for (int x = 0; x < width; x++){
			for (int y = 0; y < height;y++){
				if (world[y][x]){
					g.fillRect(x*20+1,y*20+1,19,19);
				}
			}
		}
		g.dispose();
		return image;
		
	}
	
	public void addFrame(boolean[][] world) throws IOException {
		BufferedImage image = makeFrame(world);
		try {
			IIOMetadataNode node = new IIOMetadataNode("javax_imageio_gif_image_1.0");
			IIOMetadataNode extension = new IIOMetadataNode("GraphicControlExtension");
			extension.setAttribute("disposalMethod", "none");
			extension.setAttribute("userInputFlag", "FALSE");
			extension.setAttribute("transparentColorFlag", "FALSE");
			extension.setAttribute("delayTime", "1");
			extension.setAttribute("transparentColorIndex", "255");
			node.appendChild(extension);
			IIOMetadataNode appExtensions = new IIOMetadataNode("ApplicationExtensions");
			IIOMetadataNode appExtension = new IIOMetadataNode("ApplicationExtension");
			appExtension.setAttribute("applicationID", "NETSCAPE");
			appExtension.setAttribute("authenticationCode", "2.0");
			
			// CHANGED TO MAKE LOOP - found from https://memorynotfound.com/generate-gif-image-java-delay-infinite-loop-example/
			
			//appExtension.setUserObject("\u0021\u00ff\u000bNETSCAPE2.0\u0003\u0001\u0000\u0000\u0000".getBytes());
			appExtension.setUserObject("\u0001\u0000\u0000NETSCAPE2.0\u0003\u0001\u0000\u0000\u0000".getBytes());
			
			// END OF CHANGE
			
			appExtensions.appendChild(appExtension);
			node.appendChild(appExtensions);
			
			
			IIOMetadata metadata = writer.getDefaultImageMetadata(new ImageTypeSpecifier(image), null);
			metadata.mergeTree("javax_imageio_gif_image_1.0", node);

			IIOImage t = new IIOImage(image, null, metadata);
			writer.writeToSequence(t, null);
		}
		catch (IIOInvalidTreeException e) {
			throw new IOException(e);
		}
	}

	public void close() throws IOException {
		writer.endWriteSequence();
	}

}