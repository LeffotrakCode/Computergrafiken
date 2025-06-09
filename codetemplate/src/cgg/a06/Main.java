package cgg.a06;

import cgg.*;
import tools.*;

public class Main {
    public static void main(String[] args) {
        int width = 1920;
        int height = 1080;

        // Camera transform:
         // Camera transform:
        Mat44 camTransform = Mat44.translation(5, 0, -5)
                             .multiply(Mat44.rotationY(Math.toRadians(20))); // Rotate 20 degrees

        Camera cam = new Camera(Math.PI * 0.5, width, height, camTransform);

        // Scene root with identity transform
        Group root = new Group(Mat44.identity);

        // Sphere 1 (cherry)
        Sphere sphere1 = new Sphere(new Vec3(0, 0, 4), 1,
            new TexturedPhongMaterial(new ImageTexture("codetemplate/src/textures/cherry.jpg"), Color.white, 100));
        Group g1 = new Group(Mat44.identity);
        g1.add(sphere1);

        // Sphere 2 (ice)
        Sphere sphere2 = new Sphere(new Vec3(3, 0, 4), 1,
            new TexturedPhongMaterial(new ImageTexture("codetemplate/src/textures/ice.jpg"), Color.white, 100));
        Group g2 = new Group(Mat44.identity);
        g2.add(sphere2);

        // Sphere 3 (moon)
        Sphere sphere3 = new Sphere(new Vec3(-10, 0, 10), 3,
            new TexturedPhongMaterial(new ImageTexture("codetemplate/src/textures/moon.jpg"), Color.white, 10));
        Group g3 = new Group(Mat44.identity);
        g3.add(sphere3);

        // large sphere (floor)
        Sphere floor = new Sphere(new Vec3(0, -10004, -4), 10000,
            new TexturedPhongMaterial(new ImageTexture("codetemplate/src/textures/grass.jpg"), Color.white, 10));
        Group gFloor = new Group(Mat44.identity);
        gFloor.add(floor);

        // Add all groups to the root
        root.add(g1);
        root.add(g2);
        root.add(g3);
        root.add(gFloor);

        // Create the scene
        Scene scene = new Scene(root);

        // Add light source
        scene.addLight(new PointLight(new Vec3(-10, 10, 0), new Color(1, 1, 1)));
        
        Image image = new Image(width, height);
        // Sampler
        ISampler pixelSampler = (Vec2 p) -> new Raytracer(cam, scene, image).getColor(p);
        ISampler superSampler = new SuperSampler(pixelSampler, 64, "stratified");

       

      for (int x = 0; x < width; x++) {
    for (int y = 0; y < height; y++) {
        superSampler.getColor(new Vec2(x, y));
        }
    }


        image.writePNG("test-transforms");
    }
}
