package main.services;

import javafx.scene.image.Image;

import java.util.List;
import java.util.TreeMap;

public class ImageComparator {

    private ImageDataProvider _imageDataProvider;

    public ImageComparator(ImageDataProvider imageDataProvider) {
        if(imageDataProvider == null)
            throw new IllegalArgumentException(String.format("%s cannot be null.", ImageDataProvider.class.toString()));

        _imageDataProvider = imageDataProvider;
    }

    public TreeMap<Integer, Image> getImageDistances(Image refImage, List<Image> images) throws Exception {
        if(refImage == null)
            throw new IllegalArgumentException("Reference image cannot be null.");

        if(images == null)
            throw new IllegalArgumentException("Images list cannot be null.");

        if(images.isEmpty())
            throw new IllegalArgumentException("Images list cannot be empty.");

        var refDistribution = _imageDataProvider.getGrayscaleDistribution(refImage);
        var distances = new TreeMap<Integer, Image>();
        for(Image img : images) {
            var imgDist = _imageDataProvider.getGrayscaleDistribution(img);
            distances.put(_imageDataProvider.getGrayscaleDistance(refDistribution, imgDist), img);
        }

        return distances;
    }

    public Image getClosestImage(Image refImage, List<Image> images) throws Exception {
        var distances = getImageDistances(refImage, images);
        return distances.get(distances.firstKey());
    }

}
