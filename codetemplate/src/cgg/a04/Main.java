package cgg.a04;

import cgg.*;
import tools.*;
import textures.*;

public class Main {
    public static void main(String[] args) {
        int width = 1920;
        int height = 1080;

        Scene scene = new Scene();
        Camera cam = new Camera(Math.PI * 0.5, width, height);

        // Lichtquellen
        scene.addLight(new DirectionalLight(new Vec3(1, -1, -1), new Color(1, 1, 1)));

        // Texturierte Kugeln
        scene.addSphere(new Sphere(
            new Vec3(0, 0, -3),
            1,
            new TexturedPhongMaterial(
                new ImageTexture("textures/earth.png"),
                Color.white,
                100
            )
        ));



        // Hintergrundebene
        scene.addSphere(new Sphere(
            new Vec3(0, -10004, -4),
            10000,
            new PhongMaterial(new Color(0.5, 0.5, 0.5), Color.white, 100)
        ));

        Raytracer raytracer = new Raytracer(cam, scene);
        Image image = new Image(width, height);

        
        for (int x = 0; x != width; x++)
        for (int y = 0; y != height; y++)
         image.setPixel(x, y, raytracer.getColor(new Vec2(x, y)));

        // Write the image to disk.
        image.writePNG("a04-phong");
    }
}
