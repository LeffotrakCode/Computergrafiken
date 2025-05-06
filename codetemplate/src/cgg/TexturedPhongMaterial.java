package cgg;

import tools.Color;
import tools.ISampler;

public record TexturedPhongMaterial(ISampler diffuse, Color specular, double shininess) implements IMaterial {
    @Override
    public Color getDiffuse(Hit hit) {
        //TODO
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
