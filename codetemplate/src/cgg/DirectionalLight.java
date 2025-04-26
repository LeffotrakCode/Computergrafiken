package cgg;
import tools.*;
public record DirectionalLight(Vec3 direction, Color intensity) implements ILight {
    public DirectionalLight{
        direction = Functions.normalize(direction);
    }
    @Override
    public LightInfo info(Vec3 position){
        return new LightInfo(Functions.negate(position),intensity,Double.POSITIVE_INFINITY); 
    }
}
