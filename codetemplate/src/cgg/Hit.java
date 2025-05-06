package cgg;

import tools.*;

public record Hit(
    double  t,  //Ray parameter
    Vec3  x,    //Position
    Vec3  n,    //The normal vector at the hit point is used for shading and lighting
    Vec2 uv,
    IMaterial material  
) {}
