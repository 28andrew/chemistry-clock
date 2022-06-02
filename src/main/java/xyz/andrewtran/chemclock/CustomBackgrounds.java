package xyz.andrewtran.chemclock;

import javafx.scene.image.Image;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class CustomBackgrounds {
    public Map<String, Image> imageMap = new HashMap<>();

    public CustomBackgrounds() {
        File backgroundsFolder = new File("/boot/backgrounds/");
        File[] files = backgroundsFolder.listFiles();
        if (files != null) {
            for (File file : files) {
                String name = file.getName();
                if (name.endsWith(".png")) {
                    // Found a png to add
                    try (InputStream inputStream = new FileInputStream(file)){
                        imageMap.put(name, new Image(inputStream));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
