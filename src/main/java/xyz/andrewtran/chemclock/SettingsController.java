package xyz.andrewtran.chemclock;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.util.Duration;

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
    private Label timeLabel;
    @FXML
    private Button hourPlusButton;
    @FXML
    private Button hourMinusButton;
    @FXML
    private Button minutePlusButton;
    @FXML
    private Button minuteMinusButton;
    @FXML
    private Button hourPlus5Button;
    @FXML
    private Button hourMinus5Button;
    @FXML
    private Button minutePlus5Button;
    @FXML
    private Button minuteMinus5Button;
    @FXML
    private ChoiceBox<String> widgetChoice;

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

        hourPlusButton.setOnAction(event -> {
            config.changeHourOffsetBy(1);
        });
        hourMinusButton.setOnAction(event -> {
            config.changeHourOffsetBy(-1);
        });
        minutePlusButton.setOnAction(event -> {
            config.changeMinuteOffsetBy(1);
        });
        minuteMinusButton.setOnAction(event -> {
            config.changeMinuteOffsetBy(-1);
        });
        hourPlus5Button.setOnAction(event -> {
            config.changeHourOffsetBy(5);
        });
        hourMinus5Button.setOnAction(event -> {
            config.changeHourOffsetBy(-5);
        });
        minutePlus5Button.setOnAction(event -> {
            config.changeMinuteOffsetBy(5);
        });
        minuteMinus5Button.setOnAction(event -> {
            config.changeMinuteOffsetBy(-5);
        });
        widgetChoice.getItems().add("None");
        widgetChoice.getItems().addAll(Widget.widgetMap.keySet());
        widgetChoice.getSelectionModel().select(config.getWidget());

        // INIT TIMELINE
        startLoop();
    }

    private void startLoop() {
        Duration oneFrameAmt = Duration.millis(1000 / 10f); // 10 fps
        KeyFrame oneFrame = new KeyFrame(oneFrameAmt, this::render);
        Timeline timeline = new Timeline();
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.getKeyFrames().add(oneFrame);
        timeline.play();
    }

    private void render(ActionEvent actionEvent) {
       timeLabel.setText(chemClock.getConfig().getTimeFormatted());
    }

    public void save() {
        config.set("use24", String.valueOf(dateFormatCheck.isSelected()));
        config.set("background", backgroundChoice.getSelectionModel().getSelectedItem());
        config.set("widget", widgetChoice.getSelectionModel().getSelectedItem());

        config.save();
        chemClock.switchScreen(ChemClock.Screen.MAIN);
    }
}
