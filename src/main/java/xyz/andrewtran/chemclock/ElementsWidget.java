package xyz.andrewtran.chemclock;

import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class ElementsWidget extends Widget {
    private static final List<Element> elementList = new ArrayList<>();

    private Element currentElement = null;
    private int lastHourIndex = -1;
    private final Random random = new Random(System.currentTimeMillis());

    static {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(Widget.class.getClassLoader().getResourceAsStream("Elements.csv")))){
            String line;
            boolean firstLine = true;
            while ((line = reader.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue;
                }
                String[] split = line.split(",");
                int atomicNumber = Integer.parseInt(split[0]);
                String name = split[1];
                String symbol = split[2];
                String mass = split[3];
                elementList.add(new Element(atomicNumber, name, symbol, mass));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    void draw(GraphicsContext context, Color fontColor, int hourIndex) {
        if (currentElement == null || lastHourIndex != hourIndex) {
            lastHourIndex = hourIndex;
            currentElement = elementList.get(random.nextInt(elementList.size()));
        }

        context.setFill(Color.BLACK);
        context.fillRect(315, 76, 140, 160);
        context.setFill(Color.WHITE);
        context.fillRect(315 + 4, 76 + 4, 140 - 8, 160 - 8);
        context.setFill(Color.BLACK);

        context.setTextAlign(TextAlignment.CENTER);
        context.setTextBaseline(VPos.CENTER);

        context.setFont(Font.font("Arial", 20));
        context.fillText(String.valueOf(currentElement.atomicNumber), 315 + 70, 76 + 30);
        context.setFont(Font.font("Arial", 60));
        context.fillText(currentElement.symbol, 315 + 70, 76 + 70);
        context.setFont(Font.font("Arial", 15));
        context.fillText(currentElement.name, 315 + 70, 76 + 70 + 40);
        context.setFont(Font.font("Arial", 18));
        context.fillText(currentElement.mass, 315 + 70, 76 + 70 + 40 + 28);

        context.setTextAlign(TextAlignment.LEFT);
        context.setTextBaseline(VPos.BASELINE);

    }

    public record Element(int atomicNumber, String name, String symbol, String mass) { }
}
