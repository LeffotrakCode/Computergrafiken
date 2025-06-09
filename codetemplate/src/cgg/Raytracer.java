package cgg;

import tools.*;

public class Raytracer implements ISampler {
    private final Scene scene;
    private final Camera cam;
    private final Image image;

    public Raytracer(Camera cam, Scene scene, Image image) {
        this.scene = scene;
        this.cam = cam;
        this.image = image;
    }

    @Override
    public Color getColor(Vec2 position) {
        Ray ray = cam.generateRay(position);
        Hit hit = scene.intersect(ray);
        if (hit != null) {
            return scene.shade(hit, ray);
        } else {
            return Color.black;
        }
    }
}
