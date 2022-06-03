package xyz.andrewtran.chemclock;

import javafx.scene.paint.Color;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Configuration {
    public static final Background[] backgrounds = new Background[]{
        new Background("Caroline", Color.BLUE),
        new Background("Close Containers", Color.WHITE),
        new Background("Colorful Containers", Color.BLACK),
        new Background("Flame Test", Color.WHITE),
        new Background("Lab Fire", Color.BLUE),
        new Background("Laboratory", Color.RED),
        new Background("Organic", Color.YELLOW),
        new Background("Violent", Color.color(0, 1.0, 0)),
        new Background("Your Favorites", Color.color(0.3019608f, 0.06666667f, 1.0))
    };
    public static Map<String, Background> backgroundMap = new HashMap<>();

    static {
        for (Background background : backgrounds) {
            backgroundMap.put(background.getName(), background);
        }
    }

    private static final DateFormat DATE_FORMAT_12 = new SimpleDateFormat("hh:mm");
    private static final DateFormat DATE_FORMAT_24 = new SimpleDateFormat("HH:mm");

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

    public String getWidget() {
        return get("widget", "None");
    }

    public static Background getBackgroundFromName(String name) {
        return backgroundMap.get(name);
    }

    public int getHourOffset() {
        return Integer.parseInt(get("hour_offset", "0"));
    }

    public void changeHourOffsetBy(int delta) {
        set("hour_offset", Integer.toString(getHourOffset() + delta));
    }

    public int getHourIndex() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(getCurrentDate());
        return calendar.get(Calendar.HOUR) + getHourOffset();
    }

    public void changeMinuteOffsetBy(int delta) {
        set("minute_offset", Integer.toString(getMinuteOffset() + delta));
    }

    public int getMinuteOffset() {
        return Integer.parseInt(get("minute_offset", "0"));
    }


    public Date getCurrentDate() {
        long currentMillis = new Date().getTime();
        currentMillis += TimeUnit.HOURS.toMillis(getHourOffset());
        currentMillis += TimeUnit.MINUTES.toMillis(getMinuteOffset());
        return new Date(currentMillis);
    }

    public String getTimeFormatted(boolean use24Format) {
        Date date = getCurrentDate();
        return use24Format ? DATE_FORMAT_24.format(date) : DATE_FORMAT_12.format(date);
    }

    public String getTimeFormatted() {
        return getTimeFormatted(isUsing24Format());
    }

    public void save() {
        try (OutputStream fileOutputStream = new FileOutputStream(file)) {
            properties.store(fileOutputStream, "ChemClock");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
