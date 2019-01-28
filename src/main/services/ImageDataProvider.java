package main.services;

import javafx.scene.image.Image;
import main.utils.ColorHelper;

import java.util.HashMap;

public class ImageDataProvider {

    public HashMap<Integer, Integer> getGrayscaleDistribution(Image image) throws Exception {
        if(image == null)
            throw new Exception("Image cannot be null.");

        var distributionMap = new HashMap<Integer, Integer>();
        var reader = image.getPixelReader();
        for(var x = 0; x < image.getWidth(); x++)
        {
            for(var y = 0; y < image.getHeight(); y++)
            {
                var grayScale = ColorHelper.getGrayscale(reader.getColor(x, y));
                var occurrences = 1;
                if(distributionMap.containsKey(grayScale))
                {
                    occurrences = distributionMap.get(grayScale) + 1;
                    distributionMap.remove(grayScale);
                }
                distributionMap.put(grayScale, occurrences);
            }
        }

        return distributionMap;
    }

}
