import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileInput {
    public static int[] getFlowDuration(String input) {
        String csvFile = input;
        String line = "";
        List<Integer> columnList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            boolean firstLinePassed = false;

            while ((line = br.readLine()) != null) {
                if (!firstLinePassed){
                    firstLinePassed = true;
                    continue;
                }
                String[] data = line.split(",");

                String seventhColumn = data[6];

                columnList.add(Integer.parseInt(seventhColumn));
            }



        } catch (IOException e) {
            e.printStackTrace();
        }
        int[] columnArray = new int[columnList.size()];
        for (int i = 0; i < columnList.size(); i++) {
            columnArray[i] = columnList.get(i);
        }

        return columnArray;
    }
}
