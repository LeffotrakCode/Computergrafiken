package cgg;

import tools.Color;

public record PhongMaterial(Color diffuse, Color specular, double shininess) implements IMaterial {
    @Override
    public Color getDiffuse(Hit hit){

    }
    @Override
    public Color getSpecular(Hit hit){
        return specular;
    }
    @Override
    public double getShininess(Hit hit){
        return shininess;
    }

}
