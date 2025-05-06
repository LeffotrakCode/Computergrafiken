package cgg.a04;

import cgg.*;
import tools.*;

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
                new ImageTexture("textures/earth.jpg"),
                Color.white,
                100
            )
        ));

        scene.addSphere(new Sphere(
            new Vec3(2, 0, -4),
            1,
            new TexturedPhongMaterial(
                new ImageTexture("textures/marble.jpg"),
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

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Vec2 pixel = new Vec2(x, y);
                image.setPixel(x, y, raytracer.getColor(pixel));
            }
        }

        image.writePNG("a04-textured-phong");
    }
}
