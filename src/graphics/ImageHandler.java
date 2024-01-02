package graphics;

import gamelogic.GameError;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class ImageHandler {

    public static final String DEFAULT_MISSING_IMAGE_NAME = "blank";

    private Map<String, Image> imageCache;

    public ImageHandler() {
        this.imageCache = new HashMap<>();
    }

    public Image loadImage(String imageName) {
        return loadImage(imageName, "Couldn't load image: " + imageName);
    }

    public Image loadImage(String imageName, String excMsg) {
        try {
            return imageCache.getOrDefault(imageName, readImageFromFile(imageName));
        } catch (IOException e) {
            try {
                throw new GameError(excMsg, e);
            } catch (GameError ex) {
                try {
                    return imageCache.getOrDefault(DEFAULT_MISSING_IMAGE_NAME, readImageFromFile(DEFAULT_MISSING_IMAGE_NAME));
                } catch (IOException exc) {
                    throw new IllegalStateException(excMsg + ". " + e);
                }
            }
        }
    }

    private BufferedImage readImageFromFile(String imageName) throws IOException {
        BufferedImage img = ImageIO.read(Paths.get("images/" + imageName + ".png").toFile());
        imageCache.put(imageName, img);
        return img;
    }
}
