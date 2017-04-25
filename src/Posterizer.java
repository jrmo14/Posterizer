import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Posterizer {

    BufferedImage image;
    List<Integer> colorOptions = new ArrayList<>();
    private final int WHITE = 255 << 16 | 255 << 8 | 255;
    private final int BLACK = 0;


    /**
     * Takes in an image to be processed
     *
     * @param img
     */
    public Posterizer(BufferedImage img) {
        image = img;
        try {
            populateScheme();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param threshold Minimum integer value for the difference between two pixels it to be said that there is an edge
     * @return BufferedImage image, a black and white image that shows the edges of the image
     */
    public BufferedImage detectEdges(int threshold) {
        BufferedImage m_img = makeImageClone();
        for (int x = 0; x < m_img.getWidth(); x++) {
            for (int y = 0; y < m_img.getHeight(); y++) {
                // Literal edge cases
                if (y == m_img.getHeight() - 1 && x == m_img.getWidth() - 1) {
                    if ((colorDist(removeAlpha(image.getRGB(x, y)), removeAlpha(image.getRGB(x, y - 1))) > threshold
                            && colorDist(removeAlpha(image.getRGB(x, y)), removeAlpha(image.getRGB(x - 1, y))) > threshold)
                            || (colorDist(removeAlpha(image.getRGB(x, y)), removeAlpha(image.getRGB(x, y - 1))) > threshold * 4
                            || (colorDist(removeAlpha(image.getRGB(x, y)), removeAlpha(image.getRGB(x - 1, y))) > threshold * 4))) {
                        image.setRGB(x, y, BLACK);
                    } else {
                        image.setRGB(x, y, WHITE);
                    }
                } else {
                    if (y == m_img.getHeight() - 1) {
                        if ((colorDist(removeAlpha(image.getRGB(x, y)), removeAlpha(image.getRGB(x, y - 1))) > threshold
                                && colorDist(removeAlpha(image.getRGB(x, y)), removeAlpha(image.getRGB(x + 1, y))) > threshold)
                                || (colorDist(removeAlpha(image.getRGB(x, y)), removeAlpha(image.getRGB(x, y - 1))) > threshold * 4
                                || (colorDist(removeAlpha(image.getRGB(x, y)), removeAlpha(image.getRGB(x + 1, y))) > threshold * 4))) {
                            image.setRGB(x, y, BLACK);
                        } else {
                            image.setRGB(x, y, WHITE);
                        }
                    } else if (x == m_img.getWidth() - 1) {
                        if ((colorDist(removeAlpha(image.getRGB(x, y)), removeAlpha(image.getRGB(x, y + 1))) > threshold
                                && colorDist(removeAlpha(image.getRGB(x, y)), removeAlpha(image.getRGB(x - 1, y))) > threshold)
                                || (colorDist(removeAlpha(image.getRGB(x, y)), removeAlpha(image.getRGB(x, y + 1))) > threshold * 4
                                || (colorDist(removeAlpha(image.getRGB(x, y)), removeAlpha(image.getRGB(x - 1, y))) > threshold * 4))) {
                            image.setRGB(x, y, BLACK);
                        } else {
                            image.setRGB(x, y, WHITE);
                        }
                    } else {
                        // Main case
                        if ((colorDist(removeAlpha(image.getRGB(x, y)), removeAlpha(image.getRGB(x, y + 1))) > threshold
                                && colorDist(removeAlpha(image.getRGB(x, y)), removeAlpha(image.getRGB(x + 1, y))) > threshold)
                                || (colorDist(removeAlpha(image.getRGB(x, y)), removeAlpha(image.getRGB(x, y + 1))) > threshold * 4
                                || (colorDist(removeAlpha(image.getRGB(x, y)), removeAlpha(image.getRGB(x + 1, y))) > threshold * 4))) {
                            image.setRGB(x, y, BLACK);
                        } else {
                            image.setRGB(x, y, WHITE);
                        }
                    }
                }
            }


        }
        return image;
    }


    /**
     * @param colorA an integer value containing rgb values in hex form i.e. 1752c1 to represent a blue color
     * @param colorB another integer value containign rgb values in hex form i.e. a8061e to represent red
     * @return the "distance" between the two colors
     */
    private double colorDist(int colorA, int colorB) {
        // Same as finding the distance between 2 pts in 3d
        final int rA = ((colorA >>> 16) << 16) >>> 16;
        final int gA = ((colorA >> 8) << 24) >>> 24;
        final int bA = (colorA << 24) >>> 24;
        final int rB = ((colorB >>> 16) << 16) >> 16;
        final int gB = ((colorB >>> 8) << 24) >>> 24;
        final int bB = (colorB << 24) >>> 24;

        return Math.sqrt(Math.pow(rA - rB, 2) + Math.pow(gA - gB, 2) + Math.pow(bA - bB, 2));
    }

    /**
     * Convert an image to greyscale
     *
     * @param m_image
     * @return
     */
    public BufferedImage greyscale(BufferedImage m_image) {
        for (int x = 0; x < m_image.getWidth(); x++) {
            for (int y = 0; y < m_image.getHeight(); y++) {
                int m_rgb = removeAlpha(m_image.getRGB(x, y));
                final int r = ((m_rgb >>> 16) << 16) >>> 16;
                final int g = ((m_rgb >>> 8) << 24) >>> 24;
                final int b = (m_rgb << 24) >>> 24;
                final int avg = (r + g + b) / 3;
                m_rgb = (avg << 16) | (avg << 8) | avg;
                m_image.setRGB(x, y, m_rgb);
            }
        }
        image = m_image;
        return image;
    }


    public BufferedImage nationalParkPoster() {
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                image.setRGB(x, y, fitColorScheme(image.getRGB(x, y)));
            }
        }

        return image;
    }


    public BufferedImage scifi(){
        BufferedImage m_img = makeImageClone();
        for(int x = 0; x<m_img.getWidth(); x++) {
            for (int y = 0; y < m_img.getHeight(); y++) {

            }
        }
        return m_img;
    }
    /**
     * finds and returns the closest color to m_rgb in the color scheme
     *
     * @param m_rgb
     * @return
     */

    private int fitColorScheme(int m_rgb) {
        // Cut off unneeded alpha value
        m_rgb = Integer.valueOf(Integer.toHexString(m_rgb).substring(2), 16);
        // Finds closest Material Design Color for each pixel
        int closestColor = 0;
        double leastDif = Integer.MAX_VALUE;
        for (int i = 0; i < colorOptions.size(); i++) {
            if (colorDist(m_rgb, colorOptions.get(i)) < leastDif) {
                leastDif = colorDist(m_rgb, colorOptions.get(i));
                closestColor = colorOptions.get(i);
            }
        }

        // Reverse the split that was preformed earlier, the | is a bitwise OR
        //m_rgb = (r << 16) | (b << 8) | g;
        //System.out.printf("%x\n", closestColor);
        return closestColor;
    }

    /**
     * Used with fitColorScheme, reads a file containing hex colors in color scheme to be used
     *
     * @throws IOException
     */
    private void populateScheme() throws IOException {
        final FileReader file = new FileReader("/Users/jacksonmoffet/git/Posterizer/res/colorSimple.txt");
        final BufferedReader reader = new BufferedReader(file);
        String line;
        int count = 0;
        while ((line = reader.readLine()) != null) {
            if (line.equals("")) {
                break;
            }
            colorOptions.add(Integer.parseInt(line, 16));
            count++;
        }
    }


    /**
     * Good to call before getting the individual colors of combined rgb
     *
     * @param m_rgb
     * @return m_rgb without the alpha values at the front
     */
    private int removeAlpha(int m_rgb) {
        return Integer.valueOf(Integer.toHexString(m_rgb).substring(2), 16);
    }

    /**
     * @return a copy of image to avoid having to overwrite and get bad data
     */
    private BufferedImage makeImageClone() {
        ColorModel cm = image.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = image.copyData(null);
        BufferedImage m_image = new BufferedImage(cm, raster, isAlphaPremultiplied, null);
        return m_image;
    }


}
