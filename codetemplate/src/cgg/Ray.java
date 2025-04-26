package cgg;


import tools.*;

public record Ray(Vec3 pos,Vec3 dir, double tMin,double tMax) {
 
    public Ray(Vec3 pos, Vec3 dir){
        this(pos,dir, 0 , Double.MAX_VALUE);
    }
     //Returns the point on the ray at distance t
    public Vec3 pointAt(double t){
       return new Vec3(t*dir.u() + pos.u(), t*dir.v() + pos.v(), t*dir.w() + pos.w());
       
    }
    //Checks if the parameter t is in range [tMin, tMax]
   public boolean isValid(double t){
    if((t >= tMin) && (t <= tMax)){
    return true;
   }else{
    return false;
   } 
}
}
