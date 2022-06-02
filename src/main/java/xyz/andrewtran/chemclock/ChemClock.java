package xyz.andrewtran.chemclock;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class ChemClock extends Application {
    public static ChemClock instance;

    private Stage stage;
    private MainController mainController;
    private SettingsController settingsController;
    private Scene mainScene, settingsScene;
    private Screen currentScreen = null;
    private Configuration configuration = new Configuration();
    private CustomBackgrounds customBackgrounds = new CustomBackgrounds();

    @Override
    public void start(Stage stage) throws IOException {
        instance = this;
        this.stage = stage;
        stage.initStyle(StageStyle.UNDECORATED);

        // Full screen if on PI
        if (isProductionEnvironment()) {
            stage.setAlwaysOnTop(true);
            stage.setFullScreen(true);
        }

        // Initialize stage
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("main.fxml"));
        Parent root = loader.load();
        mainController = loader.getController();

        mainScene = new Scene(root, 480, 320);

        FXMLLoader loader2 = new FXMLLoader(getClass().getClassLoader().getResource("settings.fxml"));
        Parent root2 = loader2.load();
        settingsController = loader2.getController();

        mainController.chemClock = this;
        settingsController.chemClock = this;

        settingsScene = new Scene(root2, 480, 320);
        switchScreen(Screen.MAIN);
    }

    public void switchScreen(Screen screen) {
        if (currentScreen == screen) {
            return;
        }
        stage.setScene(screen == Screen.MAIN ? mainScene : settingsScene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public boolean isProductionEnvironment() {
        return !System.getProperty("os.name").contains("Windows");
    }

    public Configuration getConfig() {
        return configuration;
    }

    public CustomBackgrounds getCustomBackgrounds() {
        return customBackgrounds;
    }

    public enum Screen {
        MAIN, SETTINGS;
    }
}
