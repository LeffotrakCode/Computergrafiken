package cgg;

import tools.*;

import java.util.ArrayList;
import java.util.List;

/**
 * A group of objects with a transformation matrix.
 * Transforms rays and normals into local space and back.
 */
public final class Group implements IShape {

    private final Mat44 transform;
    private final Mat44 inverse;
    private final Mat44 transposedInverse;

    private final List<IShape> children = new ArrayList<>();

    public Group(Mat44 transform) {
        this.transform = transform;
        this.inverse = transform.inverted();            // local to world transformation
        this.transposedInverse = inverse.transposed();  // for normals
    }

    public void add(IShape child) {
        children.add(child);
    }

    @Override
    public Hit intersect(Ray ray) {
         // Transform the ray into the group's local space
        Ray localRay = ray.transform(inverse);

        Hit closest = null;
        for (IShape child : children) {
            Hit hit = child.intersect(localRay);
            if (hit != null && (closest == null || hit.t() < closest.t())) {
                closest = hit;
            }
        }

        if (closest == null) return null;

        // Local coordinates to world coordinates
        Vec3 worldPos = transformPoint(closest.x(), transform);
        Vec3 worldNormal = transformNormal(closest.n(), transposedInverse);
        worldNormal =Functions.normalize(worldNormal);
        return new Hit(closest.t(), worldPos, worldNormal, closest.uv(), closest.material());
    }

   private Vec3 transformPoint(Vec3 p, Mat44 m) {
    double x = p.x(), y = p.y(), z = p.z();
    double tx = m.get(0,0)*x + m.get(0,1)*y + m.get(0,2)*z + m.get(0,3);
    double ty = m.get(1,0)*x + m.get(1,1)*y + m.get(1,2)*z + m.get(1,3);
    double tz = m.get(2,0)*x + m.get(2,1)*y + m.get(2,2)*z + m.get(2,3);
    return new Vec3(tx, ty, tz);
}


    private Vec3 transformNormal(Vec3 n, Mat44 m) {
        double x = n.x(), y = n.y(), z = n.z();
        double nx = x * m.get(0, 0) + y * m.get(1, 0) + z * m.get(2, 0);
        double ny = x * m.get(0, 1) + y * m.get(1, 1) + z * m.get(2, 1);
        double nz = x * m.get(0, 2) + y * m.get(1, 2) + z * m.get(2, 2);
        return new Vec3(nx, ny, nz);
    }
    

  


   
}
