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
    public Ray transform(Mat44 m) {
    // Transform the origin of the ray
    Vec3 newPos = transformPoint(pos, m);
    
    // Transform the direction of the ray (vector → without translation)
    Vec3 newDir = transformVector(dir, m);
    
    return new Ray(newPos, newDir, tMin, tMax);
}
    // Helper method: Transform a point with matrix (including translation)
    private Vec3 transformPoint(Vec3 p, Mat44 m) {
    double x = p.x(), y = p.y(), z = p.z();
    double tx = m.get(0,0)*x + m.get(0,1)*y + m.get(0,2)*z + m.get(0,3);
    double ty = m.get(1,0)*x + m.get(1,1)*y + m.get(1,2)*z + m.get(1,3);
    double tz = m.get(2,0)*x + m.get(2,1)*y + m.get(2,2)*z + m.get(2,3);
    return new Vec3(tx, ty, tz);
    }

    // Helper method: Transform a direction vector with matrix (without translation)
    private Vec3 transformVector(Vec3 v, Mat44 m) {
    double x = v.x(), y = v.y(), z = v.z();
    double tx = m.get(0,0)*x + m.get(0,1)*y + m.get(0,2)*z;
    double ty = m.get(1,0)*x + m.get(1,1)*y + m.get(1,2)*z;
    double tz = m.get(2,0)*x + m.get(2,1)*y + m.get(2,2)*z;
    return new Vec3(tx, ty, tz);
}

}
