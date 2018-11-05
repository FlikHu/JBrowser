package Component.UIComponent;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;


public class PercentageButton extends HBox {
    private DoubleProperty percentage;

    public PercentageButton(int width, int height, int value) {
        percentage = new SimpleDoubleProperty(value);
        this.maxHeight(height);
        this.maxWidth(width);

        Button minus = new Button("-");
        minus.setMaxSize(height,height);
        minus.setStyle("-fx-border-width: 1px; -fx-border-color: lightgray; -fx-background-color: #efefef");
        minus.setOnAction((actionEvent -> {
            if (percentage.getValue() > 10) percentage.setValue(percentage.getValue()-10);
        }));

        Button plus = new Button("+");
        plus.setMaxSize(height,height);
        plus.setStyle("-fx-border-width: 1px; -fx-border-color: lightgray; -fx-background-color: #efefef");
        plus.setOnAction((actionEvent -> {
            if (percentage.getValue() < 500) percentage.setValue(percentage.getValue()+10);
        }));

        TextField num = new TextField(Integer.toString(value)+"%");
        num.setStyle("-fx-background-color: white");

        num.setMaxWidth(width);
        num.setMinHeight(height);

        percentage.addListener(((observableValue, oldValue, newValue) -> {
            if(newValue.intValue() <= 10) {
                minus.setDisable(true);
            } else {
                minus.setDisable(false);
            }

            if(newValue.intValue() >= 490) {
                plus.setDisable(true);
            } else {
                plus.setDisable(false);
            }

            num.setText(Integer.toString(newValue.intValue())+"%");
        }));
        this.setAlignment(Pos.CENTER);
        this.getChildren().addAll(minus,num,plus);
    }

    public DoubleProperty getPercentage() {
        return percentage;
    }
}
