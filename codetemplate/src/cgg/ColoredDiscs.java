package cgg;

import tools.*;
import java.util.ArrayList;
import java.util.List;

// Manages and generates ColoredDiscs
public class ColoredDiscs implements ISampler {

    private final List<ColorDisc> discs;
    private final Color background;

    public ColoredDiscs(int width, int height, int discCount, Color color, Color background, int howManyColumns) {
        this.discs = new ArrayList<>();
        this.background = background;

        
        int columns = howManyColumns; 
        int rows = (discCount + columns - 1) / columns; 
        
        int spacingX = width / (columns + 1);
        int spacingY = height / (rows + 1);
        
        
        int radius;
        if (spacingX < spacingY) {
            radius = spacingX / 3;
        } else {
            radius = spacingY / 3;
        }
        
        for (int i = 0; i < discCount; i++) {
            int row = i / columns;
            int col = i % columns;
        
            int x = (col + 1) * spacingX;
            int y = (row + 1) * spacingY;
        
            discs.add(new ColorDisc(new Vec2(x, y), radius, color, background));
        }
        
        
    }

 
    public Color getColor(Vec2 point) {

        for (int i = discs.size() - 1; i >= 0; i--) {
            ColorDisc disc = discs.get(i);
            double dx = point.u() - disc.centerDot().u();
            double dy = point.v() - disc.centerDot().v();
            if (dx * dx + dy * dy <= disc.r() * disc.r()) {
                return disc.color();
            }
        }
        return background;
    }
}
