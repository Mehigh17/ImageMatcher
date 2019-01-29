package main.tests;

import javafx.scene.image.Image;
import main.services.ImageDataProvider;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class ImageDataProviderTest {

    private ImageDataProvider _imgDataProvider = new ImageDataProvider();

    @Test
    public void GetGrayscaleDistance_TwoDistributionsGiven_Pass() {
        var dist1 = new HashMap<Integer, Integer>(){{
            put(0, 10);
            put(3, 50);
            put(5, 25);
        }};

        var dist2 = new HashMap<Integer, Integer>(){{
            put(0, 3);
            put(4, 20);
            put(5, 10);
        }};

        var distance = _imgDataProvider.getGrayscaleDistance(dist1, dist2);
        assertEquals(distance, 92);
    }

    @Test
    public void GetGrayscaleDistribution_NulImageGiven_ThrowException() {
        assertThrows(Exception.class, () -> _imgDataProvider.getGrayscaleDistribution(null));
    }

    /**
     * A JavaFX runtime instance has to be initialized before test which is cumbersome so don't do this explicitly here.
     * TODO: Implement TestFX library (https://github.com/TestFX/TestFX)
     */
    @Test
    public void GetGrayscaleDistribution_ValidImageGiven_Pass(){
        var image = new Image("file:files/testImage1.png");
        // ... rest of the test here
    }

}