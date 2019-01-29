package main.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import main.services.ImageComparator;
import main.services.ImageDataProvider;

import javax.xml.transform.Result;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

public class MainController {

    @FXML
    private  Button selectFileButton;

    @FXML
    private Button selectDirectoryButton;

    @FXML
    private Button computeButton;

    private ImageDataProvider _imageDataProvider;
    private ImageComparator _comparator;

    private Image _selectedImage;
    private List<Image> _loadedImages;

    public MainController() {
        _imageDataProvider = new ImageDataProvider();
        _comparator = new ImageComparator(_imageDataProvider);
        _loadedImages = new ArrayList<>();
    }

    public void bttDirectoryClicked() {
        var dirChooser = new DirectoryChooser();
        dirChooser.setTitle("Path to Images");
        var dir = dirChooser.showDialog(null);

        if(dir != null && dir.isDirectory() && dir.exists())
        {
            _loadedImages.clear();
            for (File file : dir.listFiles()) {
                var img = new Image(String.format("file:%s", file.getAbsolutePath()));
                if(!img.isError())
                    _loadedImages.add(img);
            }
        }
    }

    public void bttFileClicked() {
        var fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("PNG", "*.png"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg")
        );
        fileChooser.setTitle("Open Reference Image");
        var file = fileChooser.showOpenDialog(null);

        if(file == null || !file.exists())
            return;

        var fileProtocol = String.format("file:%s", file.getAbsolutePath());
        var image = new Image(fileProtocol);
        _selectedImage = image;
        HashMap<Integer, Integer> distribution;
        try {
            distribution = _imageDataProvider.getGrayscaleDistribution(image);
        } catch (Exception e) {
            popError("Couldn't load the grayscale distribution for the reference image.");
            return;
        }

        ResultsController.Instance.updateBarChart(distribution, file);
    }

    public void bttComputeClicked() {
        if(_selectedImage == null) {
            popError("Please select a reference image.");
        } else if(_loadedImages.isEmpty()) {
            popError("Please select a directory containing images to compare to.");
        } else {
            TreeMap<Integer, Image> imageDistances;
            try {
                imageDistances = _comparator.getImageDistances(_selectedImage, _loadedImages);
            } catch (Exception e) {
                popError("Couldn't compute grayscale distance for the selected images.");
                return;
            }

            ResultsController.Instance.updateDistanceResults(imageDistances);

            Image closestImage;
            try {
                closestImage = _comparator.getClosestImage(_selectedImage, _loadedImages);
            } catch (Exception e) {
                popError("Couldn't fetch the closest image to the reference image.");
                return;
            }

            //closestImageView.setImage(closestImage);
        }
    }

    private void popError(String message) {
        var alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("An error occurred!");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
