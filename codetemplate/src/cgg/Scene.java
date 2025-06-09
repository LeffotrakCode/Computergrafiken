package cgg;

import java.util.ArrayList;
import cgg.ILight.LightInfo;
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

    public Color shade(Hit hit, Ray ray) {
        Color finalColor = Color.black;
        var kd = hit.material().getDiffuse(hit);
        var ks = hit.material().getSpecular(hit);
        var alpha = hit.material().getShininess(hit);

        for (ILight l : lights) {
            LightInfo lightInfo = l.info(hit.x());
            Ray shadowRay = new Ray(hit.x(), lightInfo.direction(), Functions.EPSILON, lightInfo.distance());
            Hit shadowHit = intersect(shadowRay);

            Color ambient = Functions.multiply(kd, Functions.multiply(0.2, lightInfo.intensity()));
            Color diffuse = Color.black;
            Color specular = Color.black;

            if (shadowHit == null) {
                double nDotL = Math.max(0, Functions.dot(hit.n(), lightInfo.direction()));
                diffuse = Functions.multiply(kd, Functions.multiply(nDotL, lightInfo.intensity()));

                Vec3 v = Functions.normalize(Functions.negate(ray.dir()));
                Vec3 lightVec = Functions.negate(lightInfo.direction());
                Vec3 n = hit.n();

                Vec3 r = Functions.subtract(
                    lightVec,
                    Functions.multiply(2 * Functions.dot(lightVec, n), n)
                );
                r = Functions.normalize(r);

                double rDotV = Math.max(0, Functions.dot(r, v));
                specular = Functions.multiply(ks, Functions.multiply(Math.pow(rDotV, alpha), lightInfo.intensity()));
            }

            finalColor = Functions.add(finalColor, ambient, diffuse, specular);
        }

        return finalColor;
    }
}