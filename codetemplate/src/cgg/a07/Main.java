package cgg.a07;

import cgg.*;
import tools.*;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        int width = 1920;
        int height = 1080;

        Mat44 camTransform = Mat44.translation(0, 0, -25);
        Camera cam = new Camera(Math.PI / 3, width, height, camTransform);

        Group root = new Group(Mat44.identity);

        List<ISampler> textures = List.of(
            new ImageTexture("codetemplate/src/textures/cherry.jpg"),
            new ImageTexture("codetemplate/src/textures/grass.jpg"),
            new ImageTexture("codetemplate/src/textures/ice.jpg"),
            new ImageTexture("codetemplate/src/textures/moon.jpg")
        );
        int cols = 6;
        int rows = 6;
        double spacing = 4.0;
        double startX = -((cols - 1) * spacing) / 2.0;
        double startY = -((rows - 1) * spacing) / 2.0;

        int sphereCount = 0;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                double x = startX + col * spacing;
                double y = startY + row * spacing;
                Vec3 pos = new Vec3(x, y, 0);

                IMaterial mat;
                int choice = sphereCount % 6;

                switch (choice) {
                    case 0 -> mat = new PhongMaterial(
                        new Color(0.2, 0.7, 0.3),
                        new Color(0.3, 0.3, 0.3), 100
                    );
                    case 1 -> mat = new MirrorMaterial(
                        Color.black, Color.black, 0, Color.black
                    );
                    default -> {
                        int texIndex = (sphereCount - 2) % textures.size();
                        mat = new TexturedPhongMaterial(
                            textures.get(texIndex),
                            new Color(0.4, 0.4, 0.4), 200
                        );
                    }
                }

                root.add(new Sphere(pos, 1.5, mat));
                sphereCount++;
            }
        }

        // Optional: Add subtle ambient background
        root.add(new Background());

        Scene scene = new Scene(root);
        // Stronger light for contrast
        scene.addLight(new PointLight(new Vec3(-50, 50, -50), new Color(4, 4, 4)));
        scene.addLight(new PointLight(new Vec3(50, 50, -30), new Color(4, 4, 4)));

         Image image = new Image(width, height);

        ISampler pixelSampler = (Vec2 p) -> {
            Ray ray = cam.generateRay(p);
            Hit hit = scene.intersect(ray);
            return hit == null ? Color.black : scene.shade(hit, ray);
        };

        ISampler superSampler = new SuperSampler(pixelSampler, 64, "stratified");

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Color c = superSampler.getColor(new Vec2(x, y));

                // Clamp to avoid overflow before gamma
                c = new Color(
                    Math.min(c.r(), 1),
                    Math.min(c.g(), 1),
                    Math.min(c.b(), 1)
                );

                // Gamma correction
                Color gammaCorrected = new Color(
                    Math.pow(c.r(), 1.0 / 2.2),
                    Math.pow(c.g(), 1.0 / 2.2),
                    Math.pow(c.b(), 1.0 / 2.2)
                );

                image.setPixel(x, y, gammaCorrected);
            }
        }

        image.writePNG("a07-pathtracing");
    }
}
