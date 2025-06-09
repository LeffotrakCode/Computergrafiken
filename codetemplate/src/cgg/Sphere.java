package cgg;

import tools.*;

public record Sphere(Vec3 position, double radius, IMaterial material) implements IShape {

    @Override
    public Hit intersect(Ray ray) {
        Vec3 o = ray.pos();         // Ursprung des Strahls
        Vec3 d = ray.dir();         // Richtung des Strahls
        Vec3 c = position;          // Mittelpunkt der Kugel

        Vec3 oc = Functions.subtract(o, c);
        double a = Functions.dot(d, d);
        double b = 2.0 * Functions.dot(oc, d);
        double cVal = Functions.dot(oc, oc) - radius * radius;

        double discriminant = b * b - 4 * a * cVal;
        if (discriminant < 0) return null;

        double sqrtDisc = Math.sqrt(discriminant);
        double t1 = (-b - sqrtDisc) / (2 * a);
        double t2 = (-b + sqrtDisc) / (2 * a);

        double t = Double.POSITIVE_INFINITY;
        if (ray.isValid(t1)) t = t1;
        if (ray.isValid(t2) && t2 < t) t = t2;
        if (!ray.isValid(t)) return null;

        Vec3 hitPoint = ray.pointAt(t);
        Vec3 normal = Functions.normalize(Functions.subtract(hitPoint, c));

        // Sphärische UV-Koordinaten berechnen
        Vec3 p = Functions.normalize(Functions.subtract(hitPoint, position));
        double u = 0.5 + Math.atan2(p.z(), p.x()) / (2 * Math.PI);
        double v = 0.5 - Math.asin(p.y()) / Math.PI;
        Vec2 uv = new Vec2(u, v);
        
        return new Hit(t, hitPoint, normal, uv, material, ray.dir());
    }
}
