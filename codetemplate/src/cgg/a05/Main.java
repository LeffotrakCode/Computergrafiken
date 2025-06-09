/**package cgg.a05;

import cgg.*;
import tools.*;

public class Main {
    public static void main(String[] args) {
        int width = 1920;
        int height = 1080;

        Scene scene = new Scene();
        Camera cam = new Camera(Math.PI * 0.5, width, height);

        // Light sources
        scene.addLight(new PointLight(new Vec3(-10, 10, 0), new Color(1, 1, 1)));

        // Spheres with textures
        scene.addSphere(new Sphere(
            new Vec3(0, 0, 4),
            1,
            new TexturedPhongMaterial(
                new ImageTexture("codetemplate/src/textures/cherry.jpg"),
                Color.white,
                100
            )
        ));

        scene.addSphere(new Sphere(
            new Vec3(3, 0, 4),
            1,
            new TexturedPhongMaterial(
                new ImageTexture("codetemplate/src/textures/ice.jpg"),
                Color.white,
                100
            )
        ));

        scene.addSphere(new Sphere(
            new Vec3(-10, 0, 10),
            3,
            new TexturedPhongMaterial(
                new ImageTexture("codetemplate/src/textures/moon.jpg"),
                Color.white,
                10
            )
        ));

        // Sphere for background floor
        scene.addSphere(new Sphere(
            new Vec3(0, -10004, -4),
            10000,
            new TexturedPhongMaterial(
                new ImageTexture("codetemplate/src/textures/grass.jpg"),
                Color.white,
                10
            )
        ));

        // Configure supersampling
        ISampler pixelSampler = (Vec2 p) -> new Raytracer(cam, scene).getColor(p);
        ISampler superSampler = new SuperSampler(pixelSampler, 64, "stratified");

        Image image = new Image(width, height);

        for (int x = 0; x != width; x++) {
            for (int y = 0; y != height; y++) {
                image.setPixel(x, y, superSampler.getColor(new Vec2(x, y)));
            }
        }

        image.writePNG("a05-corrected");
    }
}
*/