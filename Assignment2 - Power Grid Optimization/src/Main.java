import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Main class
 */
// FREE CODE HERE
public class Main {
    public static void main(String[] args) throws IOException {

       /** MISSION POWER GRID OPTIMIZATION BELOW **/

        System.out.println("##MISSION POWER GRID OPTIMIZATION##");
        // You are expected to read the file given as the first command-line argument to read
        // the energy demands arriving per hour. Then, use this data to instantiate a 
        // PowerGridOptimization object. You need to call getOptimalPowerGridSolutionDP() method
        // of your PowerGridOptimization object to get the solution, and finally print it to STDOUT.

        ArrayList<Integer> energyDemands = PowerGridOptimization.readEnergyDemandsFromFile(args[0]);
        PowerGridOptimization pgo = new PowerGridOptimization(energyDemands);
        OptimalPowerGridSolution solution = pgo.getOptimalPowerGridSolutionDP();
        solution.print();
        System.out.println("##MISSION POWER GRID OPTIMIZATION COMPLETED##");

        /** MISSION ECO-MAINTENANCE BELOW **/

        System.out.println("##MISSION ECO-MAINTENANCE##");
        // You are expected to read the file given as the second command-line argument to read
        // the number of available ESVs, the capacity of each available ESV, and the energy requirements
        // of the maintenance tasks. Then, use this data to instantiate an OptimalESVDeploymentGP object.
        // You need to call getMinNumESVsToDeploy(int maxNumberOfAvailableESVs, int maxESVCapacity) method
        // of your OptimalESVDeploymentGP object to get the solution, and finally print it to STDOUT.

        ArrayList<ArrayList<Integer>> params = OptimalESVDeploymentGP.readOptimalEsvFromFile(args[1]);
        OptimalESVDeploymentGP oed = new OptimalESVDeploymentGP(params.get(1));
        int minEvs = oed.getMinNumESVsToDeploy(params.get(0).get(0), params.get(0).get(1));
        oed.print(minEvs);

        System.out.println("##MISSION ECO-MAINTENANCE COMPLETED##");
    }
}
