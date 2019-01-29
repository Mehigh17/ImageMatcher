package main.controllers;

import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import main.services.ImageDataProvider;

public class MainController {

    public Button selectFileButton;
    public Button selectDirectoryButton;
    public BarChart barChart;

    private ImageDataProvider _imageDataProvider;

    public MainController() {
        _imageDataProvider = new ImageDataProvider();
    }

    public void bttDirectoryClicked() {

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

        try {
            if(!file.exists())
                throw new Exception("File doesn't exist!");

            var fileProtocol = String.format("file:%s", file.getAbsolutePath());
            var distribution = _imageDataProvider.getGrayscaleDistribution(new Image(fileProtocol));

            var series = new XYChart.Series();
            distribution.forEach((k, v) -> {
                series.getData().add(new XYChart.Data(String.format("%s", k), v));
            });
            barChart.getData().addAll(series);

            var chartTitle = String.format("Grayscale distribution for '%s'", file.getName());
            barChart.setTitle(chartTitle);
        } catch (Exception e) {
            var alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("An error occurred!");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

}
