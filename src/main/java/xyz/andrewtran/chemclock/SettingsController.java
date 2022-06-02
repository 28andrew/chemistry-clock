package xyz.andrewtran.chemclock;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;

public class SettingsController {
    ChemClock chemClock;
    private Configuration config;

    @FXML
    private Button doneButton;
    @FXML
    private Button shutdownButton;
    @FXML
    private CheckBox dateFormatCheck;
    @FXML
    private ChoiceBox<String> backgroundChoice;

    @FXML
    public void initialize() {
        chemClock = ChemClock.instance;
        config = chemClock.getConfig();

        shutdownButton.setOnAction(event -> {
            try {
                Process p = Runtime.getRuntime().exec("sudo shutdown -h now");
                p.waitFor();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        doneButton.setOnAction(event -> this.save());

        // SETTINGS
        dateFormatCheck.setSelected(config.isUsing24Format());
        backgroundChoice.getItems().addAll(Configuration.backgroundMap.keySet());
        for (String customBackgroundName : chemClock.getCustomBackgrounds().imageMap.keySet()) {
            backgroundChoice.getItems().add(">" + customBackgroundName);
        }
        backgroundChoice.getSelectionModel().select(config.getBackground());

        // INIT TIMELINE
    }

    public void save() {
        config.set("use24", String.valueOf(dateFormatCheck.isSelected()));
        config.set("background", backgroundChoice.getSelectionModel().getSelectedItem());

        config.save();
        chemClock.switchScreen(ChemClock.Screen.MAIN);
    }
}
