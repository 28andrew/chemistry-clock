package xyz.andrewtran.chemclock;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.HashMap;
import java.util.Map;

public abstract class Widget {
    public static final Map<String, Widget> widgetMap = new HashMap<>();

    static {
        widgetMap.put("Random Element", new ElementsWidget());
        widgetMap.put("Random Sprite", new SpritesWidget());
    }

    abstract void draw(GraphicsContext context, Color fontColor, int hourIndex);
}
