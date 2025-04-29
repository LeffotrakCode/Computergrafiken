package cgg;
import tools.*;


public record PointLight(Vec3 position, Color intensity)implements ILight {
    @Override
    public LightInfo info (Vec3 point){
        // Direction from point to light source
        Vec3 direction = Functions.subtract(position, point);
        direction = Functions.normalize(direction);
        // Distance to the light source
        double distance = Functions.length(Functions.subtract(position, point));
        
        return new LightInfo(direction, intensity, distance);
    }
}
