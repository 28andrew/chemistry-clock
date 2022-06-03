package xyz.andrewtran.chemclock;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SpritesWidget extends Widget {
    private static List<Image> sprites = new ArrayList<>();
    private Image currentImage = null;
    private int lastHourIndex = -1;
    private final Random random = new Random(System.currentTimeMillis());

    static {
        for (String name : new String[]{"atom","beaker","CH3COOH","lattice","life","messy","organic",
                "organic2","scientist","tube","burner"}) {
            try {
                sprites.add(new Image(SpritesWidget.class.getClassLoader().getResource("sprites/" + name + ".png")
                        .openStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    void draw(GraphicsContext context, Color fontColor, int hourIndex) {
        if (currentImage == null || lastHourIndex != hourIndex) {
            lastHourIndex = hourIndex;
            currentImage = sprites.get(random.nextInt(sprites.size()));
        }

        context.drawImage(currentImage, 315, 90);
    }
}
