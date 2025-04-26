package cgg;

import tools.*;


import java.util.List;
import java.util.ArrayList;

public class Raytracer implements ISampler {
    private final Camera cam;
    private final List<Sphere> spheres;

    public Raytracer(Camera cam) {
        this.cam = cam;
        this.spheres = new ArrayList<>();

        //Collection of spheres
        spheres.add(new Sphere(new Vec3(-3, -3, -4), 1));
        spheres.add(new Sphere(new Vec3(-2, 0, -2), 1));
        spheres.add(new Sphere(new Vec3(-15, 15, -15), 5));
        spheres.add(new Sphere(new Vec3(1.5, 1.5, -4), 1));
        spheres.add(new Sphere(new Vec3(-1.5, 1.5, -4), 1));
        spheres.add(new Sphere(new Vec3(0, -1, -4), 1));
        spheres.add(new Sphere(new Vec3(5, -4, -6), 3));
        spheres.add(new Sphere(new Vec3(0, 4, -4), -1.4));
        spheres.add(new Sphere(new Vec3(4, 5, -7), 2));
        spheres.add(new Sphere(new Vec3(0,-4, -4), 1));
    }

    @Override
    public Color getColor(Vec2 pixel) {
        Ray ray = cam.generateRay(pixel);
        return raytrace(ray);
    }
    //Perform ray tracing to find the closest intersection with a sphere
    private Color raytrace(Ray ray) {
        Hit closest = null;

        for (Sphere s : spheres) {
            Hit h = s.intersect(ray);
            if (h != null && (closest == null || h.t() < closest.t())) {
                closest = h;
            }
        }

        if (closest != null) {
            return shade(closest);
        }

        //Backgound color
        return Color.blue;
    }

    private static Color shade(Hit hit) {
        Vec3 lightDir = Functions.normalize(new Vec3(1, 1, 0.5));
        Color ambient = Functions.multiply(0.1, hit.c());
        Color diffuse = Functions.multiply(0.9 * Math.max(0, Functions.dot(lightDir, hit.n())), hit.c());
        return Functions.add(ambient, diffuse);
    }

}
