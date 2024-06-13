import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HyperloopTrainNetwork implements Serializable {
    static final long serialVersionUID = 11L;
    public double averageTrainSpeed;
    public final double averageWalkingSpeed = 1000 / 6.0;;
    public int numTrainLines;
    public Station startPoint;
    public Station destinationPoint;
    public List<TrainLine> lines;

    /**
     * Method with a Regular Expression to extract integer numbers from the fileContent
     * @return the result as int
     */
    public int getIntVar(String varName, String fileContent) {
        Pattern p = Pattern.compile("[\\t ]*" + varName + "[\\t ]*=[\\t ]*([0-9]+)");
        Matcher m = p.matcher(fileContent);
        m.find();
        return Integer.parseInt(m.group(1));
    }

    /**
     * Write the necessary Regular Expression to extract string constants from the fileContent
     * @return the result as String
     */
    public String getStringVar(String varName, String fileContent) {
        Pattern p = Pattern.compile("[\\t ]*" + varName + "[\\t ]*=[\\t ]*\"([^\"]+)\"");
        Matcher m = p.matcher(fileContent);
        m.find();
        return m.group(1);
    }

    /**
     * Write the necessary Regular Expression to extract floating point numbers from the fileContent
     * Your regular expression should support floating point numbers with an arbitrary number of
     * decimals or without any (e.g. 5, 5.2, 5.02, 5.0002, etc.).
     * @return the result as Double
     */
    public Double getDoubleVar(String varName, String fileContent) {
        Pattern p = Pattern.compile("[\\t ]*" + varName + "[\\t ]*=[\\t ]*([0-9]+(?:\\.[0-9]+)?)");
        Matcher m = p.matcher(fileContent);
        m.find();
        return Double.parseDouble(m.group(1));
    }

    /**
     * Write the necessary Regular Expression to extract a Point object from the fileContent
     * points are given as an x and y coordinate pair surrounded by parentheses and separated by a comma
     * @return the result as a Point object
     */
    public Point getPointVar(String varName, String fileContent) {
        Pattern p = Pattern.compile("\\s*" + varName + "\\s*=\\s*\\(\\s*(\\d+)\\s*," +
                "\\s*(\\d+)\\s*\\)");
        Matcher m = p.matcher(fileContent);
        m.find();
        int x = Integer.parseInt(m.group(1));
        int y = Integer.parseInt(m.group(2));
        return new Point(x, y);
    }

    /**
     * Function to extract the train lines from the fileContent by reading train line names and their 
     * respective stations.
     * @return List of TrainLine instances
     */
    public List<TrainLine> getTrainLines(String fileContent) {
        List<TrainLine> trainLines = new ArrayList<>();
        Pattern p = Pattern.compile("\\s*train_line_name\\s*=\\s*\"([^\"]+)\"\\s*train_line_stations\\s*=[\\t ]*\\((.*?)\\)(?!.*\\()");

        Matcher m = p.matcher(fileContent);
        while (m.find()) {
            String lineName = m.group(1);

            Pattern pattern = Pattern.compile("\\(\\s*(\\d+)\\s*,\\s*(\\d+)\\s*\\)");
            String coords = "(" + m.group(2) + ")";
            Matcher matcher = pattern.matcher(coords);

            List<Station> stations = new ArrayList<>();
            int statNum = 0;
            while (matcher.find()) {
                int x = Integer.parseInt(matcher.group(1));
                int y = Integer.parseInt(matcher.group(2));
                stations.add(new Station(new Point(x,y), lineName+ " Line Station " + ++statNum));
            }
            trainLines.add(new TrainLine(lineName, stations));
        }
        return trainLines;
    }

    /**
     * Function to populate the given instance variables of this class by calling the functions above.
     */
    public void readInput(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
            String fileContent = sb.toString();

            averageTrainSpeed = 1000.0 / 60.0 * getDoubleVar("average_train_speed", fileContent);
            numTrainLines = getIntVar("num_train_lines", fileContent);
            startPoint = new Station(getPointVar("starting_point", fileContent), "Starting Point");
            destinationPoint = new Station(getPointVar("destination_point", fileContent),"Final Destination");
            lines = getTrainLines(fileContent);

        } catch (IOException e) {
            e.printStackTrace(); // Handle file reading error
        }
    }
}