package xyz.andrewtran.chemclock;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Background {
    private String name;
    private Color timeColor;
    private Image image;

    public Background(String name, Color timeColor) {
        this.name = name;
        this.timeColor = timeColor;
    }

    public String getName() {
        return name;
    }

    public Color getTimeColor() {
        return timeColor;
    }

    @SuppressWarnings("ConstantConditions")
    public Image getImage() {
        if (image != null) {
            return image;
        }

        try {
            image = new Image(Background.class.getClassLoader().getResource("background/" + name + ".png").openStream());
            return image;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
