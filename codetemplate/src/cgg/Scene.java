package cgg;

import java.util.ArrayList;
import tools.*;

public class Scene {
    private final Group world;
    private final ArrayList<ILight> lights = new ArrayList<>();

    public Scene(Group world) {
        this.world = world;
    }

    public void addShape(IShape s) {
        world.add(s);
    }

    public void addLight(ILight light) {
        lights.add(light);
    }

    public Hit intersect(Ray ray) {
        return world.intersect(ray);
    }

    // Public shading method used in main()
    public Color shade(Hit hit, Ray ray) {
        return pathtrace(ray, 6); // default depth = 6
    }

    // Path tracing recursive method
    public Color pathtrace(Ray ray, int depth) {
        if (ray == null || depth <= 0)
            return Color.black;

        Hit hit = intersect(ray);
        if (hit == null)
            return getBackground(ray.dir()); // Himmel/Hintergrundfarbe

        IMaterial mat = hit.material();

        Color emission = mat.getEmission(hit); // z.B. Lichtquelle
        Ray secondary = mat.getSecondaryRay(hit);

        if (secondary == null)
            return emission; // Kein Sekundärstrahl

        Color indirect = pathtrace(secondary, depth - 1);

        return Functions.add(emission, indirect);
    }

    // Simpler Himmel: oben blau, unten schwarz
    private Color getBackground(Vec3 dir) {
        double t = 0.5 * (dir.y() + 1.0);
        Color blackScaled = Functions.multiply(1 - t, Color.black);
        Color blueScaled = Functions.multiply(t, new Color(0.5, 0.7, 1.0));
        return Functions.add(blackScaled, blueScaled);
    }
}
