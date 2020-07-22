package objects;

import javafx.scene.image.Image;

import java.io.File;
import java.util.HashMap;

public class TextureManager {

    // зображення мікрооб'ектів
    public static HashMap<String, Image> textures;

    public static void init() {
        File dir = new File("src/objects/resources");
        try {
            textures = new HashMap<String, Image>();
            for(File item : dir.listFiles()) {
                Image image_tmp = new Image("objects/resources/" + item.getName());
                double y = image_tmp.getHeight();
                double x = image_tmp.getWidth();
                Image newImage = new Image("objects/resources/" + item.getName(),
                        x * 2, y * 2, false, false);
                textures.put(item.getName(), newImage);
                System.out.println(item.getName());
            }

            System.out.println("initialized successfully");
        }
        catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
}
