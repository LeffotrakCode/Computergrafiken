package cgg;
import static tools.Functions.vec2;

import tools.*;

public class Background implements IShape {
    IMaterial material = new SkyMaterial(
        new ImageTexture("codetemplate/src/textures/Sky.jpg")
    );

    @Override
    public Hit intersect(Ray ray){
        if(ray.tMax() >= Double.MAX_VALUE){
            double u = 0; 
            double v = 0;

            return new Hit(
                Double.MAX_VALUE,
                ray.pointAt(Double.MAX_VALUE),
                Functions.negate(ray.dir()),
                vec2(u, v),
                material,
                ray.dir());
        }
        else 
            return null;
    }
}
