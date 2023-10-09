import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CSVread {
    List<String[]> ReadMethod(String path){
        String line = "";
        String[] Line;
        List<String[]> DataList = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(path))) {
            int i = 0;
            line = scanner.nextLine();
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                Line = line.split(",");
                DataList.add(Line);
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return DataList;
    }
}


