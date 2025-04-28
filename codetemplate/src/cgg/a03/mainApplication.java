package cgg.a03;
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
        scene.addSphere(new Sphere(new Vec3(0, 0, -4), 1));
        
        scene.addLight(new DirectionalLight(new Vec3(5, 5, 0), Color.white));
        

        Raytracer raytracer = new Raytracer(cam,scene);

        var image = new Image(width, height);
        for (int x = 0; x != width; x++)
        for (int y = 0; y != height; y++)
         image.setPixel(x, y, raytracer.getColor(new Vec2(x, y)));

        // Write the image to disk.
        image.writePNG("a03-Test");
    }
    
}
