package cgg;
import static tools.Functions.multiply;

import tools.Color;
import tools.Functions;
import tools.Vec3;
public record MirrorMaterial(Color diffuse, Color specular, double shininess, Color emission) implements IMaterial {
    public MirrorMaterial(){
        this(multiply(0.5, Color.gray), Color.black, 0, Color.black);
    }

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
        return emission;
    }
    @Override
  public Ray getSecondaryRay(Hit hit){
    Vec3 I = hit.incident();
    Vec3 N = hit.n();
    double dot = Functions.dot(I, N);
    Vec3 reflected = Functions.subtract(I, Functions.multiply(2 * dot, N));
    return new Ray(hit.x(), reflected);
}
}