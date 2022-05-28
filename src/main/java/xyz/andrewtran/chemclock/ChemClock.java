package xyz.andrewtran.chemclock;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ChemClock extends Application {

    @Override
    public void start(Stage stage) {
        stage.initStyle(StageStyle.UNDECORATED);

        // Full screen if on PI
        if (isProductionEnvironment()) {
            stage.setAlwaysOnTop(true);
            stage.setFullScreen(true);
        }

        Label l = new Label("Chemistry Project Test...");
        Scene scene = new Scene(new StackPane(l), 480, 320);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public boolean isProductionEnvironment() {
        return !System.getProperty("os.name").contains("Windows");
    }
}
