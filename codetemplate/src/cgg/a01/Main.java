/** 
package cgg.a01;

import cgg.Image;
import cgg.ColorDisc;
import cgg.ColoredDiscs;
import cgg.ConstantColor;

import static tools.Functions.*;



import static tools.Color.*;


public class Main {

  public static void main(String[] args) {
    int width = 400;
    int height = 400;

    // This class instance defines the contents of the image.
  //var constant = new ConstantColor(blue);
  
  //var ColorDisc = new ColorDisc(vec2(200, 200), 50, red, green);
  var MultipleDiscs = new ColoredDiscs(width, height, 81,   red, yellow,9);
 
   


    // Creates an image and iterates over all pixel positions inside the image.
    var image = new Image(width, height);
    for (int x = 0; x != width; x++)
      for (int y = 0; y != height; y++)
        image.setPixel(x, y, MultipleDiscs.getColor(vec2(x, y )));

    // Write the image to disk.
    image.writePNG("a01-colordiscs");
  }
}
*/