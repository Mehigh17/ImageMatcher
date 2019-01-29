package main.models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class ResultModel {

    public ResultModel(String filePath, int distance) {
        this.filePath = new SimpleStringProperty(filePath);
        this.distance = new SimpleIntegerProperty(distance);
    }

    public String getFilePath() {
        return filePath.get();
    }

    public void setFilePath(String filePath) {
        this.filePath.set(filePath);
    }

    public int getDistance() {
        return distance.get();
    }

    public void setDistance(int distance) {
        this.distance.set(distance);
    }

    private SimpleStringProperty filePath;
    private SimpleIntegerProperty distance;

}
