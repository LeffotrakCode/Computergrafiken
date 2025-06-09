package cgg.a07;

import cgg.*;
import tools.*;

public class Main {
    public static void main(String[] args) {
        int width = 1920;
        int height = 1080;

        // Kamera-Transformation
        Mat44 camTransform = Mat44.translation(0, 0, -10)
                .multiply(Mat44.rotationY(Math.toRadians(0)));

        Camera cam = new Camera(Math.PI * 0.5, width, height, camTransform);

        // Root-Gruppe
        Group root = new Group(Mat44.identity);

        // Objekte (Sphären mit Texturmaterialien)
        root.add(new Sphere(new Vec3(0, 0, 4), 1,
                new TexturedPhongMaterial(new ImageTexture("codetemplate/src/textures/cherry.jpg"), Color.white, 100)));

        root.add(new Sphere(new Vec3(3, 0, 4), 1,
                new TexturedPhongMaterial(new ImageTexture("codetemplate/src/textures/ice.jpg"), Color.white, 100)));

        root.add(new Sphere(new Vec3(-10, 0, 10), 3,
                new TexturedPhongMaterial(new ImageTexture("codetemplate/src/textures/moon.jpg"), Color.white, 10)));

        // Boden
        root.add(new Sphere(new Vec3(0, -10004, -4), 10000,
                new TexturedPhongMaterial(new ImageTexture("codetemplate/src/textures/grass.jpg"), Color.white, 10)));

        // Hintergrund
        root.add(new Background());

        // Szene
        Scene scene = new Scene(root);
        scene.addLight(new PointLight(new Vec3(-10, 10, 0), new Color(1, 1, 1)));

        // Bild
        Image image = new Image(width, height);

        // Sampler
        ISampler pixelSampler = (Vec2 p) -> {
            Ray ray = cam.generateRay(p);
            Hit hit = scene.intersect(ray);
            if (hit == null) {
                return new Color(0, 0, 0);
            }
            return scene.shade(hit, ray);
        };

        ISampler superSampler = new SuperSampler(pixelSampler, 64, "stratified");

        // Render-Schleife
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Color c = superSampler.getColor(new Vec2(x, y));
                image.setPixel(x, y, c);
            }
        }

        image.writePNG("output-transforms");
    }
}
