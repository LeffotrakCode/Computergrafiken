package cgg;

import tools.Color;
import tools.Functions;
import tools.Vec3;

public record PhongMaterial(Color diffuse, Color specular, double shininess) implements IMaterial {
    private static final double RAY_OFFSET = 1e-5;

    @Override
    public Color getDiffuse(Hit hit) {
        return diffuse;
    }

    @Override
    public Color getSpecular(Hit hit) {
        return specular;
    }

    @Override
    public double getShininess(Hit hit) {
        return shininess;
    }

    @Override
    public Color getEmission(Hit hit) {
        return Color.black;
    }

    @Override
    public Ray getSecondaryRay(Hit hit) {
        Vec3 dir;
        Vec3 n = hit.n();
        do {
            dir = new Vec3(
                2 * Math.random() - 1,
                2 * Math.random() - 1,
                2 * Math.random() - 1
            );
        } while (Functions.length(dir) > 1 || Functions.dot(dir, n) < 0);
        dir = Functions.normalize(dir);
        Vec3 origin = Functions.add(hit.x(), Functions.multiply(RAY_OFFSET, n));
        return new Ray(origin, dir);
    }
}
