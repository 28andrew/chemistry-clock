package xyz.andrewtran.chemclock;

import javafx.scene.paint.Color;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Configuration {
    public static final Background[] backgrounds = new Background[]{
        new Background("Caroline", Color.BLUE),
        new Background("Close Containers", Color.WHITE),
        new Background("Colorful Containers", Color.BLACK),
        new Background("Flame Test", Color.WHITE),
        new Background("Lab Fire", Color.BLUE),
        new Background("Laboratory", Color.RED),
        new Background("Organic", Color.YELLOW),
        new Background("Violent", Color.color(0, 1.0, 0))
    };
    public static Map<String, Background> backgroundMap = new HashMap<>();

    static {
        for (Background background : backgrounds) {
            backgroundMap.put(background.getName(), background);
        }
    }

    private final File file = new File("clock.properties");
    private final Properties properties = new Properties();

    public Configuration() {
        // Read file if it exists
        if (file.exists()) {
            try (InputStream fileInputStream = new FileInputStream(file)){
                properties.load(fileInputStream);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void set(String key, String value) {
        properties.setProperty(key, value);
    }

    public String get(String key) {
        return properties.getProperty(key);
    }

    public String get(String key, String defaultVal) {
        return properties.getProperty(key, defaultVal);
    }

    public boolean isUsing24Format() {
        return Boolean.parseBoolean(get("use24", "false"));
    }

    public String getBackground() {
        return get("background", "Colorful Containers");
    }

    public static Background getBackgroundFromName(String name) {
        return backgroundMap.get(name);
    }

    public int getHourOffset() {
        return Integer.parseInt(get("hour_offset", "0"));
    }

    public int getMinuteOffset() {
        return Integer.parseInt(get("minute_offset", "0"));
    }

    public void save() {
        try (OutputStream fileOutputStream = new FileOutputStream(file)) {
            properties.store(fileOutputStream, "ChemClock");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
