package cgg;
import tools.*;
public record DirectionalLight(Vec3 direction, Color intensity) implements ILight {


    @Override
    public LightInfo info(Vec3 direction) {
        double simulatedDistance = Double.MAX_VALUE;
        return new LightInfo(
            
            Functions.negate(direction), 
            intensity,simulatedDistance
                
        );
}
}