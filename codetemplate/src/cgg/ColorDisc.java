
package cgg;
import tools.*;
/**
 * @centerDot is the Starting Point of the Disk.
 * @r is the radius. 
 */
public record ColorDisc(Vec2 centerDot, double r, Color color,Color background) implements ISampler {
    
     
    //Everything past the Radius gets defaulted to @background.
    
    public Color getColor(Vec2 testingPixel) {
        
        double x1 = centerDot.u();
        double y1 = centerDot.v();
        double x2 = testingPixel.u();
        double y2 = testingPixel.v();
        double distance = Math.pow((x2 - x1),2) +Math.pow((y2 - y1),2)  ;
        double rsq = Math.pow(r, 2);
        if(distance <= rsq){
            return color;
        }else{
        
        return background;
    }
    
}
}
