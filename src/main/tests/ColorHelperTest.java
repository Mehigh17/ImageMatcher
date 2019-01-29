package main.tests;

import javafx.scene.paint.Color;
import main.utils.ColorHelper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ColorHelperTest {

    @Test
    void GetGrayscale_WhiteColorGiven_Pass() {
        var grayScale = ColorHelper.getGrayscale(Color.WHITE);
        assertEquals(grayScale, 255);
    }

}