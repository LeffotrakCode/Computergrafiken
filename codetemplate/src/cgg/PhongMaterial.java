package cgg;

import tools.Color;
import tools.Functions;
import tools.Vec3;

public record PhongMaterial(Color diffuse, Color specular, double shininess) implements IMaterial {
    @Override
    public Color getDiffuse(Hit hit){
        return diffuse;
    }
    @Override
    public Color getSpecular(Hit hit){
        return specular;
    }
    @Override
    public double getShininess(Hit hit){
        return shininess;
    }
    @Override
    public Color getEmission(Hit hit){
        return Color.black;
    }
    @Override
    public Ray getSecondaryRay(Hit hit) {
        Vec3 n = hit.n();
        Vec3 dir;
        double x, y, z;

        do {
            x = 2 * Math.random() - 1;
            y = 2 * Math.random() - 1;
            z = 2 * Math.random() - 1;
            dir = new Vec3(x, y, z);
        } while (Functions.dot(dir, dir)
 >= 1 || Functions.dot(dir, n) <= 0);

        dir = Functions.normalize(dir);
        return new Ray(hit.x(), dir, Functions.EPSILON, Double.POSITIVE_INFINITY);
    }

}

