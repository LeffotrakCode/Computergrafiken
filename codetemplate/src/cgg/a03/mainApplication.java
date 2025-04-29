package cgg.a03;
import static tools.Color.blue;
import static tools.Color.red;

import cgg.*;
import tools.Color;
import tools.Vec2;
import tools.Vec3;

public class mainApplication {
    public static void main(String[] args) {
        int width = 500;
        int height = 500;
        Scene scene= new Scene();
        Camera cam = new Camera(Math.PI * 0.5, 500, 500);
        scene.addSphere(new Sphere(new Vec3(0, 0, -4), 1,blue));
        scene.addSphere(new Sphere(new Vec3(3, 3, -4), 1,red));
        
        scene.addLight(new PointLight(new Vec3(3, 3, 0), new Color(1, 1, 1)));

        

        Raytracer raytracer = new Raytracer(cam,scene);

        var image = new Image(width, height);
        for (int x = 0; x != width; x++)
        for (int y = 0; y != height; y++)
         image.setPixel(x, y, raytracer.getColor(new Vec2(x, y)));

        // Write the image to disk.
        image.writePNG("a03-Test");
    }
    
}
