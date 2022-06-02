package xyz.andrewtran.chemclock;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TouchEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainController {
    ChemClock chemClock;

    @FXML
    private Canvas canvas;
    private GraphicsContext graphicsContext;
    @FXML
    private Button settingsButton;
    private Timeline timeline;
    private Font digitalFont;
    private DateFormat dateFormat12 = new SimpleDateFormat("hh:mm");
    private DateFormat dateFormat24 = new SimpleDateFormat("HH:mm");

    @FXML
    public void initialize() {
        // Settings button hide logic
        settingsButton.setVisible(false);
        canvas.requestFocus();
        canvas.addEventFilter(MouseEvent.ANY, new EventHandler<>() {
            long startTime;

            @Override
            public void handle(MouseEvent event) {
                if (event.getEventType().equals(MouseEvent.MOUSE_CLICKED)) {
                    startTime = System.currentTimeMillis();
                } else if (event.getEventType().equals(MouseEvent.MOUSE_RELEASED) &&
                        (System.currentTimeMillis() - startTime) > 2000) {
                    // Long press detected..
                    settingsButton.setVisible(!settingsButton.isVisible());
                }
            }
        });
        settingsButton.setOnAction(event -> {
            chemClock.switchScreen(ChemClock.Screen.SETTINGS);
            settingsButton.setVisible(false);
        });

        // Font
        digitalFont = Font.loadFont(getClass().getClassLoader().getResourceAsStream("digital-mono.ttf"), 120);

        // Init
        startLoop();
    }

    private void startLoop() {
        Duration oneFrameAmt = Duration.millis(1000 / 15f); // 15 fps
        KeyFrame oneFrame = new KeyFrame(oneFrameAmt, this::render);
        timeline = new Timeline();
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.getKeyFrames().add(oneFrame);
        timeline.play();
    }

    private void render(ActionEvent actionEvent) {
        graphicsContext = canvas.getGraphicsContext2D();
        // Clear
        graphicsContext.setFill(Color.WHITE);
        graphicsContext.fillRect(0, 0, 480, 320);
        // Background
        String backgroundName = chemClock.getConfig().getBackground();
        graphicsContext.setFill(Color.BLACK);
        if (!backgroundName.startsWith(">")) {
            Background background = Configuration.getBackgroundFromName(backgroundName);
            graphicsContext.drawImage(background.getImage(), 0, 0);
            graphicsContext.setFill(background.getTimeColor());
        } else {
            String customBackgroundName = backgroundName.substring(1);
            Image customBackground = chemClock.getCustomBackgrounds().imageMap.get(customBackgroundName);
            graphicsContext.drawImage(customBackground, 0, 0);
        }
        // Time
        graphicsContext.setFont(digitalFont);
        String time = (chemClock.getConfig().isUsing24Format() ? dateFormat24 : dateFormat12).format(new Date());
        graphicsContext.fillText(time, 5, 200);
    }
}
