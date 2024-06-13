
import java.io.IOException;

class Main {
    public static void main(String args[]) throws IOException {
        int[] randomData = FileInput.getFlowDuration(args[0]);
        Experiment.allExperiments(randomData);
    }

}
