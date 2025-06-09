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
        Color color = (hit != null) ? scene.shade(hit, ray) : Color.black;

        // x/y-Koordinaten aus Position (Vec2)
        int x = (int) position.x();
        int y = (int) position.y();

        // Bild setzen
        if (x >= 0 && x < image.width() && y >= 0 && y < image.height()) {
            image.setPixel(x, y, color);
        }

        return color;
    }
}
