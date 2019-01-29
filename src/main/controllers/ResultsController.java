package main.controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import main.models.ResultModel;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.TreeMap;

public class ResultsController implements Initializable {

    /**
     * This method is a placeholder until the DI container is implemented
     * TODO: Implement a MessageBus through an IoC container
     */
    public static ResultsController Instance; // To be removed

    @FXML
    private BarChart barChart;

    @FXML
    private ImageView referenceImageView;

    @FXML
    private TableView<ResultModel> resultData;

    @FXML
    private TableColumn<ResultModel, String> filePath;

    @FXML
    private TableColumn<ResultModel, Integer> distance;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Instance = this; // To be removed

        filePath.setCellValueFactory(new PropertyValueFactory<>("FilePath"));
        distance.setCellValueFactory(new PropertyValueFactory<>("Distance"));
    }

    public void updateBarChart(HashMap<Integer, Integer> refDistr, File refFile) {
        barChart.getData().clear();

        var series = new XYChart.Series();
        refDistr.forEach((k, v) -> {
            series.getData().add(new XYChart.Data(String.format("%s", k), v));
        });
        barChart.getData().addAll(series);

        var chartTitle = String.format("Grayscale distribution for '%s'", refFile.getName());
        barChart.setTitle(chartTitle);
    }

    public void updateDistanceResults(TreeMap<Integer, Image> distances) {
        var list = new ArrayList<ResultModel>();
        distances.forEach((k, v) -> {
            list.add(new ResultModel(v.getUrl(), k));
        });
        resultData.setItems(FXCollections.observableArrayList(list));

        referenceImageView.setImage(distances.get(distances.firstKey()));
    }

}
