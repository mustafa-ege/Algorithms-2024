import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * This class accomplishes Mission Eco-Maintenance
 */
public class OptimalESVDeploymentGP
{
    private ArrayList<Integer> maintenanceTaskEnergyDemands;

    /*
     * Should include tasks assigned to ESVs.
     * For the sample input:
     * 8 100
     * 20 50 40 70 10 30 80 100 10
     * 
     * The list should look like this:
     * [[100], [80, 20], [70, 30], [50, 40, 10], [10]]
     * 
     * It is expected to be filled after getMinNumESVsToDeploy() is called.
     */
    private ArrayList<ArrayList<Integer>> maintenanceTasksAssignedToESVs = new ArrayList<>();

    ArrayList<ArrayList<Integer>> getMaintenanceTasksAssignedToESVs() {
        return maintenanceTasksAssignedToESVs;
    }

    public OptimalESVDeploymentGP(ArrayList<Integer> maintenanceTaskEnergyDemands) {
        this.maintenanceTaskEnergyDemands = maintenanceTaskEnergyDemands;
    }

    public ArrayList<Integer> getMaintenanceTaskEnergyDemands() {
        return maintenanceTaskEnergyDemands;
    }

    /**
     *
     * @param maxNumberOfAvailableESVs the maximum number of available ESVs to be deployed
     * @param maxESVCapacity the maximum capacity of ESVs
     * @return the minimum number of ESVs required using first fit approach over reversely sorted items.
     * Must return -1 if all tasks can't be satisfied by the available ESVs
     */
    public int getMinNumESVsToDeploy(int maxNumberOfAvailableESVs, int maxESVCapacity)
    {
        Collections.sort(maintenanceTaskEnergyDemands, Collections.reverseOrder());

        int[] esvRemainings = new int[maxNumberOfAvailableESVs];
        for (int e = 0 ; e < esvRemainings.length; e++)
            esvRemainings[e] = maxESVCapacity;
        for (int task : maintenanceTaskEnergyDemands){
            boolean taskAssigned = false;
            for (int i = 0; i < maxNumberOfAvailableESVs; i++) {
                if (task <= esvRemainings[i]) {
                    esvRemainings[i] -= task;
                    taskAssigned = true;
                    if (maintenanceTasksAssignedToESVs.size() <= i)
                        maintenanceTasksAssignedToESVs.add(new ArrayList<>());
                    maintenanceTasksAssignedToESVs.get(i).add(task);
                    break;
                }
            }
            if (!taskAssigned){
                return -1;}
            }
        int minNumESVs = 0;
        for (int esv : esvRemainings){
            if (esv < maxESVCapacity)
                minNumESVs++;
        }
        return minNumESVs;
    }
    public void print(int minEvs) {
        if (minEvs == -1){
            System.out.println("Warning: Mission Eco-Maintenance Failed.");
        } else {
            System.out.println("The minimum number of ESVs to deploy: " + minEvs);
            for (int i = 0; i < maintenanceTasksAssignedToESVs.size(); i++)
                System.out.println("ESV "+ (i+1) +" tasks: " + printList(maintenanceTasksAssignedToESVs.get(i)));
        }
    }
    public String printList(ArrayList<Integer> tasks){
        String list = "[";
        for (int i = 0; i < tasks.size(); i++){
            list += tasks.get(i);
            if (i < tasks.size()-1)
                list += ", ";
        }
        list += "]";
        return list;
    }
    public static ArrayList<ArrayList<Integer>> readOptimalEsvFromFile(String file) throws IOException {
        ArrayList<ArrayList<Integer>> inputs = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String[] firstLine = reader.readLine().split(" ");
            ArrayList<Integer> possessed = new ArrayList<>();
            possessed.add(Integer.parseInt(firstLine[0]));
            possessed.add(Integer.parseInt(firstLine[1]));
            inputs.add(possessed);
            String[] secondLine = reader.readLine().split(" ");
            ArrayList<Integer> demands = new ArrayList<>();
            for (String s : secondLine) {
                demands.add(Integer.parseInt(s));
            }
            inputs.add(demands);
        }
        return inputs;
    }

}
