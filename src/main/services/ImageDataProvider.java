package main.services;

import javafx.scene.image.Image;
import main.utils.ColorHelper;

import java.util.HashMap;

public class ImageDataProvider {

    public int getGrayscaleDistance(HashMap<Integer, Integer> dist1, HashMap<Integer, Integer> dist2) {
        int distance = 0;

        for(var i = 0; i < 255; i++)
            distance += Math.abs(dist1.getOrDefault(i, 0) - dist2.getOrDefault(i, 0));

        return distance;
    }

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
