package cgg;
import static tools.Functions.vec2;

import tools.*;

public class Background implements IShape {
    IMaterial material = new SkyMaterial(
        new ImageTexture("codetemplate/src/textures/Sky.jpg")
    );

    @Override
public Hit intersect(Ray ray) {
    // Return a hit very far away to simulate background
    double t = 1e30;
    Vec3 point = ray.pointAt(t);
    Vec3 normal = Functions.negate(ray.dir());
    
    // Compute spherical coordinates for texture lookup
    double u = 0.5 + (Math.atan2(ray.dir().z(), ray.dir().x()) / (2 * Math.PI));
    double v = 0.5 - (Math.asin(ray.dir().y()) / Math.PI);

    return new Hit(t, point, normal, vec2(u, v), material, ray.dir());
}
}
