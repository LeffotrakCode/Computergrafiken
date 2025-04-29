package cgg;

import java.util.ArrayList;
import cgg.ILight.LightInfo;
import tools.*;

public class Scene {
    private ArrayList<Sphere> spheres = new ArrayList<>();
    private ArrayList<ILight> lights = new ArrayList<>();

    public Scene() {
        spheres = new ArrayList<>();
        lights = new ArrayList<>();
    }

    public void addSphere(Sphere sphere) {
        spheres.add(sphere);
    }

    public void addLight(ILight light) {
        lights.add(light);
    }

    public Hit intersect(Ray ray) {
        Hit closest = null;
        for (Sphere s : spheres) {
            Hit h = s.intersect(ray);
            if (h != null && (closest == null || h.t() < closest.t())) {
                closest = h;
            }
        }
        return closest;
    }

    public Color shade(Hit hit, Ray ray) {
        Color finalColor = Color.black;
    
        for (ILight l : lights) {
            LightInfo lightInfo = l.info(hit.x());
    
            // Schattenstrahl in Richtung Licht
            Ray shadowRay = new Ray(
                hit.x(),
                lightInfo.direction(),
                Functions.EPSILON,
                lightInfo.distance()
            );
            Hit shadowHit = intersect(shadowRay);
    
            Color ambient = Functions.multiply(hit.c(), Functions.multiply(0.2, lightInfo.intensity()));
            Color diffuse = Color.black;
            Color specular = Color.black;
    
            if (shadowHit == null) {
                // Diffusanteil
                double nDotL = Math.max(0, Functions.dot(hit.n(), lightInfo.direction()));
                diffuse = Functions.multiply(hit.c(), Functions.multiply(nDotL, lightInfo.intensity()));
    
                // Richtung zur Kamera
                Vec3 v = Functions.normalize(Functions.negate(ray.dir()));
    
                // Licht kommt aus -lightDir
                Vec3 lightVec = Functions.negate(lightInfo.direction());
                Vec3 n = hit.n();
    
                // Reflexionsrichtung r = l - 2*(l·n)*n
                Vec3 r = Functions.subtract(
                    lightVec,
                    Functions.multiply(2 * Functions.dot(lightVec, n), n)
                );
                r = Functions.normalize(r);
    
                // Spekularanteil
                double rDotV = Math.max(0, Functions.dot(r, v));
                double alpha = 100.0;
                Color ks = Color.white;
                specular = Functions.multiply(ks, Functions.multiply(Math.pow(rDotV, alpha), lightInfo.intensity()));
            }
    
            finalColor = Functions.add(finalColor, ambient, diffuse, specular);
        }
    
        return finalColor;
    }
    
}
