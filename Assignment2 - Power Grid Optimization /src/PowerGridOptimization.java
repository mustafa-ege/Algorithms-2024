import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This class accomplishes Mission POWER GRID OPTIMIZATION
 */
public class PowerGridOptimization {
    private ArrayList<Integer> amountOfEnergyDemandsArrivingPerHour;

    public PowerGridOptimization(ArrayList<Integer> amountOfEnergyDemandsArrivingPerHour){
        this.amountOfEnergyDemandsArrivingPerHour = amountOfEnergyDemandsArrivingPerHour;
    }

    public ArrayList<Integer> getAmountOfEnergyDemandsArrivingPerHour() {
        return amountOfEnergyDemandsArrivingPerHour;
    }
    /**
     *     Function to implement the given dynamic programming algorithm
     *     SOL(0) <- 0
     *     HOURS(0) <- [ ]
     *     For{j <- 1...N}
     *         SOL(j) <- max_{0<=i<j} [ (SOL(i) + min[ E(j), P(j − i) ] ]
     *         HOURS(j) <- [HOURS(i), j]
     *     EndFor
     *
     * @return OptimalPowerGridSolution
     */
    public OptimalPowerGridSolution getOptimalPowerGridSolutionDP(){
        int N = amountOfEnergyDemandsArrivingPerHour.size();
        int[] D = new int[N+1];
        int[] E = new int[N+1];
        for (int i = 1; i <= N; i++) {
            D[i] = amountOfEnergyDemandsArrivingPerHour.get(i - 1);
            E[i] = i * i;
        }
        int[] SOL = new int[N+1];

        ArrayList<ArrayList<Integer>> HOURS = new ArrayList<>(N+1);

        SOL[0] = 0;
        HOURS.add(new ArrayList<>());

        for (int j = 1; j <= N; j++) {

            int maxSOL = 0;
            ArrayList<Integer> chosenHours = new ArrayList<>();

            for (int i = 0; i < j; i++) {
                int currentSOL = SOL[i] + Math.min(D[j], E[j - i]);
                if (currentSOL > maxSOL) {
                    maxSOL = currentSOL;
                    chosenHours = HOURS.get(i);
                }
            }
            SOL[j] = maxSOL;
            ArrayList<Integer> hoursList = new ArrayList<>();
            for (int i : chosenHours)
                hoursList.add(i);
            hoursList.add(j);
            HOURS.add(hoursList);
        }

        int sum = 0;
        for (int num: amountOfEnergyDemandsArrivingPerHour)
            sum += num;

        OptimalPowerGridSolution solution = new OptimalPowerGridSolution(SOL[N], HOURS.get(N));
        solution.setDemandedEnergy(sum);
        return solution;
    }
    public static ArrayList<Integer> readEnergyDemandsFromFile(String filename) throws IOException {
        ArrayList<Integer> energyDemands = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.trim().split(" ");
                for (String token : tokens)
                    energyDemands.add(Integer.parseInt(token));
            }
        }
        return energyDemands;
    }
}
