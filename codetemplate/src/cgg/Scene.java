package cgg;

import java.util.ArrayList;
import java.util.List;

import cgg.ILight.LightInfo;
import tools.*;

public class Scene {
    private IShape root;
    private List<ILight> lights;

    public Scene(IShape root) {
        this.root = root;
        this.lights = new ArrayList<>();
    }

    public void addLight(ILight light) {
        lights.add(light);
    }

    public Hit intersect(Ray ray) {
        return root.intersect(ray);
    }

    public Color getBackground(Vec3 dir) {
        return new Color(0.2, 0.3, 0.5); // grau-blauer Himmel
    }

    public Color pathtrace(Ray ray, int depth) {
        if (ray == null || depth <= 0) return Color.black;

        Hit hit = intersect(ray);
        if (hit == null) return getBackground(ray.dir());

        IMaterial mat = hit.material();
        Color emission = mat.getEmission(hit);
        Color directLight = Color.black;

        for (ILight light : lights) {
            LightInfo lightInfo = light.info(hit.x());
            Vec3 toLight = lightInfo.direction();
            Ray shadowRay = new Ray(hit.x(), toLight);
            Hit shadowHit = intersect(shadowRay);

            boolean inShadow = shadowHit != null && shadowHit.t() < lightInfo.distance() - 1e-4;

            if (!inShadow) {
                double ndotl = Math.max(0, Functions.dot(hit.n(), toLight));
                Color diffuse = Functions.multiply(ndotl, mat.getDiffuse(hit));

                Vec3 viewDir = Functions.negate(ray.dir());
                Vec3 reflectDir = Functions.reflect(Functions.negate(toLight), hit.n());
                double spec = Math.pow(Math.max(0, Functions.dot(viewDir, reflectDir)), mat.getShininess(hit));
                Color specular = Functions.multiply(spec, mat.getSpecular(hit));

                Color total = Functions.add(diffuse, specular);
                directLight = Functions.add(directLight, Functions.multiply(lightInfo.intensity(), total));
            }
        }

        Ray secondary = mat.getSecondaryRay(hit);
        Color indirect = secondary != null ? pathtrace(secondary, depth - 1) : Color.black;

        return Functions.add(emission, Functions.add(directLight, indirect));
    }

    public Color shade(Hit hit, Ray ray) {
        return pathtrace(ray, 3); // max depth = 3
    }
}
