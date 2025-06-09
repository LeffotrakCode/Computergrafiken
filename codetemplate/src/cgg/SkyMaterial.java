package cgg;

import tools.*;

public class SkyMaterial implements IMaterial {
    ImageTexture texture;

    public SkyMaterial(ImageTexture texture){
        this.texture = texture;
    }

    @Override
    public Color getEmission(Hit hit){
        
        return texture.getColor(hit.uv());
    }
    @Override
    public Ray getSecondaryRay(Hit hit){
        return null;
    }
    @Override
public double getShininess(Hit hit) {
    return 0;  // Beispielwert, da SkyMaterial vermutlich keinen Glanz hat
}

@Override
public Color getDiffuse(Hit hit) {
    return new Color(0, 0, 0);  // kein Diffusanteil
}

@Override
public Color getSpecular(Hit hit) {
    return new Color(0, 0, 0);  // kein Spiegelanteil
}
}
