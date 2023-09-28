package utils;

import java.awt.*;

public interface Drawable {
    void draw(Graphics2D g, int xOffset, int yOffset);

    int getY();
}
