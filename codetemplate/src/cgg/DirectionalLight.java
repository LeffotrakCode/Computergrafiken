package cgg;
import tools.*;
public record DirectionalLight(Vec3 direction, Color intensity) implements ILight {
    public DirectionalLight{
        direction = Functions.normalize(direction);
    }

    @Override
    public LightInfo info(Vec3 position) {
        double simulatedDistance = 1000000.0;
        return new LightInfo(
            
            Functions.negate(direction), // Richtung vom Punkt zur Lichtquelle
            intensity,simulatedDistance
                 // Unendliche Entfernung
        );
}
}