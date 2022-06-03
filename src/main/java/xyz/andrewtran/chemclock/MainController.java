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
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class MainController {
    ChemClock chemClock;

    @FXML
    private Canvas canvas;
    private GraphicsContext graphicsContext;
    @FXML
    private Button settingsButton;
    private Font digitalFont;

    @FXML
    public void initialize() {
        chemClock = ChemClock.instance;
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
        Timeline timeline = new Timeline();
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
        Color fontColor = Color.BLACK;
        graphicsContext.setFill(fontColor);
        if (!backgroundName.startsWith(">")) {
            Background background = Configuration.getBackgroundFromName(backgroundName);
            graphicsContext.drawImage(background.getImage(), 0, 0);
            fontColor = background.getTimeColor();
            graphicsContext.setFill(fontColor);
        } else {
            String customBackgroundName = backgroundName.substring(1);
            Image customBackground = chemClock.getCustomBackgrounds().imageMap.get(customBackgroundName);
            graphicsContext.drawImage(customBackground, 0, 0);
        }
        // Widget
        String widgetName = chemClock.getConfig().getWidget();
        if (!widgetName.equals("None")) {
            Widget widget = Widget.widgetMap.get(widgetName);
            widget.draw(graphicsContext, fontColor, chemClock.getConfig().getHourIndex());

        }
        // Time
        graphicsContext.setFont(digitalFont);
        graphicsContext.setFill(fontColor);
        graphicsContext.fillText(chemClock.getConfig().getTimeFormatted(), 5, 200);
    }
}
