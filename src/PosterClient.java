import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PosterClient {

    public static void main(String[] args) {
        final String inputPath;
        final String outputPath;
        if (args.length == 2) {
            inputPath = args[0];
            outputPath = args[1];
        } else {
            inputPath = "input.jpg";
            outputPath = "output.jpg";

        }


        BufferedImage image = new BufferedImage(1000, 1000, 5);
        File sourceImage1 = new File(inputPath);
        try {
            image = ImageIO.read(sourceImage1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        final long start = System.currentTimeMillis();
        Posterizer posterizer = new Posterizer(image);

        image = posterizer.nationalParkPoster();
        //image = posterizer.detectEdges(10);
        File outputFile1 = new File(outputPath);
        try {
            ImageIO.write(image, "jpg", outputFile1);
        } catch (IOException e) {

        }
        final long stop = System.currentTimeMillis();
        final long time = stop-start;
        if(time<1000){
            System.out.printf("Ran for %d milliseconds", time);
        }
        else if(time < 60000){
            System.out.printf("Ran for %.3f seconds", (double)time/1000);
        }
        else{
            System.out.printf("Ran for %.5 minutes", (double)time/60000);
        }
    }
}
