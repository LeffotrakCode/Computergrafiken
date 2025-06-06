
package tools;

import static tools.Functions.*;

import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageTexture implements ISampler {
  private BufferedImage image;
  public final int width;
  public final int height;
  private final double componentScale;
  private final int components;

  public ImageTexture(String filename) {
    try {
      image = ImageIO.read(new File(filename));
    } catch (IOException e) {
      System.err.println("Cannot read image from: " + filename);
      System.exit(1);
    }

    if (image == null) {
      System.err.println("Error reading image from: " + filename);
      System.exit(1);
    }

    width = image.getWidth();
    height = image.getHeight();
    components = image.getRaster().getNumBands();

    System.out.format("texture: %s: %dx%d, components: %d\n", filename, width, height, components);

    switch (image.getSampleModel().getDataType()) {
      case DataBuffer.TYPE_BYTE:
        componentScale = 255;
        break;
      case DataBuffer.TYPE_USHORT:
        componentScale = 65535;
        break;
      default:
        componentScale = 1;
        break;
    }
  }

  public Color getColor(Vec2 at) {
  
    // Wrap UV coordinates to stay within texture bounds [0,1]
    int x = (int) ((at.u() - Math.floor(at.u())) * width);
    int y = (int) ((at.v() - Math.floor(at.v())) * height);

    // Apply gamma correction to normalized color
    double[] pixelBuffer = new double[4];
    image.getRaster().getPixel(x, y, pixelBuffer);
    Color color = color(pixelBuffer[0], pixelBuffer[1], pixelBuffer[2]);
    Color linearColor = divide(color, componentScale);

    return new Color(
    Math.pow(linearColor.r(), 2.2),
    Math.pow(linearColor.g(), 2.2),
    Math.pow(linearColor.b(), 2.2)
    );
  }
}
