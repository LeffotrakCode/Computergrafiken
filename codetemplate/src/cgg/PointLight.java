package cgg;
import tools.*;


public record PointLight(Vec3 position, Color intensity)implements ILight {
    @Override
    public LightInfo info (Vec3 point){
        Vec3 direction = Functions.subtract(position, point); // Richtung vom Punkt zur Lichtquelle
        direction = Functions.normalize(direction);
        double distance = Functions.length(Functions.subtract(position, point)); // Entfernung zur Lichtquelle
        
        return new LightInfo(direction, intensity, distance);
    }
}
