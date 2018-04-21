import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ColorReader {

    public static void main(String[] args) {
        //hexFinder("../res/color.html", "../res/colorComplex.txt");
        findDuplicateColor();
    }


    private static void hexFinder(final String fileInputPath, final String fileOutputPath) {
        String line;

        int colorCount = 0;

        try {
            FileReader reader = new FileReader(fileInputPath);
            BufferedReader bufferedReader = new BufferedReader(reader);

            FileWriter writer = new FileWriter(fileOutputPath);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);

            while ((line = bufferedReader.readLine()) != null) {
                if (catchHexCode(line).equals("")) {
                    continue;
                }
                bufferedWriter.write(catchHexCode(line));
                bufferedWriter.newLine();
                colorCount++;
            }

            // Close everything
            bufferedReader.close();
            bufferedWriter.close();
            reader.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(colorCount);
    }

    private static String catchHexCode(final String line) {

        if (line.contains("hex")) {
            return line.substring(line.indexOf("#") + 1, line.indexOf("#") + 7);
        } else return "";
    }

    private static void findDuplicateColor() {
        int count = 0;
        String line;
        List<String> alreadyIndexed = new ArrayList<>();

        try {
            FileReader file = new FileReader("../res/colorComplex.txt");
            BufferedReader reader = new BufferedReader(file);
            while ((line = reader.readLine()) != null) {
                boolean foundDupe = false;
                count++;
                for (int i = 0; i < alreadyIndexed.size(); i++) {
                    if (alreadyIndexed.get(i).equals(line)) {
                        System.out.println(count);
                        foundDupe = true;
                        break;
                    }
                }
                if (!foundDupe) {
                    alreadyIndexed.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
