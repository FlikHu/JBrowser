package Component.UIComponent;

import javafx.animation.FillTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

// Helper class for creating a toggle switch, which is not presented in JavaFX
public class ToggleSwitch extends Parent {

    private BooleanProperty isOn;

    public ToggleSwitch(int width, int height, boolean toggled) {
        isOn = new SimpleBooleanProperty(toggled);

        TranslateTransition translation = new TranslateTransition(Duration.seconds(0.3));
        FillTransition fill = new FillTransition(Duration.seconds(0.3));
        ParallelTransition animation = new ParallelTransition(translation, fill);

        Rectangle rect = new Rectangle();
        rect.setArcWidth(width/2);
        rect.setArcHeight(width/2);
        rect.setWidth(width);
        rect.setHeight(height);
        rect.setFill(isOn.getValue() ? Color.valueOf("#4285f4") : Color.WHITE);
        rect.setStroke(Color.LIGHTGRAY);

        Circle toggle = new Circle();
        toggle.setRadius(width/4);
        toggle.setCenterX(isOn.getValue() ? width*3/4 : width/4);
        toggle.setCenterY(height/2);
        toggle.setFill(Color.valueOf("#efefef"));
        toggle.setStroke(Color.LIGHTGRAY);
        getChildren().addAll(rect, toggle);

        translation.setNode(toggle);
        fill.setShape(rect);

        if(!isOn.getValue()) {
            setOnMouseClicked(event -> {
                if(!isOn.getValue()) {
                    translation.setToX(width/2);
                    fill.setFromValue(Color.WHITE);
                    fill.setToValue(Color.valueOf("#4285f4"));
                } else {
                    translation.setToX(0);
                    fill.setFromValue(Color.valueOf("#4285f4"));
                    fill.setToValue(Color.WHITE);
                }
                animation.play();
                isOn.setValue(!isOn.getValue());
            });
        } else {
            setOnMouseClicked(event -> {
                if(!isOn.getValue()) {
                    translation.setToX(0);
                    fill.setFromValue(Color.WHITE);
                    fill.setToValue(Color.valueOf("#4285f4"));
                } else {
                    translation.setToX(-width/2);
                    fill.setFromValue(Color.valueOf("#4285f4"));
                    fill.setToValue(Color.WHITE);
                }
                animation.play();
                isOn.setValue(!isOn.getValue());
            });
        }
    }

    public BooleanProperty isOnProperty() {
        return isOn;
    }
}