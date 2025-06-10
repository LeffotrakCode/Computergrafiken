package cgg;

import tools.Color;
import tools.Functions;
import tools.ISampler;
import tools.Vec2;
import tools.Vec3;

public record TexturedPhongMaterial(ISampler diffuse, Color specular, double shininess) implements IMaterial {
    private static final double RAY_OFFSET = 1e-5;
    private static final Vec2 DEFAULT_UV = new Vec2(0.5, 0.5);

    @Override
    public Color getDiffuse(Hit hit) {
        Vec2 uv = hit.uv() != null ? hit.uv() : DEFAULT_UV;
        return diffuse.getColor(uv);
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
