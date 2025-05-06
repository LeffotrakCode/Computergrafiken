package cgg;

import tools.*;
//TODO - Umbau auf Nutzung von Imaterial-Interface
public record Sphere(Vec3 position, double radius, IMaterial material) {

    public Hit intersect(Ray ray) {
        Vec3 o = ray.pos();
        Vec3 d = ray.dir();
        Vec3 c = position;

        Vec3 oc = Functions.subtract(o,c);
        double a = Functions.dot(d, d);
        double b = 2.0 * Functions.dot(oc, d);
        double cVal = Functions.dot(oc, oc) - radius * radius;
      

        double discriminant = Math.pow(b, 2) - 4 * a * cVal;
        if (discriminant < 0) {
            return null;//no hit
        }

        double sqrtDisc = Math.pow(discriminant, 0.5);
        double t1 = (-b - sqrtDisc) / (2 * a);
        double t2 = (-b + sqrtDisc) / (2 * a);

        double t = Double.POSITIVE_INFINITY;
        
        if (ray.isValid(t1) && t1 < t) {
          t = t1;  
        }
        if (ray.isValid(t2) && t2 < t){
           t = t2; 
        } 
        if (!ray.isValid(t)) return null;
        
        Vec3 point = ray.pointAt(t);
        Vec3 normal = Functions.normalize(Functions.subtract(point,position));

        //TODO calculate spherical texcoords
        //TODO
        //TODO
        //LOOK Implementierung Texturkoordinaten Kugel
        
        return new Hit(t, point, normal, vec2(u,v), material);
        
    }
}
