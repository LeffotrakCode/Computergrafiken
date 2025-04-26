/**
package cgg.a02;


import tools.Vec2;
import tools.*;

import static tools.Functions.vec3;

import cgg.Camera;
import cgg.Image;
import cgg.Ray;
import cgg.Raytracer;
import cgg.Sphere;


public class Main {
  public static void main(String[] args) {
    int width = 500;
    int height = 500;
    //Aufgabe 2.2
   // var cam = new Camera(Math.PI * 0.5, 10, 10);
   // System.out.println(cam.generateRay(new Vec2(0, 0)));
   // System.out.println(cam.generateRay(new Vec2(5, 5)));
   // System.out.println(cam.generateRay(new Vec2(10, 10)));

   //Aufgabe 2.3
   //var s1 = new Sphere(vec3(0, 0, -2), 1);
   //var s2 = new Sphere(vec3(0, -1, -2), 1);
   //var s3 = new Sphere(vec3(0, 0, 0), 1);
   //var r1 = new Ray(vec3(0, 0, 0), vec3(0, 0, -1));
   //var r2 = new Ray(vec3(0, 0, 0), vec3(0, 1, -1));
   //System.out.println(s1.intersect(r1));
   //System.out.println(s1.intersect(r2));
   //System.out.println(s2.intersect(r1));
   //System.out.println(s3.intersect(r1));

   //Aufgabe 2.4

   var cam = new Camera(Math.PI * 0.5, 500, 500);
   var MultipleSpheres = new Raytracer(cam);
   // Creates an image and iterates over all pixel positions inside the image.
    var image = new Image(width, height);
    for (int x = 0; x != width; x++)
      for (int y = 0; y != height; y++)
      image.setPixel(x, y, MultipleSpheres.getColor(new Vec2(x, y)));

    // Write the image to disk.
    image.writePNG("a02-spheres");
  }
  
    


}
*/