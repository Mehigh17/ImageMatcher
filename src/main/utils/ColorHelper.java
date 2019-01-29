package main.utils;

import javafx.scene.paint.Color;

public final class ColorHelper {

    public static int getGrayscale(Color color) {
        return (int) (((color.getRed() + color.getGreen() + color.getBlue()) / 3.0) * 255);
    }

}
