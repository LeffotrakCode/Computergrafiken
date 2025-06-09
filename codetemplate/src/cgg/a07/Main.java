package cgg.a07;

import cgg.*;
import tools.*;

public class Main {
    public static void main(String[] args) {
        int width = 1920;
        int height = 1080;

        // Kamera-Transformation: Kamera auf Z = -10, schaut auf Ursprung
        Mat44 camTransform = Mat44.translation(0, 0, -10)
                .multiply(Mat44.rotationY(Math.toRadians(0)));

        Camera cam = new Camera(Math.PI / 3, width, height, camTransform);

        Group root = new Group(Mat44.identity);

        // Gitter aus 3x3 Kugeln
        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                Vec3 pos = new Vec3(x * 4, -2, z * 4);
                IMaterial mat;

                int index = (x + 1) * 3 + (z + 1); // Werte von 0 bis 8

                if (index % 3 == 0) {
                    // PhongMaterial, feste rote Farbe
                    mat = new PhongMaterial(
                            new Color(1.0, 0.2, 0.2), // Diffuse rot
                            Color.white,               // Specular weiß
                            20                        // Shininess
                    );
                } else if (index % 3 == 1) {
                    // Spiegelmaterial
                    mat = new MirrorMaterial(Color.black, Color.white, 0, new Color(0, 0, 0));
                } else {
                    // Texturiertes Material mit fixer Textur
                    mat = new TexturedPhongMaterial(
                            new ImageTexture("codetemplate/src/textures/cherry.jpg"),
                            Color.white,
                            10
                    );
                }

                root.add(new Sphere(pos, 1.5, mat));
            }
        }

        // Leuchtende Kugel als Lichtquelle
        root.add(new Sphere(new Vec3(0, 8, 0), 1.0,
                new MirrorMaterial(Color.black, Color.black, 0, new Color(10, 10, 10))));

        // Hintergrund
        root.add(new Background());

        // Szene und Licht hinzufügen
        Scene scene = new Scene(root);
        scene.addLight(new PointLight(new Vec3(-10, 10, 0), new Color(1, 1, 1)));

        // Bild anlegen
        Image image = new Image(width, height);

        // Sampler: Strahlt Ray aus Kamera in Szene
        ISampler pixelSampler = (Vec2 p) -> {
            Ray ray = cam.generateRay(p);
            Hit hit = scene.intersect(ray);
            if (hit == null) {
                return new Color(0, 0, 0);
            }
            return scene.shade(hit, ray);
        };

        // Supersampling mit 64 Samples (stratified)
        ISampler superSampler = new SuperSampler(pixelSampler, 64, "stratified");

        // Render-Schleife
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Color c = superSampler.getColor(new Vec2(x, y));
                image.setPixel(x, y, c);
            }
        }

        // Bild speichern
        image.writePNG("a07-pathtracing");
    }
}
