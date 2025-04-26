
package cgg;
import tools.*;

public class Image implements tools.IImage {
    private double[] data;
    private int width; 
    private int height;
    public Image(int width, int height) {
        // Provides storage for the image data. For each pixel in the image
        // three double values are needed to store the pixel components.
        this.width = width; 
        this.height = height;
        int arraySize = (width * height)*3;
        this.data = new double[arraySize]; 
        
    }

    public void setPixel(int x, int y, Color color) {
        // Stores the RGB color components for one particular pixel addressed
        // by it's coordinates in the image.
        
        int index = (y*width()+x)*3;
        
        double red = color.r();
        double green = color.g();
        double blue = color.b();

        data[index] = red;
        data[index + 1] = green;
        data [index + 2]= blue;

        height();
        width();
    }

    public Color getPixel(int x, int y) {
        // Retrieves the RGB color components for one particular pixel addressed
        // by it's coordinates in the image.
        int index = (y*width()+x)*3;
        double red = data[index];
        double green = data[index + 1];
        double blue = data[index + 2 ];

        return new Color(red,green,blue);
        
    }

    public void writePNG(String name) {
        // This call also needs to be adjusted once Image() and setPixel()
        // are implemented. Use
        // ImageWriter.writePNG(String basename, double[] data, int width, int height) to
        // write the image data to disk in PNG format.
        ImageWriter.writePNG(name,data, width(),height());
    }

    public void writeHDR(String name) {
        // This call also needs to be adjusted once Image() and setPixel()
        // are implemented. Use
        // ImageWriter.writeHDR(String basename, double[] data, int width, int height) to
        // write the image data to disk in OpenEXR format.
        ImageWriter.writeHDR(name,data,width(),height());
    }

    public int width() {
        return width;
    }

    public int height() {
        return height;
    }
}
